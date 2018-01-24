package com.android.zy.weibolikeanimview.animation;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.android.zy.weibolikeanimview.view.CircleRiverView;

/**
 * Created by hp on 2018/1/22.
 * a helper class to generate animations
 */

public class AnimationHelper {
    /**
     * perform the scale&rotate animation
     */
    public AnimatorSet generateThumbAnimation(@NonNull View thumbView,
                                              long scaleDuration,
                                              long rotateDuration) {
        AnimatorSet animatorSet = new AnimatorSet();
        Animator animatorX
                = generateScaleAnimation(thumbView, scaleDuration, 1.0f, 1.5f, 1.0f, true);
        Animator animatorY
                = generateScaleAnimation(thumbView, scaleDuration, 1.0f, 1.5f, 1.0f, false);
        Animator animatorRotate
                = ObjectAnimator.ofFloat(thumbView, "rotation", 0f, -15f, 0f);

        Interpolator interpolator = new LinearInterpolator();
        animatorX.setInterpolator(interpolator);
        animatorY.setInterpolator(interpolator);
        animatorX.setDuration(scaleDuration);
        animatorY.setDuration(scaleDuration);
        animatorRotate.setDuration(rotateDuration);
        animatorSet.playTogether(animatorX, animatorY, animatorRotate);
        return animatorSet;
    }

    public ObjectAnimator generateLaserAnimation(CircleRiverView riverView,
                                                 long duration,
                                                 float from,
                                                 float to,
                                                 ValueAnimator.AnimatorUpdateListener updateListener,
                                                 Animator.AnimatorListener animatorListener) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(
                riverView,
                "laserPercent",
                from,
                to);
        objectAnimator.setDuration(duration);
        objectAnimator.setInterpolator(new AccelerateInterpolator());
        objectAnimator.addListener(animatorListener);
        objectAnimator.addUpdateListener(updateListener);
        return objectAnimator;
    }

    public ObjectAnimator generateRiverAnimation(CircleRiverView riverView,
                                                 long duraion,
                                                 float from,
                                                 float to,
                                                 ValueAnimator.AnimatorUpdateListener updateListener,
                                                 Animator.AnimatorListener animatorListener) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(
                riverView,
                "RiverRadiusPercent",
                from,
                to
        );
        objectAnimator.setDuration(duraion);
        objectAnimator.addUpdateListener(updateListener);
        objectAnimator.addListener(animatorListener);
        return objectAnimator;
    }

    ;

    public ObjectAnimator generateAlphaAnimation(View riverView,
                                                 long duration,
                                                 float from,
                                                 float mid,
                                                 float to) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(riverView, "alpha", from, mid, to);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.setDuration(duration);
        return objectAnimator;
    }

    public ObjectAnimator generateScaleAnimation(View targetView,
                                                 long duration,
                                                 float from,
                                                 float mid,
                                                 float to,
                                                 boolean isX) {
        ObjectAnimator objectAnimator
                = ObjectAnimator.ofFloat(targetView, isX ? "scaleX" : "scaleY", from, mid, to);
        objectAnimator.setDuration(duration);
        return objectAnimator;
    }

    public ObjectAnimator generateTranslateAnimation(View targetView,
                                                     long duration,
                                                     int from,
                                                     int to) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(targetView, "translationY", from, to);
        objectAnimator.setDuration(duration);
        return objectAnimator;
    }

}
