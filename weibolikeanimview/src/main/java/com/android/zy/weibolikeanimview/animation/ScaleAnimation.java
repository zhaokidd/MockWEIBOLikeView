package com.android.zy.weibolikeanimview.animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.view.View;

/**
 * Created by zy on 2017/10/11.
 */

public class ScaleAnimation {
    static class ReverseTimeInterpolater implements TimeInterpolator {
        @Override
        public float getInterpolation(float input) {
            if (input <= 0.5) {
                return input;
            } else {
                return (1 - input);
            }
        }
    }

    static class FloatEvaluator implements TypeEvaluator<Float> {
        @Override
        public Float evaluate(float fraction, Float startValue, Float endValue) {
            return null;
        }
    }

    public void startScaleAnimation(View view) {
        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator objectAnimatorX = ObjectAnimator
                .ofFloat(view, "scaleX", 1.0f, 1.5f);
        objectAnimatorX.setDuration(300);
        objectAnimatorX.setEvaluator(new android.animation.FloatEvaluator());
        objectAnimatorX.setInterpolator(new ReverseTimeInterpolater());

        ObjectAnimator objectAnimatorY = ObjectAnimator
                .ofFloat(view, "scaleY", 1.0f, 1.5f);
        objectAnimatorY.setDuration(300);
        objectAnimatorY.setEvaluator(new android.animation.FloatEvaluator());
        objectAnimatorY.setInterpolator(new ReverseTimeInterpolater());

        animatorSet
                .play(objectAnimatorY)
                .with(objectAnimatorX);
        animatorSet.start();
    }
}
