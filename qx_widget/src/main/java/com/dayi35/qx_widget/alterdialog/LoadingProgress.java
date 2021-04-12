package com.dayi35.qx_widget.alterdialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

import com.dayi35.qx_widget.R;


/**
 * @ClassName: LoadingProgress
 * @Description:
 * @Author: Administrator AKEE
 * @Date: 2018/10/24 15:03
 */
public class LoadingProgress extends ProgressDialog{

    public LoadingProgress(Context context) {
        super(context);
    }

    public LoadingProgress(Context context, int theme) {
        super(context, theme);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(getContext());
    }

    private void init(Context context) {
        //设置不可取消，点击其他区域不能取消，实际中可以抽出去封装供外包设置 setCancelable(false);
         setCanceledOnTouchOutside(false);
         setContentView(R.layout.widget_loading_progress);
         WindowManager.LayoutParams params = getWindow().getAttributes();
         params.width = WindowManager.LayoutParams.WRAP_CONTENT;
         params.height = WindowManager.LayoutParams.WRAP_CONTENT;
         getWindow().setAttributes(params);
    }

    @Override
    public void show() {
        super.show();
    }
}
