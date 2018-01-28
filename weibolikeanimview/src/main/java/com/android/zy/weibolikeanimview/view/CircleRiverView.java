package com.android.zy.weibolikeanimview.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.android.zy.weibolikeanimview.R;
import com.android.zy.weibolikeanimview.animation.AnimationHelper;

/**
 * Created by zy on 2018/1/22.
 *
 */

public class CircleRiverView extends View {
    private static final String TAG = "CircleRiverView";
    private static final float DEFAULT_LASER_INIT = 0.7f;//
    private static final float DEFAULT_RIVER_INIT = 0.2f;//
    private int mCircleCenterX = 0;     //
    private int mCircleCenterY = 0;     //
    private float mCircleRadius = 40;   //
    private Paint mCirclePaint;         //
    private Paint mSecondaryCirclePaint;//
    private Paint mLaserPaint;          //
    private Point[] mEndPoints;         //
    private Point[] mStartPoints;       //
    private float mLaserPercent = DEFAULT_LASER_INIT; //
    private float mRiverRadiusPercent = DEFAULT_RIVER_INIT;//

    private AnimationHelper mAnimationHelper;       //animation-helper

    public CircleRiverView(Context context) {
        this(context, null);
    }

    public CircleRiverView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleRiverView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * start river animation
     */
    public void startRiverAnimation(Animator.AnimatorListener callback) {
        ObjectAnimator laserAnimation =
                mAnimationHelper.generateLaserAnimation(
                        this,
                        600,
                        DEFAULT_LASER_INIT,
                        0.0f,
                        mLaserUpdateListener,
                        mLaserAnimatorListener);
        ObjectAnimator riverAnimation =
                mAnimationHelper.generateRiverAnimation(
                        this,
                        400,
                        DEFAULT_RIVER_INIT,
                        1.0f,
                        mRiverUpdateListener,
                        mRiverAnimatorListener
                );

        ObjectAnimator alphaAnimation =
                mAnimationHelper.generateAlphaAnimation(
                        this,
                        600,
                        0.0f,
                        1.0f,
                        0.0f
                );
        alphaAnimation.addListener(mAlphaAnimatorListener);
        alphaAnimation.addListener(callback);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(riverAnimation).with(alphaAnimation).after(100).with(laserAnimation);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();
    }

    private Animator.AnimatorListener mAlphaAnimatorListener =
            new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    setVisibility(VISIBLE);

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    setVisibility(GONE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            };

    private ValueAnimator.AnimatorUpdateListener mLaserUpdateListener =
            new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float ratio = (float) animation.getAnimatedValue();
                    mLaserPercent = ratio;
                    updateStartPoints();
                    invalidate();
                }
            };

    private ValueAnimator.AnimatorListener mLaserAnimatorListener =
            new ValueAnimator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    mLaserPercent = DEFAULT_LASER_INIT;
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            };

    private ValueAnimator.AnimatorUpdateListener mRiverUpdateListener =
            new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float ratio = (float) animation.getAnimatedValue();
                    mRiverRadiusPercent = ratio;
                    invalidate();
                }
            };
    private ValueAnimator.AnimatorListener mRiverAnimatorListener =
            new ValueAnimator.AnimatorListener() {

                @Override
                public void onAnimationStart(Animator animation) {
                    mRiverRadiusPercent = DEFAULT_RIVER_INIT;
                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            };


    private void init() {
//        this.setAlpha(0);
        mCirclePaint = new Paint();
        mCirclePaint.setColor(getResources().getColor(R.color.color_river_panel));
        mCirclePaint.setAlpha(30);

        mLaserPaint = new Paint();
        mLaserPaint.setColor(getResources().getColor(R.color.color_river_line));
        mLaserPaint.setStrokeCap(Paint.Cap.ROUND);
        mLaserPaint.setStrokeWidth(7);
        mLaserPaint.setAntiAlias(true);

        mSecondaryCirclePaint = new Paint();
        mSecondaryCirclePaint.setColor(getResources().getColor(R.color.color_river_panel));


        mEndPoints = new Point[6];
        for (int i = 1; i < mEndPoints.length; i++) {
            mEndPoints[i] = new Point();
        }

        mStartPoints = new Point[6];
        for (int i = 1; i < mStartPoints.length; i++) {
            mStartPoints[i] = new Point();
        }

        mAnimationHelper = new AnimationHelper();
    }

    /**
     *
     */
    private void calculateLaserPoints() {
        Point point1 = mEndPoints[1];
        point1.x = (int) (mCircleCenterX + mCircleRadius * Math.cos(Math.toRadians(15)));
        point1.y = (int) (Math.sin(Math.toRadians(15)) * mCircleRadius) + mCircleCenterY;
        Point point2 = mEndPoints[2];
        point2.x = (int) (mCircleRadius * Math.cos(Math.toRadians(-40))) + mCircleCenterX;
        point2.y = (int) (mCircleRadius * Math.sin(Math.toRadians(-40))) + mCircleCenterY;
        Point point3 = mEndPoints[3];
        point3.x = mCircleCenterX;
        point3.y = (int) (mCircleCenterY - mCircleRadius);
        Point point4 = mEndPoints[4];
        point4.x = (int) (mCircleRadius * Math.cos(Math.toRadians(-140))) + mCircleCenterX;
        point4.y = (int) (mCircleRadius * Math.sin(Math.toRadians(-140))) + mCircleCenterY;

        Point point5 = mEndPoints[5];
        point5.x = (int) (mCircleCenterX + mCircleRadius * Math.cos(Math.toRadians(-195)));
        point5.y = (int) (mCircleRadius * Math.sin(Math.toRadians(-195)) + mCircleCenterY);
        for (int i = 1; i < mStartPoints.length; i++) {
            mStartPoints[i].x = (int) ((mEndPoints[i].x - mCircleCenterX) * (1.0f - mLaserPercent) + mCircleCenterX);
            mStartPoints[i].y = (int) ((mEndPoints[i].y - mCircleCenterY) * (1.0f - mLaserPercent) + mCircleCenterY);
        }

    }

    private void updateStartPoints() {
        for (int i = 1; i < mStartPoints.length; i++) {
            mStartPoints[i].x = (int) ((mEndPoints[i].x - mCircleCenterX) * (1.0f - mLaserPercent) + mCircleCenterX);
            mStartPoints[i].y = (int) ((mEndPoints[i].y - mCircleCenterY) * (1.0f - mLaserPercent) + mCircleCenterY);
        }
    }


    public float getLaserPercent() {
        return mLaserPercent;
    }

    public void setLaserPercent(float mLaserPercent) {
        this.mLaserPercent = mLaserPercent;
    }


    public float getRiverRadiusPercent() {
        return mRiverRadiusPercent;
    }

    public void setRiverRadiusPercent(float mRiverRadiusPercent) {
        this.mRiverRadiusPercent = mRiverRadiusPercent;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        //1.draw the river circle
//        canvas.drawCircle(mCircleCenterX, mCircleCenterY, mCircleRadius, mCirclePaint);

        //2.draw secondary circle on top of the circle below
        canvas.drawCircle(mCircleCenterX, mCircleCenterY, mCircleRadius * mRiverRadiusPercent, mSecondaryCirclePaint);
        //3. draw the angled line,there are five lines,from 1 to 5 , the sweep angles are: 30,-45,-90,-135,-210
        for (int i = 1; i < mEndPoints.length && i < mStartPoints.length; i++) {
            canvas.drawLine(mStartPoints[i].x, mStartPoints[i].y, mEndPoints[i].x, mEndPoints[i].y, mLaserPaint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCircleCenterX = (int) (getMeasuredWidth() / 2);
        mCircleCenterY = (int) (getMeasuredHeight() / 2);
        if (getMeasuredWidth() < getMeasuredHeight()) {
            mCircleRadius = getMeasuredWidth() / 2;
        } else {
            mCircleRadius = getMeasuredHeight() / 2;
        }
        calculateLaserPoints();
        invalidate();
    }
}
