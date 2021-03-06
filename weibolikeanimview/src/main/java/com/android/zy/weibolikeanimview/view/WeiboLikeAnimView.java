package com.android.zy.weibolikeanimview.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.zy.weibolikeanimview.R;
import com.android.zy.weibolikeanimview.animation.AnimationHelper;


/**
 * Created by zy on 2018/1/22.
 */
public class WeiboLikeAnimView extends RelativeLayout {
    private int mRiverRadius = LENGHT_RIVER_RADIUS_DEFAULT;
    private final static int LENGHT_RIVER_RADIUS_DEFAULT = 0; //default river radius
    private int mLikeViewLength = LENGTH_THUMB_DEFAULT;
    private int mThumbLength = LENGTH_THUMB_DEFAULT;
    private final static int LENGTH_THUMB_DEFAULT = 50;        //default thumb length

    private ImageView ivThumb;
    private ImageView ivLikePlus;
    private CircleRiverView riverView;
    private RelativeLayout.LayoutParams mLpIvThumb;
    private RelativeLayout.LayoutParams mLpRiverView;
    private RelativeLayout.LayoutParams mLpLikeView;

    private AnimationHelper animationHelper;

    private boolean mIsLiked;//点赞的初始状态，是否已经点赞
    private boolean misRepeatLikeEnable = false;//

    public WeiboLikeAnimView(Context context) {
        this(context, null);
    }

    public WeiboLikeAnimView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeiboLikeAnimView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray =
                context.obtainStyledAttributes(attrs, new int[]{android.R.attr.layout_width}, defStyleAttr, 0);
        mThumbLength = (int) typedArray.getLayoutDimension(0, null);
        typedArray =
                context.obtainStyledAttributes(attrs, R.styleable.WeiboLikeAnimView, 0, 0);
        mIsLiked = typedArray.getBoolean(R.styleable.WeiboLikeAnimView_likeview_isLiked, false);
        mRiverRadius = mThumbLength;
        mLikeViewLength = mThumbLength / 2;
        typedArray.recycle();
        init();
    }

    private Animator.AnimatorListener mThumbAnimListener = new Animator.AnimatorListener() {
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
    };

    private Animator.AnimatorListener mPlusViewAnimListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {
            ivLikePlus.setVisibility(VISIBLE);
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            ivLikePlus.setVisibility(GONE);
            if (misRepeatLikeEnable) {
                setClickable(true);
            }
            setmIsLiked(true);
            ivThumb.setImageResource(R.drawable.video_interact_like_highlight);
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    private void init() {
//        setViewSurroundingOffset();
        setBackgroundColor(getResources().getColor(android.R.color.white));
        animationHelper = new AnimationHelper();

        //add circle-river-view
        riverView = new CircleRiverView(getContext());
        mLpRiverView = new RelativeLayout.LayoutParams(mRiverRadius, mRiverRadius);
        mLpRiverView.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        mLpRiverView.addRule(RelativeLayout.CENTER_HORIZONTAL);
        mLpRiverView.bottomMargin = mRiverRadius / 2;
        addView(riverView, mLpRiverView);

        //init thumb view
        int ivThumbPadding = (int) (Math.sin(Math.toRadians(10)) * mThumbLength);
        ivThumb = new ImageView(getContext());
        ivThumb.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        ivThumb.setScaleType(ImageView.ScaleType.FIT_XY);
        ivThumb.setImageResource(R.drawable.video_interact_like);
        ivThumb.setPadding(ivThumbPadding, ivThumbPadding, ivThumbPadding, ivThumbPadding);
        mLpIvThumb = new RelativeLayout.LayoutParams(mThumbLength, mThumbLength);
        mLpIvThumb.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        mLpIvThumb.addRule(RelativeLayout.CENTER_HORIZONTAL);
        mLpIvThumb.bottomMargin = 0;
        addView(ivThumb, mLpIvThumb);

        //init the plus-one view
        ivLikePlus = new ImageView(getContext());
        ivLikePlus.setVisibility(INVISIBLE);
        ivLikePlus.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ivLikePlus.setImageResource(R.drawable.like_anim_plus_one);
        mLpLikeView = new RelativeLayout.LayoutParams(mLikeViewLength, mLikeViewLength);
        mLpLikeView.addRule(RelativeLayout.CENTER_HORIZONTAL);
        mLpLikeView.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        mLpLikeView.bottomMargin = mLikeViewLength * 2;
        addView(ivLikePlus, mLpLikeView);

        riverView.setVisibility(View.GONE);
//        ivLikePlus.setVisibility(View.GONE);

        //init state
        if (mIsLiked) {
            ivThumb.setImageResource(R.drawable.video_interact_like_highlight);
            setClickable(false);
        } else {
            ivThumb.setImageResource(R.drawable.video_interact_like);
            setClickable(true);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w < h) {
            setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), ivThumb.getMeasuredHeight() / 8);
//            setViewSurroundingOffset();
            removeView(riverView);
            mLpRiverView.width = w;
            mLpRiverView.height = w;
            mLpRiverView.bottomMargin = w / 2;
            mRiverRadius = w;
            addView(riverView, mLpRiverView);

            mThumbLength = w;
            removeView(ivThumb);
            mLpIvThumb.width = mThumbLength;
            mLpIvThumb.height = mThumbLength;
            addView(ivThumb, mLpIvThumb);

            //resize plus view
            mLikeViewLength = w / 2;
            removeView(ivLikePlus);
            mLpLikeView.width = mLikeViewLength;
            mLpLikeView.height = mLikeViewLength;
            mLpLikeView.bottomMargin = w;
            addView(ivLikePlus, mLpLikeView);


            invalidate();
        }
    }

    private void setViewSurroundingOffset() {
        int padding = (int) (Math.sin(Math.toRadians(15)) * mThumbLength);
        setPadding(padding, padding, padding, padding);
    }


    public void startAnim() {
        setClickable(false);
        final AnimatorSet thumbAnimatorSet =
                animationHelper.generateThumbAnimation(ivThumb, 600, 600);
        final AnimatorSet likePlusViewAnimatorSet = new AnimatorSet();
        //generate plus-view animation
        ObjectAnimator plusViewTranslateAnim =
                animationHelper.generateTranslateAnimation(ivLikePlus, 1200, 0, -ivLikePlus.getTop());
        ObjectAnimator plusViewScaleXAnim =
                animationHelper.generateScaleAnimation(ivLikePlus, 500, 1.0f, 1.5f, 1.0f, true);
        ObjectAnimator plusViewScaleYAnim =
                animationHelper.generateScaleAnimation(ivLikePlus, 500, 1.0f, 1.5f, 1.0f, false);
        ObjectAnimator plusViewAlphaAnim =
                animationHelper.generateAlphaAnimation(ivLikePlus, 600, 0.5f, 1.0f, 0.0f);
        plusViewAlphaAnim.addListener(mPlusViewAnimListener);

        thumbAnimatorSet.start();
        likePlusViewAnimatorSet.
                playTogether(plusViewAlphaAnim, plusViewScaleXAnim, plusViewScaleYAnim, plusViewTranslateAnim);
        if (riverView != null) {
            riverView.startRiverAnimation(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    likePlusViewAnimatorSet.start();
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
        }
    }

    public void reset() {
        mIsLiked = false;
        setClickable(true);
        ivThumb.setImageResource(R.drawable.video_interact_like);
    }

    public void setIsRepeatLikeEnable(boolean enable) {
        misRepeatLikeEnable = enable;
    }

    public boolean ismIsLiked() {
        return mIsLiked;
    }

    private void changeThumbResource() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(1);
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (mIsLiked) {
//                    ivThumb.setImageResource(0);
                    if (mIsLiked) {
                        setClickable(true);
                        ivThumb.setImageResource(R.drawable.video_interact_like_highlight);

                    } else {
                        ivThumb.setImageResource(R.drawable.video_interact_like);
                        setClickable(false);
                    }
/*                    removeView(ivThumb);
                    addView(ivThumb,mLpIvThumb);
                    ivThumb.invalidate();*/
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.start();
    }


    private void changethumbState() {
        ivThumb.post(new Runnable() {
            @Override
            public void run() {
                if (mIsLiked) {
                    ivThumb.setImageResource(0);
                    if (mIsLiked) {
                        setClickable(true);
                        ivThumb.setImageResource(R.drawable.video_interact_like_highlight);

                    } else {
                        ivThumb.setImageResource(R.drawable.video_interact_like);
                        setClickable(false);
                    }
                }
            }
        });
    }


    public void setmIsLiked(final boolean mIsLiked) {
        this.mIsLiked = mIsLiked;
        changethumbState();
    }

}
