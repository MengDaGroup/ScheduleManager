package com.dayi35.qx_widget.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.dayi35.qx_widget.R;


/**
 * Created by hyl on 2018/4/18.
 */


/**
 * 四个角为圆角的imageview
 */
public class CircularCornerImageView extends ImageView {

    //圆角弧度
    private float[] mRids = new float[8];

    public CircularCornerImageView(Context context) {
        super(context);
    }

    public CircularCornerImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        readAttrs(context, attrs);
    }

    public CircularCornerImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        readAttrs(context, attrs);
    }

    public CircularCornerImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        readAttrs(context, attrs);
    }


    private void readAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.widget_cricular_imageview);
        mRids[0] = typedArray.getDimension(R.styleable.widget_cricular_imageview_widget_topleft1_radius, 0.0f);
        mRids[1] = typedArray.getDimension(R.styleable.widget_cricular_imageview_widget_topright1_radius, 0.0f);
        mRids[2] = typedArray.getDimension(R.styleable.widget_cricular_imageview_widget_bottomleft1_radius, 0.0f);
        mRids[3] = typedArray.getDimension(R.styleable.widget_cricular_imageview_widget_bottomright1_radius, 0.0f);

        mRids[4] = typedArray.getDimension(R.styleable.widget_cricular_imageview_widget_topleft2_radius, 0.0f);
        mRids[5] = typedArray.getDimension(R.styleable.widget_cricular_imageview_widget_topright2_radius, 0.0f);
        mRids[6] = typedArray.getDimension(R.styleable.widget_cricular_imageview_widget_bottomleft2_radius, 0.0f);
        mRids[7] = typedArray.getDimension(R.styleable.widget_cricular_imageview_widget_bottomright2_radius, 0.0f);
        typedArray.recycle();
    }

    protected void onDraw(Canvas canvas) {
        Path path = new Path();
        int w = this.getWidth();
        int h = this.getHeight();
        //绘制圆角imageview
        path.addRoundRect(new RectF(0, 0, w, h), mRids, Path.Direction.CW);
        canvas.clipPath(path);
        super.onDraw(canvas);
    }



}

