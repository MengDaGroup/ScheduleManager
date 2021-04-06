package com.dayi35.recycle.adapter;

import android.util.SparseArray;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by ljc on 2018/4/24.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {

    private final SparseArray<View> mSparseArray;
    private boolean mInited = false;

    public BaseViewHolder(View itemView) {
        super(itemView);
        mSparseArray = new SparseArray<>();
    }

    public <T extends View> T getView(int id) {
        View view = mSparseArray.get(id);
        if (view == null) {
            view = itemView.findViewById(id);
            mSparseArray.put(id, view);
        }

        return (T) view;
    }

    public boolean isInited(){
        return mInited;
    }

    public void inited(){
          mInited=true;
    }
}
