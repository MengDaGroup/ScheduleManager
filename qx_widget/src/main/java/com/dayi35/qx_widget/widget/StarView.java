package com.dayi35.qx_widget.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.dayi35.qx_widget.R;


/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2020/5/26 11:10
 * 描    述: 类 自定义评价星级
 *          调用示例
 *              <com.dayi35.nk.business.mine.widget.StarView
 *                 android:layout_width="wrap_content"
 *                 android:layout_height="wrap_content"
 *                 app:starWidth ="24dp"
 *                 app:starHeight ="24dp"
 *                 app:starsNum ="5"
 *                 app:starDistance ="14dp"
 *                 app:starBackground ="@drawable/widget_ic_star_defocus"
 *                 app:starDrawBackground ="@drawable/widget_ic_star_focusing"
 *                 app:starClickable ="true"
 *                 />
 *                 starBackground 与 starDrawBackground 不为空
 * 修订历史:
 * =========================================
 */

public class StarView extends View {
    //评分
    private int starMark = 0;
    //个数
    private int starNum = 5;
    //高度
    private int starHeight ;
    //宽度
    private int starWidth ;
    //间距
    private int starDistance ;
    //未选中状态
    private Drawable starBackgroundBitmap;
    //选中状态
    private Bitmap starDrawDrawable;
    //变化监听
    private OnStarChangeListener changeListener;
    //是否可以点击
    private boolean isClick = true;
    private boolean reCanvas = true;
    //画笔
    private Paint mPaint;

    public StarView(Context mContext, AttributeSet attrs) {
        super(mContext, attrs);
        init(mContext, attrs);
    }

    public StarView(Context context) {
        super(context);
    }

    public StarView(Context mContext, AttributeSet attrs, int defStyleAttr) {
        super(mContext, attrs, defStyleAttr);
        init(mContext, attrs);
    }

    private void init(Context mContext, AttributeSet attrs){

        //初始化控件属性
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs,R.styleable.widget_star);
        starNum = typedArray.getInteger(R.styleable.widget_star_widget_stars_num, 5);
        starHeight = (int) typedArray.getDimension(R.styleable.widget_star_widget_star_height, 0);
        starWidth = (int) typedArray.getDimension(R.styleable.widget_star_widget_star_width, 0);
        starDistance = (int) typedArray.getDimension(R.styleable.widget_star_widget_star_distance, 0);
        isClick = typedArray.getBoolean(R.styleable.widget_star_widget_star_clickable,true);
        starBackgroundBitmap = typedArray.getDrawable(R.styleable.widget_star_widget_star_background);
        starDrawDrawable = drawableToBitmap(typedArray.getDrawable(R.styleable.widget_star_widget_star_drawbackground));
        typedArray.recycle();
        setClickable(isClick);
        //初始化画笔
        mPaint = new Paint();
        //设置抗锯齿
        mPaint.setAntiAlias(true);
        mPaint.setShader(new BitmapShader(starDrawDrawable,BitmapShader.TileMode.CLAMP,BitmapShader.TileMode.CLAMP));
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(starWidth*starNum+starDistance*(starNum-1),starHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (null==starDrawDrawable||null==starBackgroundBitmap){
            return;
        }
        for (int i = 0;i<starNum;i++) {
            starBackgroundBitmap.setBounds(starDistance * i + starWidth * i, 0, starWidth * (i + 1) + starDistance * i, starHeight);
            starBackgroundBitmap.draw(canvas);
        }
        if (starMark > 1) {
            canvas.drawRect(0, 0, starWidth, starHeight, mPaint);
            for (int i = 1; i < starMark; i++) {
                canvas.translate(starDistance + starWidth, 0);
                canvas.drawRect(0, 0, starWidth, starHeight, mPaint);
            }
        }else {
            canvas.drawRect(0, 0, starWidth * starMark, starHeight, mPaint);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        if (x <= 0)
            x = (int) (getMeasuredWidth()*1.0f/starNum);
        if (x > getMeasuredWidth())
            x = getMeasuredWidth();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if (reCanvas) {
                    setMark(x * 1.0f / (getMeasuredWidth() * 1.0f / starNum));
                }
                break;
        }
        return true;
    }

    /**
     * 设置分数
     */
    public void setMark(Float mark){
        starMark = Math.round(mark);
        if (starMark <= 1){
            starMark = 1;
        }
        if (null!=changeListener){
            changeListener.onStarChange(this,starMark);
        }
        invalidate();
    }

    //是否重绘
    public void setReCanvas(boolean reCanvas) {
        this.reCanvas = reCanvas;
    }

    /**
     * 设置监听
     */
    public void setStarChangeLister(OnStarChangeListener starChangeLister){
        changeListener = starChangeLister;
    }

    /**
     * 获取分数
     */
    public float getMark(){
        return starMark;
    }

    /**
     * 星星数量变化监听接口
     */
    public interface OnStarChangeListener{
        void onStarChange(View v, int mark);
    }

    /**
     * drawable转bitmap
     */
    private Bitmap drawableToBitmap(Drawable drawable)
    {
        if (drawable == null)return null;
        Bitmap bitmap = Bitmap.createBitmap(starWidth, starHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, starWidth, starHeight);
        drawable.draw(canvas);
        return bitmap;
    }

}
