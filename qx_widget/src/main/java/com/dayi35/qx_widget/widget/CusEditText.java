package com.dayi35.qx_widget.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;

import com.dayi35.qx_widget.R;


/**
 * Created by ljc on 2018/3/28.
 * 自定义editView
 * 1:右边添加清除按钮
 * 2:添加内容监听功能
 */

public class CusEditText extends AppCompatEditText implements View.OnFocusChangeListener, TextWatcher {
    /**
     * 是否获取焦点
     */
    private boolean mHasFocus;
    /**
     * 右边清除按钮
     */
    private Drawable mClearDrawable;
    /**
     * 观察者（观察输入内容，做出响应的控件）
     */
    private View mObserverView;
    /**
     * 观察输入的长度
     */
    private int mObservedLenght;

    public CusEditText(Context context) {
        super(context);
        init();
    }

    public CusEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CusEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // getCompoundDrawables() Returns drawables for the left(0), top(1), right(2) and bottom(3)
        mClearDrawable = getCompoundDrawables()[2]; // 获取drawableRight
        if (mClearDrawable == null) {
            // 如果为空，即没有设置drawableRight，则使用R.mipmap-xhdpi.close这张图片
            mClearDrawable = getResources().getDrawable(R.mipmap.widget_ic_inputclean);
        }
        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());
        setOnFocusChangeListener(this);
        addTextChangedListener(this);
        // 默认隐藏图标
        setDrawableVisible(false);
    }

    protected void setDrawableVisible(boolean visible) {
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }

    /**
     * 我们无法直接给EditText设置点击事件，只能通过按下的位置来模拟clear点击事件
     * 当我们按下的位置在图标包括图标到控件右边的间距范围内均算有效
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {
                int start = getWidth() -  getTotalPaddingRight() ; // 起始位置
                int end = getWidth(); // 结束位置
                boolean available = (event.getX() > start) && (event.getX() < end);
                if (available) {
                    this.setText("");
                }
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.mHasFocus = hasFocus;
        if (hasFocus && getText().length() > 0) {
            setDrawableVisible(true); // 有焦点且有文字时显示图标
        } else {
            setDrawableVisible(false);
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int count, int after) {
        if (mHasFocus) {
            setDrawableVisible(s.length() > 0);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void afterTextChanged(Editable s) {
        if(null!=mObserverView){
            if(TextUtils.isEmpty(s.toString())){
                mObserverView.setEnabled(false);
            }else if(s.toString().length()<mObservedLenght){
                mObserverView.setEnabled(false);
            }else {
                mObserverView.setEnabled(true);
            }
        }
        if(null!=mListener){
            mListener.onTextChanged(s.toString());
        }
    }
    /**
     * 添加内容监听
     * @param view
     * @param lenght
     */
    public void addContentListenerForView(View view, int lenght){
        this.mObserverView = view;
        this.mObservedLenght = lenght;
    }

    private TextChangeListener mListener;

    public void setOnTextChangedListener(TextChangeListener listener){
        mListener = listener;
    }

    public interface TextChangeListener{
        void onTextChanged(String text);
    }
}
