package com.dayi35.recycle.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.dayi35.recycle.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljc on 2018/4/26.
 */

public abstract class BaseRVAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    protected final int ITEM_TYPE_FOOTER = -1;
    protected final int ITEM_TYPE_DATA = 1;
    protected final int ITEM_TYPE_HEAD = 0;
    protected List<T> mDataList = new ArrayList<>();
    protected Context mContext;
    private LayoutInflater mInflater;

    private boolean mShowFooter = true;
    private boolean mShowHead = false;

    protected int mFromType;

    protected int mTotleSize;   //数据总量
    /**
     * 是否还有下一页
     */
    private boolean mHasNextPage = true;

    public boolean hasNextPage() {
        return mHasNextPage;
    }

    public void setNextPage(boolean mHasNextPage) {
        this.mHasNextPage = mHasNextPage;
    }

    public void setFromType(int fromType) {
        mFromType = fromType;
    }

    protected onItmeClickListener<T> mItemClickListener;
    protected onItmeLongClickListener<T> mItemLongClickListener;

    public BaseRVAdapter(Context context, List<T> list) {
        mContext = context;
        mDataList = list;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setShowHead(boolean showHead) {
        mShowHead = showHead;
    }

    public boolean isShowHead() {
        return mShowHead;
    }

    public void setShowFooter(boolean showFooter) {
        this.mShowFooter = showFooter;
    }

    public boolean isShowFooter() {
        return mShowFooter;
    }

    /**
     * item 点击监听
     *
     * @param listener
     */
    public void setOnItemLongClickListener(onItmeLongClickListener<T> listener) {
        mItemLongClickListener = listener;
    }

    /**
     * item 长按监听
     *
     * @param listener
     */
    public void setOnClickListener(onItmeClickListener<T> listener) {
        mItemClickListener = listener;
    }

    protected T getItme(int pos) {
        if (getItemCount() >= pos + 1) {
            return mDataList.get(pos);
        } else {
            return null;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mShowFooter) {
            if (position + 1 == getItemCount()) {
                return ITEM_TYPE_FOOTER;
            } else {
                if (mShowHead) {
                    if (0 == position) {
                        return ITEM_TYPE_HEAD;
                    } else {
                        return ITEM_TYPE_DATA;
                    }
                } else {
                    return ITEM_TYPE_DATA;
                }
            }
        } else {
            if (mShowHead) {
                if (0 == position) {
                    return ITEM_TYPE_HEAD;
                } else {
                    return ITEM_TYPE_DATA;
                }
            } else {
                return ITEM_TYPE_DATA;
            }
        }
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case ITEM_TYPE_HEAD: {
                itemView = mInflater.inflate(getHeadLayout(), parent, false);
            }
            break;
            case ITEM_TYPE_DATA: {
                itemView = mInflater.inflate(getItemLayout(), parent, false);
            }
            break;
            case ITEM_TYPE_FOOTER: {
                itemView = mInflater.inflate(R.layout.rv_footer, parent, false);
            }
            break;
            default: {
                itemView = mInflater.inflate(getItemLayout(), parent, false);
            }
            break;
        }
        return new BaseViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        int begin = mShowFooter ? 1 : 0;
        if (mDataList == null) {
            return begin;
        }
        return mDataList.size() + begin;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (ITEM_TYPE_HEAD == holder.getItemViewType()) {
            bindHead(holder, position);
        } else if (ITEM_TYPE_DATA == holder.getItemViewType()) {
            bindItem(holder, position);
        } else if (ITEM_TYPE_FOOTER == holder.getItemViewType()) {
            bindFooter(holder, position);
        }
    }

    /**
     * item 数据绑定
     *
     * @param holder
     * @param position
     */
    protected abstract void bindItem(BaseViewHolder holder, int position);

    /**
     * head数据绑定
     *
     * @param holder
     * @param position
     */
    protected void bindHead(BaseViewHolder holder, int position) {

    }

    /**
     * Footer数据绑定
     *
     * @param holder
     * @param position
     */
    protected void bindFooter(BaseViewHolder holder, int position) {
        TextView tvMoreData = holder.getView(R.id.more_data_msg);
        ProgressBar pb = holder.getView(R.id.pb);
        if (mTotleSize != 0 && mDataList.size() > 0 && mDataList.size() >= mTotleSize) {
            pb.setVisibility(View.GONE);
            tvMoreData.setText(R.string.rv_no_more_data);
        }
    }

    /**
     * 获取item 布局
     *
     * @return itemlayout
     */
    protected abstract int getItemLayout();

    /**
     * 获取头部布局
     *
     * @return
     */
    protected int getHeadLayout() {
        return 0;
    }

    public void addItme(int pos, T item) {
        mDataList.add(pos, item);
        notifyItemInserted(pos);
    }

    public void removeItem(int pos) {
        if (mDataList.size() >= pos + 1) {
            mDataList.remove(pos);
            notifyItemRemoved(pos);
        }
    }

    /**
     * 更新某个位置的数据
     */
    public void updateItem(T data, int position) {
        mDataList.remove(position);
        mDataList.add(position, data);
        notifyDataSetChanged();
    }

    /**
     * 添加list
     *
     * @param list
     * @param refresh 是否更新(移除)之前的列表项
     */
    public void addAll(List<T> list, boolean refresh) {
        addAll(list, refresh, true);
    }

    /**
     * 添加list
     *
     * @param list
     * @param refresh    是否更新(移除)之前的列表项
     * @param showFooter 是否需要footer
     */
    public void addAll(List<T> list, boolean refresh, boolean showFooter) {
        if (refresh) {
            mShowFooter = showFooter;
            mDataList.clear();
        }
        mDataList.addAll(list);
        notifyDataSetChanged();
    }

    public void setTotleSize(int size) {
        mTotleSize = size;
    }

    public interface onItmeClickListener<T> {
        void onItmeClick(int pos, T map);
    }

    public interface onItmeLongClickListener<T> {
        void onItmeLongClick(int pos, T v);
    }

}
