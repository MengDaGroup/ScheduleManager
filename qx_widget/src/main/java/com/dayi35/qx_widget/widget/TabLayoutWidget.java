package com.dayi35.qx_widget.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.viewpager.widget.ViewPager;

import com.dayi35.qx_utils.widget.LayoutParamsManager;
import com.dayi35.qx_widget.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiuJC on 2019/8/8
 **/
public class TabLayoutWidget extends RelativeLayout {

    private static String Tag = "TabView";

    private Context mContext;
    /**
     * tab集合
     */
    private List<Tab> mTabList = new ArrayList<>();
    /**
     * 指示器
     */
    private View mViewIndicator;

    private HorizontalScrollView mHorizaontalScorolView;

    private LinearLayout mLinearLaouyout;
    /**
     * 指示器的宽度
     */
    private int mIndicatorWidth;
    /**
     * 指示器的长度
     */
    private int mIndicatorHeight;
    /**
     * 指示器颜色
     */
    private int mIndicatorColor;
    /**
     * 指示器背景
     */
    private int mIndicatorBG;
    /**
     * tab 文字大小
     */
    private float mTabTextSize;
    /**
     * tab 选中的文字大小
     * 当{@link  }设置为true时,该属性无效
     */
    private float mTabTextSelSize;
    /**
     * tab 文字颜色
     */
    private int mTabTextColor;
    /**
     * tab 文字选中颜色
     */
    private int mTabSelTextColor;
    /**
     * tab 背景颜色
     */
    private int mTabBackgroundColor;
    /**
     * tab 选中的背景颜色
     */
    private int mTabSelBackgroundColor;
    /**
     * tab 左右padding(当mIsTabWeight=true的时候，这个值只是为了扩大点击效果）
     */
    private int mTabLeftRightPadding;
    /**
     * tab 左右Margin
     */
    private int mTabLeftRightMargin;
    /**
     * tab 是否自动设置左右padding（第一个和最后一个元素）
     */
    private boolean mAutoPadding;
    /**
     * tab是否按权重分配
     */
    private boolean mIsTabWeight;
    /**
     * 当前选中位置
     */
    private int mCurrentSelPos = 0;

    private int mDuration = 200;
    /**
     * UI设计的基础宽度
     */
    private float mUiBaseWidth = 1080f;

    /**
     * indicatro移动动画的起始X坐标
     */
    private float mIndicatorMoveStartX = 0;
    /**
     * scrollview 滑动的距离
     */
    private int mScrollX = 0;
    /**
     * 关联的viewpager
     */
    private ViewPager mViewPager;

    /**
     * 是否添加文字动画
     */
    private static boolean mIsTextAnimation = false;
    private boolean mBoldSelected;  //选中加粗

    /**
     * 是否自动滚动
     *
     * @param context
     */
    private boolean mAutoScroll;


    public TabLayoutWidget(Context context) {
        this(context, null);
    }

    public TabLayoutWidget(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabLayoutWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mLinearLaouyout = new LinearLayout(context);
        mViewIndicator = new View(context);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.widget_tab_layout_widget);
        /**
         * tab
         */
        mIsTabWeight = ta.getBoolean(R.styleable.widget_tab_layout_widget_widget_is_tab_weight, true);
        mTabTextColor = ta.getColor(R.styleable.widget_tab_layout_widget_widget_tab_text_color, Color.BLACK);
        mTabSelTextColor = ta.getColor(R.styleable.widget_tab_layout_widget_widget_tab_text_sel_color, Color.RED);
        mTabTextSize = ta.getDimension(R.styleable.widget_tab_layout_widget_widget_tab_text_size, 10);
        mTabTextSelSize = ta.getDimension(R.styleable.widget_tab_layout_widget_widget_tab_text_sel_size, mTabTextSize);
        mTabLeftRightPadding = (int) ta.getDimension(R.styleable.widget_tab_layout_widget_widget_tab_padding, 10);
        mTabLeftRightMargin = (int) ta.getDimension(R.styleable.widget_tab_layout_widget_widget_tab_margin, 10);
        mTabBackgroundColor = ta.getColor(R.styleable.widget_tab_layout_widget_widget_tab_background_color, Color.WHITE);
        mTabSelBackgroundColor = ta.getColor(R.styleable.widget_tab_layout_widget_widget_tab_sel_background_color, mTabBackgroundColor);
        mIsTextAnimation = ta.getBoolean(R.styleable.widget_tab_layout_widget_widget_is_tab_text_animation, false);
        mBoldSelected = ta.getBoolean(R.styleable.widget_tab_layout_widget_widget_text_sel_bold, false);
        mAutoScroll = ta.getBoolean(R.styleable.widget_tab_layout_widget_widget_auto_scroll, false);
        mAutoPadding = ta.getBoolean(R.styleable.widget_tab_layout_widget_widget_auto_padding, true);
        /**
         * 如果按照权重划分，则不需要HorizontalScrollView
         */
        if (mIsTabWeight) {
            LayoutParams linerLp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            mLinearLaouyout.setLayoutParams(linerLp);
            this.addView(mLinearLaouyout);
        } else {
            mHorizaontalScorolView = new HorizontalScrollView(context);
            mHorizaontalScorolView.setHorizontalScrollBarEnabled(false);
            //设置横向滚动缺省模式
            mHorizaontalScorolView.setOverScrollMode(OVER_SCROLL_NEVER);
            LayoutParams scorlViewLp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            mHorizaontalScorolView.setLayoutParams(scorlViewLp);
            mHorizaontalScorolView.addView(mLinearLaouyout);
            this.addView(mHorizaontalScorolView);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mHorizaontalScorolView.setOnScrollChangeListener(new OnScrollChangeListener() {
                    @Override
                    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                        mScrollX = scrollX;
                        moveIndicator(mCurrentSelPos);
                    }
                });
            }
        }
        /**
         * indicator
         */
        mIndicatorWidth = (int) ta.getDimension(R.styleable.widget_tab_layout_widget_widget_indicator_width, 30);
        mIndicatorHeight = (int) ta.getDimension(R.styleable.widget_tab_layout_widget_widget_indicator_height, 8);
        mIndicatorColor = ta.getColor(R.styleable.widget_tab_layout_widget_widget_indicator_color, Color.BLACK);
        mIndicatorBG = ta.getResourceId(R.styleable.widget_tab_layout_widget_widget_indicator_backgroud, -1);
        LayoutParams indicatorLp = new LayoutParams(mIndicatorWidth, mIndicatorHeight);
        indicatorLp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        mViewIndicator.setLayoutParams(indicatorLp);
        mViewIndicator.setBackgroundColor(mIndicatorColor);
        if (mIndicatorBG != -1) {
            mViewIndicator.setBackgroundResource(mIndicatorBG);
        }
        addView(mViewIndicator);
        ta.recycle();
    }

    public List<Tab> getTabList() {
        return mTabList;
    }

    public void setupWithViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                moveAnimation(mIndicatorMoveStartX+mInitStartX*positionOffset*2);
            }

            @Override
            public void onPageSelected(int position) {
                if (position != mCurrentSelPos) {
                    setTabSel(position, false);
                    if (null != mHorizaontalScorolView) {
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * indicator移动动画
     *
     * @param endX
     */
    private void moveAnimation(float endX) {
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.setFillAfter(true);
        TranslateAnimation translateAnimation = new TranslateAnimation(mIndicatorMoveStartX, endX, 0, 0);
        translateAnimation.setDuration(mDuration);
        animationSet.addAnimation(translateAnimation);
        mViewIndicator.startAnimation(animationSet);
        mIndicatorMoveStartX = endX;
    }

    /**
     * move indicator
     *
     * @param movePos 要移动的目的位置
     */
    private void moveIndicator(int movePos) {

        TextView tvMovePosTab = mTabList.get(movePos).getTvTab();   //被选中位置tab
        /**
         * 相对父控件左边的距离+控件本身的宽度的一半-Indicatoir一半的宽度-scrollview滑动的距离
         */
        float endX;  //通过控件先对父布局的位置来计算

        if (!mAutoPadding && movePos == 0) {
            endX = tvMovePosTab.getLeft() + (tvMovePosTab.getWidth() - (mTabLeftRightPadding) - mViewIndicator.getWidth()) / 2 - mScrollX;
        } else if (!mAutoPadding && movePos == mTabList.size() - 1) {
            endX = tvMovePosTab.getLeft() + (tvMovePosTab.getWidth() + (mTabLeftRightPadding) - mViewIndicator.getWidth()) / 2 - mScrollX;
        } else {
            endX = tvMovePosTab.getLeft() + (tvMovePosTab.getWidth() - mViewIndicator.getWidth()) / 2 - mScrollX;
        }
        moveAnimation(endX);
    }

    /**
     * set tablist
     *
     * @param titleList
     */
    public void setTabList(List<String> titleList) {
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        float width = windowManager.getDefaultDisplay().getWidth();  //屏幕宽度
        float relativeUIWidh = width / mUiBaseWidth;   //屏幕宽度跟UI设计宽度的相对比例
        for (int i = 0; i < titleList.size(); i++) {
            String title = titleList.get(i);
            Tab tab = new Tab(title);
            final TextView tvTab = new TextView(mContext);
            tvTab.setSingleLine();
            LinearLayout.LayoutParams tvLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            tvLp.gravity = Gravity.CENTER;
            tvTab.setText(title);
            tvTab.setGravity(Gravity.CENTER);
            tvTab.setTextColor(mTabTextColor);
            tvTab.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTabTextSize);
            if (mIsTabWeight) {
//                float weightWidth = width/titleList.size();    //屏幕宽度根据titleList平分的宽度
//                int leftRightMargin = (int) ((weightWidth - title.length()*mTabTextSize-mTabLeftRightPadding*2)/2);   //tab左右margin
//                tvLp.setMargins(leftRightMargin,0,leftRightMargin,0);
//                tvTab.setPadding(mTabLeftRightPadding,0,mTabLeftRightPadding,0);

                tvLp.width = 0;
                tvLp.weight = 1;
                tvTab.setPadding((mTabLeftRightPadding),
                        0,
                        (mTabLeftRightPadding),
                        0);
                tvTab.setLayoutParams(tvLp);
            } else {
                if (!mAutoPadding && i == 0) {
                    tvTab.setPadding(0, 0, mTabLeftRightPadding, 0);
                } else if (!mAutoPadding && i == titleList.size() - 1) {
                    tvTab.setPadding(mTabLeftRightPadding, 0, 0, 0);
                } else {
                    tvTab.setPadding(mTabLeftRightPadding, 0, mTabLeftRightPadding, 0);
                }
                tvLp.setMargins(mTabLeftRightMargin, 0, mTabLeftRightMargin, 0);
            }
            tvTab.setLayoutParams(tvLp);
            tab.setTvTab(tvTab);

            if (!mIsTabWeight) {
            }
            mLinearLaouyout.addView(tvTab);
            mTabList.add(tab);
            if (0 == i) {
                tab.setSel(true);
            }
            /**
             * tab click listener
             */
            final int finalI = i;
            tvTab.setOnClickListener(v -> {
                if (mCurrentSelPos == finalI) {
                    return;
                }
                setTabSel(finalI, true);
            });
        }
        float initStartX = 0f;   //计算指示器的初始化位置
        if (mIsTabWeight) {
            initStartX = width / mTabList.size() / 2f - relativeUIWidh * mIndicatorWidth / 2f;
        } else {
            Paint paint = new Paint();
            paint.setTextSize(mTabTextSize);
            int firstTextWidth = (int) paint.measureText(mTabList.get(0).getText());
            initStartX = (firstTextWidth - mViewIndicator.getWidth()) / 2;

        }

        moveAnimation(initStartX);
    }


    /**
     * @param tab
     */
    public void addTab(final Tab tab) {
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        float width = windowManager.getDefaultDisplay().getWidth();
        mTabList.add(tab);
        final int tabPos = mTabList.size() - 1;
        final TextView tvTab = new TextView(mContext);
        tab.setTvTab(tvTab);
        tab.setSel(tab.isSel);
        tvTab.setText(tab.text);
        tvTab.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams tvLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        tvLp.gravity = Gravity.CENTER;
        tvTab.setTextSize(mTabTextSize);
        if (mIsTabWeight) {
            for (Tab tab1 : mTabList) {
                float weightWidth = width / mTabList.size();
                int leftRightMargin = (int) ((weightWidth - tab.text.length() * sp2px(mTabTextSize) - 20) / 2);
                tvLp.setMargins(leftRightMargin, 0, leftRightMargin, 0);
                tab1.getTvTab().setPadding(mTabLeftRightPadding, 0, mTabLeftRightPadding, 0);
                tab1.getTvTab().setLayoutParams(tvLp);
            }
        } else {
            tvTab.setPadding(mTabLeftRightPadding, 0, mTabLeftRightPadding, 0);
            tvLp.setMargins(mTabLeftRightMargin, 0, mTabLeftRightMargin, 0);
        }
        tvTab.setLayoutParams(tvLp);
        mLinearLaouyout.addView(tvTab);
        /**
         * indicator init animations
         */
        float relativeUIWidh = width / mUiBaseWidth;   //屏幕宽度跟UI设计宽度的相对比例
        float initStartX = 0f;   //计算指示器的初始化位置
        if (mIsTabWeight) {
            initStartX = width / mTabList.size() / 2f - relativeUIWidh * mIndicatorWidth / 2f;
        } else {
            initStartX = mTabLeftRightMargin + mTabLeftRightPadding + mTabList.get(0).getText().length() * sp2px(mTabTextSize) / 2 - relativeUIWidh * mIndicatorWidth / 2f;
        }
        moveAnimation(initStartX);
        /**
         * tab click listener
         */
        tvTab.setOnClickListener(v -> {
            if (tab.isSel()) {
                return;
            }
            setTabSel(tabPos, true);
        });
    }

    /**
     * set tab selected
     *
     * @param selPos
     */
    public void setTabSel(int selPos, boolean updatePager) {
        setTabSel(selPos, updatePager, true);
    }

    public void setTabSel(int selPos, boolean updatePager, boolean callBack) {
        //        scrollToVisible(selPos);
        mTabList.get(mCurrentSelPos).setSel(false);
        mTabList.get(selPos).setSel(true);
        mCurrentSelPos = selPos;
        moveIndicator(mCurrentSelPos);
        if (null != mViewPager && updatePager) {
            mViewPager.setCurrentItem(selPos, false);
        }
        if (callBack && null != mTabSelectListener) {
            mTabSelectListener.onTabSelectListener(selPos);
        }
    }

    private void scrollToVisible(int selPos) {
        if (mAutoScroll) {
            TextView textView = mTabList.get(selPos).getTvTab();
            int viewWidth = getWidth();
            int selectedWidth = textView.getMeasuredWidth();
            int[] pos = new int[2];
            textView.getLocationOnScreen(pos);
            if (pos[0] > viewWidth) {
                mHorizaontalScorolView.smoothScrollTo(pos[0] - getPaddingLeft(), 0);
            } else if (pos[0] + selectedWidth > viewWidth) {
                mHorizaontalScorolView.smoothScrollBy(pos[0] + selectedWidth - viewWidth + getPaddingRight(), 0);
            } else if (pos[0] < getPaddingLeft()) {
                mHorizaontalScorolView.smoothScrollBy(pos[0] - getPaddingLeft(), 0);
            }
        }
    }

    /**
     * dp转px
     */
    public int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, mContext.getResources().getDisplayMetrics());
    }

    /**
     * sp转px
     */
    public int sp2px(float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, mContext.getResources().getDisplayMetrics());
    }

    private TabSelectListener mTabSelectListener;

    public void setOnTabSelectListener(TabSelectListener listener) {
        mTabSelectListener = listener;
    }

    public interface TabSelectListener {
        void onTabSelectListener(int pos);
    }

    public class Tab {
        private String text;
        private boolean isSel = false;
        private TextView tvTab;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
            tvTab.setText(text);
        }

        public boolean isSel() {
            return isSel;
        }

        /**
         * 文字大小缩放（缩小）
         *
         * @param scaleRate
         */
        private void scaleSmallAnimation(float scaleRate) {
            AnimationSet animationSet = new AnimationSet(true);
            animationSet.setFillAfter(true);
            ScaleAnimation scaleAnimation = new ScaleAnimation(scaleRate, 1.0f, scaleRate, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            scaleAnimation.setDuration(200);
            animationSet.addAnimation(scaleAnimation);
            tvTab.startAnimation(animationSet);
        }

        /**
         * 文字大小缩放（放大）
         *
         * @param scaleRate
         */
        private void scaleBigAnimation(float scaleRate) {
            AnimationSet animationSet = new AnimationSet(true);
            animationSet.setFillAfter(true);
            ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, scaleRate, 1.0f, scaleRate, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            scaleAnimation.setDuration(200);
            animationSet.addAnimation(scaleAnimation);
            tvTab.startAnimation(animationSet);
        }


        public void setSel(boolean sel) {
            isSel = sel;
            float scaleRate = mTabTextSelSize / mTabTextSize;
            if (isSel) {
                if (mBoldSelected) {
                    tvTab.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                }
                tvTab.setTextColor(mTabSelTextColor);
                tvTab.setBackgroundColor(mTabSelBackgroundColor);
                if (mIsTextAnimation) scaleBigAnimation(scaleRate);
            } else {
                tvTab.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tvTab.setTextColor(mTabTextColor);
                tvTab.setBackgroundColor(mTabBackgroundColor);
                if (mIsTextAnimation) scaleSmallAnimation(scaleRate);
            }
        }

        public TextView getTvTab() {
            return tvTab;
        }

        public void setTvTab(TextView tvTab) {
            this.tvTab = tvTab;
        }

        public Tab(String text, boolean sel) {
            this.text = text;
            this.isSel = sel;
        }

        public Tab(String text) {
            this.text = text;
        }


    }

    public void updateName(String name, int postion) {
        TextView textView = (TextView) mLinearLaouyout.getChildAt(postion);
        textView.setText(name);
        Tab tab = mTabList.get(postion);
        tab.setText(name);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (mTabList != null && mTabList.size() > 0) {
            scrollToVisible(mCurrentSelPos);
        }
        super.onLayout(changed, l, t, r, b);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        int height = MeasureSpec.getSize(heightMeasureSpec);

        View childView;
        int realWidth = 0;
        LayoutParams params;
        for (int i = 0; i < getChildCount(); i++) {
            childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            params = (LayoutParams) childView.getLayoutParams();
            realWidth = realWidth + childView.getMeasuredWidth() + params.leftMargin + params.rightMargin;
        }
        setMeasuredDimension(realWidth, height);
    }

}
