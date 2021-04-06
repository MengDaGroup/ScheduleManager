#
#### 目录介绍
- 1.复杂页面库介绍
- 2.本库优势亮点
    - 2.1 支持多种状态切换管理
    - 2.2 支持添加多个header和footer
    - 2.3 支持侧滑功能和拖拽移动
    - 2.4 其他亮点介绍
- 3.如何使用介绍
    - 3.1 最基础的使用
    - 3.2 添加下拉刷新和加载更多监听
    - 3.3 添加header和footer操作
    - 3.4 设置数据和刷新
    - 3.5 设置adapter
    - 3.6 设置条目点击事件
    - 3.7 设置侧滑删除功能[QQ侧滑删除]
    - 3.8 轻量级拖拽排序与滑动删除
- 4.关于状态切换
    - 4.1 关于布局内容
    - 4.2 关于实现思路
    - 4.3 关于状态切换api调用
    - 4.4 关于自定义状态布局
    - 4.5 关于自定义布局交互事件处理
- 5.常用api介绍
    - 5.1 状态切换方法说明
    - 5.2 viewHolder方法说明
    - 5.3 adapter方法说明
    - 5.4 分割线方法说明
    - 5.5 swipe侧滑方法说明
    - 5.6 其他api说明

#### 最基础的使用
- 首先在集成：
    - implementation 'org.yczbj:YCRefreshViewLib:2.5.8'
- 在布局中
    ```
    <org.yczbj.ycrefreshviewlib.YCRefreshView
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:id="@+id/recyclerView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_progress="@layout/view_custom_loading_data"
        app:layout_empty="@layout/view_custom_empty_data"
        app:layout_error="@layout/view_custom_data_error"/>
    ```
- 在代码中，初始化recyclerView
    ```
    adapter = new PersonAdapter(this);
    recyclerView.setAdapter(adapter);
    final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(linearLayoutManager);
    adapter.addAll(data);
    ```
- 在代码中，创建adapter实现RecyclerArrayAdapter<T>
    ```
    public class PersonAdapter extends RecyclerArrayAdapter<PersonData> {

        public PersonAdapter(Context context) {
            super(context);
        }

        @Override
        public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
            return new PersonViewHolder(parent);
        }

        public class PersonViewHolder extends BaseViewHolder<PersonData> {

            private ImageView iv_news_image;

            PersonViewHolder(ViewGroup parent) {
                super(parent, R.layout.item_news);
                iv_news_image = getView(R.id.iv_news_image);
            }

            @Override
            public void setData(final PersonData person){

            }
        }
    }
    ```




#### 添加下拉刷新和加载更多监听
- 下拉刷新监听操作
    ```
    //设置刷新listener
    recyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            //刷新操作
        }
    });
    //设置是否刷新
    recyclerView.setRefreshing(false);
    //设置刷新颜色
    recyclerView.setRefreshingColorResources(R.color.colorAccent);
    ```
- 上拉加载更多监听操作
    - 第一种情况，上拉加载更多后自动加载下一页数据
    ```
    //设置上拉加载更多时布局，以及监听事件
    adapter.setMore(R.layout.view_more, new OnLoadMoreListener() {
        @Override
        public void onLoadMore() {
            //可以做请求下一页操作
        }
    });
    ```
    - 第二种情况，上拉加载更多后手动触发加载下一页数据
    ```
    adapter.setMore(R.layout.view_more2, new OnMoreListener() {
        @Override
        public void onMoreShow() {
            //不做处理
        }

        @Override
        public void onMoreClick() {
            //点击触发加载下一页数据
        }
    });
    ```
- 在上拉加载更多时，可能出现没有更多数据，或者上拉加载失败，该如何处理呢？
    ```
    //设置上拉加载没有更多数据监听
    adapter.setNoMore(R.layout.view_no_more, new OnNoMoreListener() {
        @Override
        public void onNoMoreShow() {
            //上拉加载，没有更多数据展示，这个方法可以暂停或者停止加载数据
            adapter.pauseMore();
        }

        @Override
        public void onNoMoreClick() {
            //这个方法是点击没有更多数据展示布局的操作，比如可以做吐司等等
            Log.e("逗比","没有更多数据了");
        }
    });
    //设置上拉加载更多异常监听数据监听
    adapter.setError(R.layout.view_error, new OnErrorListener() {
        @Override
        public void onErrorShow() {
            //上拉加载，加载更多数据异常展示，这个方法可以暂停或者停止加载数据
            adapter.pauseMore();
        }

        @Override
        public void onErrorClick() {
            //这个方法是点击加载更多数据异常展示布局的操作，比如恢复加载更多等等
            adapter.resumeMore();
        }
    });
    ```



#### 添加header和footer操作
- 添加headerView操作。至于添加footerView的操作，几乎和添加header步骤是一样的。
    - 添加普通的布局【非listView或者RecyclerView布局】
    ```
    adapter.addHeader(new InterItemView() {
        @Override
        public View onCreateView(ViewGroup parent) {
            View inflate = LayoutInflater.from(context).inflate(R.layout.header_view, null);
            return inflate;
        }

        @Override
        public void onBindView(View headerView) {
            TextView tvTitle = headerView.findViewById(R.id.tvTitle);
        }
    });
    ```
    - 添加list布局【以横向recyclerView为例子】
    ```
    adapter.addHeader(new InterItemView() {
        @Override
        public View onCreateView(ViewGroup parent) {
            RecyclerView recyclerView = new RecyclerView(parent.getContext()){
                //为了不打扰横向RecyclerView的滑动操作，可以这样处理
                @SuppressLint("ClickableViewAccessibility")
                @Override
                public boolean onTouchEvent(MotionEvent event) {
                    super.onTouchEvent(event);
                    return true;
                }
            };
            return recyclerView;
        }

        @Override
        public void onBindView(View headerView) {
            //这里的处理别忘了
            ((ViewGroup)headerView).requestDisallowInterceptTouchEvent(true);
        }
    });
    ```
- 注意要点
    - 如果添加了HeaderView，凡是通过ViewHolder拿到的position都要减掉HeaderView的数量才能得到正确的position。



#### 设置数据和刷新
- 添加所有数据，可以是集合，也可以是数组
    ```
    //添加所有数据
    adapter.addAll(data);
    //添加单挑数据
    adapter.add(data);
    ```
- 插入，刷新和删除数据
    ```
    //插入指定索引数据，单个数据
    adapter.insert(data, pos);
    //插入指定索引数据，多个数据
    adapter.insertAll(data, pos);
    //刷新指定索引数据
    adapter.update(data, pos);
    //删除数据，指定数据
    adapter.remove(data);
    //删除数据，指定索引
    adapter.remove(pos);
    //清空所有数据
    ```


#### 设置adapter
- 注意自定义adapter需要实现RecyclerArrayAdapter<T>，其中T是泛型，就是你要使用的bean数据类型
    ```
    public class PersonAdapter extends RecyclerArrayAdapter<PersonData> {

        public PersonAdapter(Context context) {
            super(context);
        }

        @Override
        public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
            return new PersonViewHolder(parent);
        }

        public class PersonViewHolder extends BaseViewHolder<PersonData> {

            private TextView tv_title;
            private ImageView iv_news_image;

            PersonViewHolder(ViewGroup parent) {
                super(parent, R.layout.item_news);
                iv_news_image = getView(R.id.iv_news_image);
                tv_title = getView(R.id.tv_title);

                //添加孩子的点击事件
                addOnClickListener(R.id.iv_news_image);
                addOnClickListener(R.id.tv_title);
            }

            @Override
            public void setData(final PersonData person){
                Log.i("ViewHolder","position"+getDataPosition());
                tv_title.setText(person.getName());
            }
        }
    }
    ```


#### 设置条目点击事件[item条目点击事件，item条目孩子view点击事件]
- 条目单击点击事件，长按事件[省略，可以自己看代码]
    ```
    adapter.setOnItemClickListener(new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            if (adapter.getAllData().size()>position && position>=0){
                //处理点击事件逻辑
            }
        }
    });
    ```
- 条目中孩子的点击事件
    ```
    //添加孩子的点击事件，可以看3.5设置adapter
    addOnClickListener(R.id.iv_news_image);
    addOnClickListener(R.id.tv_title);

    //设置孩子的点击事件
    adapter.setOnItemChildClickListener(new OnItemChildClickListener() {
        @Override
        public void onItemChildClick(View view, int position) {
            switch (view.getId()){
                case R.id.iv_news_image:
                    Toast.makeText(HeaderFooterActivity.this,
                            "点击图片了",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.tv_title:
                    Toast.makeText(HeaderFooterActivity.this,
                            "点击标题",Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    });
    ```



#### 设置侧滑删除功能[QQ侧滑删除]
- 在布局文件中，这里省略部分代码
    ```
    <org.yczbj.ycrefreshviewlib.swipeMenu.YCSwipeMenu
        android:orientation="horizontal">
        <!--item内容-->
        <RelativeLayout
        </RelativeLayout>

        <!-- 侧滑菜单 -->
        <Button
            android:id="@+id/btn_del"/>
        <Button
            android:id="@+id/btn_top"/>
    </org.yczbj.ycrefreshviewlib.swipeMenu.YCSwipeMenu>
    ```
- 在代码中设置
    - 在adapter中定义接口
    ```
    private OnSwipeMenuListener listener;
    public void setOnSwipeMenuListener(OnSwipeMenuListener listener) {
        this.listener = listener;
    }
    ```
    - 在adapter设置点击事件
    ```
    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_del:
                    if (null != listener) {
                        listener.toDelete(getAdapterPosition());
                    }
                    break;
                case R.id.btn_top:
                    if (null != listener) {
                        listener.toTop(getAdapterPosition());
                    }
                    break;
            }
        }
    };
    btn_del.setOnClickListener(clickListener);
    btn_top.setOnClickListener(clickListener);
    ```
- 处理置顶或者删除的功能
    ```
    adapter.setOnSwipeMenuListener(new OnSwipeMenuListener() {
        //删除功能
        @Override
        public void toDelete(int position) {

        }

        //置顶功能
        @Override
        public void toTop(int position) {

        }
    });
    ```


#### 轻量级拖拽排序与滑动删除
- 处理长按拖拽，滑动删除的功能。轻量级，自由选择是否实现。
    ```
    mCallback = new DefaultItemTouchHelpCallback(new DefaultItemTouchHelpCallback
                            .OnItemTouchCallbackListener() {
        @Override
        public void onSwiped(int adapterPosition) {
            // 滑动删除的时候，从数据库、数据源移除，并刷新UI
        }

        @Override
        public boolean onMove(int srcPosition, int targetPosition) {
            return false;
        }
    });
    mCallback.setDragEnable(true);
    mCallback.setSwipeEnable(true);
    mCallback.setColor(this.getResources().getColor(R.color.colorAccent));
    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mCallback);
    itemTouchHelper.attachToRecyclerView(recyclerView);
    ```




### 关于状态切换
####  关于布局内容
- YCRecyclerView是一个组合自定义控件，其布局如下所示
    ```
     <!--刷新控件    省略部分代码-->
    <android.support.v4.widget.SwipeRefreshLayout>
        <FrameLayout>
            <!--RecyclerView控件-->
            <android.support.v7.widget.RecyclerView/>
            <!--加载数据为空时的布局-->
            <FrameLayout/>
            <!--正在加载数据中的布局-->
            <FrameLayout/>
            <!--加载错误时的布局：网络错误或者请求数据错误-->
            <FrameLayout/>
        </FrameLayout>
    </android.support.v4.widget.SwipeRefreshLayout>
    ```



####  关于实现思路
- 关于页面状态切换的思路
    - 第一种方式：直接把这些界面include到main界面中，然后动态去切换界面，后来发现这样处理不容易复用到其他项目中，而且在activity中处理这些状态的显示和隐藏比较乱
    - 第二种方式：利用子类继承父类特性，在父类中写切换状态，但有些界面如果没有继承父类，又该如何处理
- 而本库采用的做法思路
    - 一个帧布局FrameLayout里写上4种不同类型布局，正常布局，空布局，加载loading布局，错误布局[网络异常，加载数据异常]
    - 当然也可以自定义这些状态的布局，通过addView的形式，将不同状态布局添加到对应的FrameLayout中。而切换状态，只需要设置布局展示或者隐藏即可。



####  关于状态切换api调用
- 如下所示
    ```
    //设置加载数据完毕状态
    recyclerView.showRecycler();
    //设置加载数据为空状态
    recyclerView.showEmpty();
    //设置加载错误状态
    recyclerView.showError();
    //设置加载数据中状态
    recyclerView.showProgress();
    ```


####  关于自定义状态布局
- 如下所示
    ```
    //设置空状态页面自定义布局
    recyclerView.setEmptyView(R.layout.view_custom_empty_data);
    recyclerView.setEmptyView(view);
    //获取空页面自定义布局
    View emptyView = recyclerView.getEmptyView();

    //设置异常状态页面自定义布局
    recyclerView.setErrorView(R.layout.view_custom_data_error);
    recyclerView.setErrorView(view);

    //设置加载loading状态页面自定义布局
    recyclerView.setProgressView(R.layout.view_progress_loading);
    recyclerView.setProgressView(view);
    ```

####  关于自定义布局交互事件处理
- 有时候，加载页面出现异常情况，比如没有网络会显示自定义的网络异常页面。现在需要点击异常页面按钮等等操作，那么该如何做呢？
    ```
    //注意需要
    LinearLayout ll_error_view = (LinearLayout) recyclerView.findViewById(R.id.ll_error_view);
    ll_error_view.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //比如，跳转到网络设置页面，或者再次刷新数据，或者其他操作等等
        }
    });
    ```



### 常用api介绍
- 状态切换方法说明
    ```
    //设置加载数据完毕状态
    recyclerView.showRecycler();
    //设置加载数据为空状态
    recyclerView.showEmpty();
    //设置加载错误状态
    recyclerView.showError();
    //设置加载数据中状态
    recyclerView.showProgress();
    //设置自定义布局，其他几个方法同理
    recyclerView.setEmptyView(R.layout.view_custom_empty_data);
    ```
- viewHolder方法说明
    ```
    //子类设置数据方法
    setData方法
    //findViewById方式
    iv_news_image = getView(R.id.iv_news_image);
    //获取上下文
    Context context = getContext();
    //获取数据索引的位置
    int dataPosition = getDataPosition();
    //添加item中子控件的点击事件
    addOnClickListener(R.id.tv_title);
    ```
- adapter方法说明
    ```
    //删除索引处的数据
    adapter.remove(0);
    //触发清空所有数据
    adapter.removeAll();
    //添加数据，注意这个是在最后索引处添加
    adapter.add(new PersonData());
    //添加所有数据
    adapter.addAll(DataProvider.getPersonList(0));
    //插入数据
    adapter.insert(data,3);
    //在某个索引处插入集合数据
    adapter.insertAll(data,3);
    //获取item索引位置
    adapter.getPosition(data);
    //触发清空所有的数据
    adapter.clear();
    //获取所有的数据
    adapter.getAllData();

    //清除所有footer
    adapter.removeAllFooter();
    //清除所有header
    adapter.removeAllHeader();
    //添加footerView
    adapter.addFooter(view);
    //添加headerView
    adapter.addHeader(view);
    //移除某个headerView
    adapter.removeHeader(view);
    //移除某个footerView
    adapter.removeFooter(view);
    //获取某个索引处的headerView
    adapter.getHeader(0);
    //获取某个索引处的footerView
    adapter.getFooter(0);
    //获取footer的数量
    adapter.getFooterCount();
    //获取header的数量
    adapter.getHeaderCount();

    //设置上拉加载更多的自定义布局和监听
    adapter.setMore(R.layout.view_more,listener);
    //设置上拉加载更多的自定义布局和监听
    adapter.setMore(view,listener);
    //设置上拉加载没有更多数据布局
    adapter.setNoMore(R.layout.view_nomore);
    //设置上拉加载没有更多数据布局
    adapter.setNoMore(view);
    //设置上拉加载没有更多数据监听
    adapter.setNoMore(R.layout.view_nomore,listener);
    //设置上拉加载异常的布局
    adapter.setError(R.layout.view_error);
    //设置上拉加载异常的布局
    adapter.setError(view);
    //设置上拉加载异常的布局和异常监听
    adapter.setError(R.layout.view_error,listener);
    //暂停上拉加载更多
    adapter.pauseMore();
    //停止上拉加载更多
    adapter.stopMore();
    //恢复上拉加载更多
    adapter.resumeMore();

    //获取上下文
    adapter.getContext();
    //应该使用这个获取item个数
    adapter.getCount();
    //设置操作数据[增删改查]后，是否刷新adapter
    adapter.setNotifyOnChange(true);

    //设置孩子点击事件
    adapter.setOnItemChildClickListener(listener);
    //设置条目点击事件
    adapter.setOnItemClickListener(listener);
    //设置条目长按事件
    adapter.setOnItemLongClickListener(listener);
    ```
- 分割线方法说明
    ```
    //可以设置线条颜色和宽度的分割线
    //四个参数，上下文，方向，线宽，颜色
    final RecycleViewItemLine line = new RecycleViewItemLine(this, LinearLayout.HORIZONTAL,
            (int)AppUtils.convertDpToPixel(1,this),
            this.getResources().getColor(R.color.color_f9f9f9));
    recyclerView.addItemDecoration(line);

    //适用于瀑布流中的间距设置
    SpaceViewItemLine itemDecoration = new SpaceViewItemLine(
            (int) AppUtils.convertDpToPixel(8,this));
    itemDecoration.setPaddingEdgeSide(true);
    itemDecoration.setPaddingStart(true);
    itemDecoration.setPaddingHeaderFooter(true);
    recyclerView.addItemDecoration(itemDecoration);

    //可以设置线条颜色和宽度，并且可以设置距离左右的间距
    DividerViewItemLine itemDecoration = new
            DividerViewItemLine( this.getResources().getColor(R.color.color_f9f9f9)
            , LibUtils.dip2px(this, 1f),
            LibUtils.dip2px(this, 72), 0);
    itemDecoration.setDrawLastItem(false);
    recyclerView.addItemDecoration(itemDecoration);