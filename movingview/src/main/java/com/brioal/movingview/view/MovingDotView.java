package com.brioal.movingview.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.brioal.movingview.R;
import com.brioal.movingview.exceptions.SizeNotDeterminedException;
import com.brioal.movingview.listener.OnAnimatorChangeListener;
import com.brioal.movingview.util.SizeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 小球移动类
 * Created by Brioal on 2016/8/12.
 */

public class MovingDotView extends ViewGroup {
    private List<Dot> mDots; //存放小点的List
    private int mDotsCount; //小点总数
    private int mCenterDotRadius; //中心大点的半径
    private Drawable mCenterDotRes; //中心大点的资源文件
    private int mDotColor; //小点的颜色
    private Paint mPaint;
    private int mMaxDotRadius; //最大小点半径
    private int mMinDotRadius; //最小小点半径
    private int mMaxDotSpeed; //最大小点速度
    private int mMinDotSpeed; //最小小点速度
    private int mWidth;
    private int mProgress; //当前进度
    private int mTextSize; //进度文字大小
    private int mTextColor; //进度文字颜色
    private int mBtnTextColor; //按钮上文字的颜色
    private long mAnimatorDuration; //动画持续的时间
    private CenterDot mCenterDot; //中心点组件
    private OnAnimatorChangeListener mChangeListener; //动画改变监听器

    public void setChangeListener(OnAnimatorChangeListener changeListener) {
        mChangeListener = changeListener;
    }

    public MovingDotView(Context context) {
        this(context, null);
    }

    public MovingDotView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(true);
        initObtainStyled(context, attrs);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        getChildAt(0).layout(getWidth() / 2 - mCenterDotRadius, getWidth() / 2 - mCenterDotRadius, getWidth() / 2 + mCenterDotRadius, getWidth() / 2 + mCenterDotRadius);
    }


    private void initObtainStyled(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MovingDotView);
        mDotsCount = array.getInteger(R.styleable.MovingDotView_md_dot_count, 10);
        mCenterDotRadius = (int) array.getDimension(R.styleable.MovingDotView_md_center_dot_radius, 0);
        mCenterDotRes = array.getDrawable(R.styleable.MovingDotView_md_center_dot_back);
        mDotColor = array.getColor(R.styleable.MovingDotView_md_dot_color, getResources().getColor(R.color.colorPrimary));
        mMaxDotRadius = (int) array.getDimension(R.styleable.MovingDotView_md_dot_max_radius, SizeUtil.Dp2Px(getContext(), 10));
        mMinDotRadius = (int) array.getDimension(R.styleable.MovingDotView_md_dot_min_radius, SizeUtil.Dp2Px(getContext(), 5));
        mMaxDotSpeed = array.getInteger(R.styleable.MovingDotView_md_dot_max_speed, 10);
        mMinDotSpeed = array.getInteger(R.styleable.MovingDotView_md_dot_min_speed, 1);
        mTextSize = (int) array.getDimension(R.styleable.MovingDotView_md_text_size, SizeUtil.Sp2Px(getContext(), 70));
        mBtnTextColor = array.getColor(R.styleable.MovingDotView_md_btn_text_color, getResources().getColor(R.color.colorPrimary));
        mAnimatorDuration = array.getInteger(R.styleable.MovingDotView_md_animator_duration, 1500);
        mTextColor = array.getColor(R.styleable.MovingDotView_md_text_color, Color.WHITE);
        array.recycle();

        mDots = new ArrayList<>();
        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mProgress = 50;
        setBackgroundColor(Color.WHITE);

    }

    public void setProgress(int progress) {
        mProgress = progress;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //假设情况是用户至少会给宽或者高指定一个确定的值,否则抛出异常
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode != MeasureSpec.EXACTLY && heightMode != MeasureSpec.EXACTLY) {
            try {
                throw new SizeNotDeterminedException("宽高不能都为wrap_content");
            } catch (SizeNotDeterminedException e) {
                e.printStackTrace();
            }
        }

        mWidth = Math.min(widthSize, heightSize);
        setMeasuredDimension(mWidth, mWidth);
        Dot.WIDTH = mWidth;
        Dot.SPEED = mMinDotSpeed;
        Dot.sMaxDotRadius = mMaxDotRadius;
        Dot.sMinDotRadius = mMinDotRadius;
    }

    @Override
    protected void onSizeChanged(final int w, final int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mCenterDotRadius == 0) {
            mCenterDotRadius = w / 4;
        }
        for (int i = 0; i < mDotsCount; i++) {
            mDots.add(new Dot());
        }
        mCenterDot = new CenterDot(getContext(), mCenterDotRadius * 2);
        mCenterDot.setClickable(true);
        mCenterDot.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isCleaned) {
                    startClean();
                }
            }
        });
        if (mCenterDotRes == null) {
            mCenterDotRes = getResources().getDrawable(R.drawable.ic_center_dot);
        }
        mCenterDot.setBackground(mCenterDotRes);
        mCenterDot.setProgressTextSize(mTextSize);
        mCenterDot.setProgressTextColor(mTextColor);
        mCenterDot.setBtnTextColor(mBtnTextColor);
        mCenterDot.setProgress(mProgress);
        addView(mCenterDot);
    }

    private int toProgress;

    public void setToProgress(int toProgress) {
        this.toProgress = toProgress;
    }

    public void startClean() {
        startAnimation(0, 1, toProgress);
        isCleaned = true;
    }

    public void backClean() {
        startAnimation(0.9f, 0, toProgress);
        isCleaned = false;
    }

    public void startAnimation(float from, float to, final int toProgress) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(from, to);
        valueAnimator.setDuration(mAnimatorDuration);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float progress = (float) valueAnimator.getAnimatedValue();
                if (mChangeListener != null) {
                    mChangeListener.onProgressChanged(progress);
                }
                Dot.SPEED = (int) (mMinDotSpeed + progress * (mMaxDotSpeed - mMinDotSpeed));
                mCenterDot.setAnimationPogress(progress);
                mCenterDot.setProgress((int) (mProgress + progress * (toProgress - mProgress)));
            }
        });
        valueAnimator.start();

    }

    private boolean isCleaned = false;

    public boolean isCleaned() {
        return isCleaned;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mDotColor);
        canvas.save();
        canvas.translate(getWidth() / 2, getHeight() / 2);
        for (int j = 0; j < mDots.size(); j++) {
            Dot dot = mDots.get(j);
            float progress = (float) ((dot.getZ() - mCenterDotRadius) / (new Dot(-getWidth() / 2, -getWidth() / 2, 0).getZ() - mCenterDotRadius));//1~0
            if (progress > 1) {
                progress = 1;
            }
            if (progress < 0) {
                progress = 0;
            }
            int alpha = (int) ((1 - progress) * 200 + 75);
            mPaint.setAlpha(alpha > 255 ? 255 : alpha);
            canvas.drawCircle(dot.getX(), dot.getY(), dot.getRadius(), mPaint);
            dot.checkAndChange();
        }
        postInvalidateDelayed(10);
        canvas.restore();

    }

}
