package com.brioal.movingview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.brioal.movingview.R;


/**中心大圆点
 * Created by Brioal on 2016/8/13.
 */

public class CenterDot extends View {
    private int mRadius; //半径
    private int mProgressTextColor; //进度文字颜色
    private int mBtnTextColor; //按钮文字颜色
    private int mProgressTextSize; //进度文字大小
    private Paint mPaint;
    private int mWidth; //组件宽度
    private int mYOffset; //上文字下按钮的偏移量
    private int mProgress; // 当前进度
    private int mMaxYOffset; //偏移量的最大值

    public CenterDot(Context context, int width) {
        this(context, null, width);
    }

    public CenterDot(Context context, AttributeSet attrs, int width) {
        super(context, attrs);
        mWidth = width;
        init();
    }

    //设置动画
    public void setAnimationPogress(float progress) {
        mYOffset = (int) (mMaxYOffset * (1 - progress));
        setScaleX((float) (1 - progress * 0.2));
        setScaleY((float) (1 - progress * 0.2));
        invalidate();
    }

    //设置文字
    public void setProgress(int progress) {
        this.mProgress = progress;
        invalidate();
    }

    //设置进度文字大小
    public void setProgressTextSize(int progressTextSize) {
        mProgressTextSize = progressTextSize;
    }

    //设置进度文字颜色
    public void setProgressTextColor(int progressTextColor) {
        mProgressTextColor = progressTextColor;
    }

    //设置按钮文字颜色
    public void setBtnTextColor(int btnTextColor) {
        mBtnTextColor = btnTextColor;
    }

    private void init() {
        mRadius = mWidth / 2;
        mMaxYOffset = mRadius / 2;
        mYOffset = mMaxYOffset;


        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        setBackgroundResource(R.drawable.ic_center_dot);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mWidth, mWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        String text = mProgress + "";
        Rect textBound = new Rect();
        canvas.save();
        canvas.translate(mWidth / 2, mWidth / 2);
        mPaint.setTextSize(mProgressTextSize);
        mPaint.setColor(mProgressTextColor);
        mPaint.getTextBounds(text, 0, text.length(), textBound);

        canvas.drawText(text, -(textBound.right + textBound.left) / 2, -(textBound.top + textBound.bottom) / 2 - mYOffset, mPaint);


        String percentText = "%";
        mPaint.setTextSize(mProgressTextSize/3);
        Rect percentBound = new Rect();
        mPaint.getTextBounds(percentText, 0, percentText.length(), percentBound);
        canvas.drawText(percentText, (textBound.right + textBound.left) / 2, -mYOffset, mPaint);


        Drawable btnDrawable = getResources().getDrawable(R.drawable.ic_btn_bg);
        String btnText = "加速";
        mPaint.setTextSize(mYOffset * 2 / 3);
        mPaint.setColor(getResources().getColor(R.color.colorPrimary));
        Rect btnTexBound = new Rect();
        mPaint.getTextBounds(btnText, 0, btnText.length(), btnTexBound);
        btnDrawable.setBounds(-mRadius / 2, mRadius - mMaxYOffset - mYOffset, mRadius / 2, mRadius - mMaxYOffset);
        btnDrawable.draw(canvas);
        int btnX = btnDrawable.getBounds().centerX();
        int btnY = btnDrawable.getBounds().centerY();
        canvas.save();
        canvas.translate(btnX, btnY);
        canvas.drawText(btnText, -(btnTexBound.right - btnTexBound.left) / 2, (btnTexBound.bottom - btnTexBound.top) / 2, mPaint);
        canvas.restore();

        canvas.restore();
    }
}
