package com.android.zy.sinaweibolikeanimation;

import android.animation.Animator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.android.zy.weibolikeanimview.view.CircleRiverView;
import com.android.zy.weibolikeanimview.view.WeiboLikeAnimView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private WeiboLikeAnimView mLikeAnimView;
    private CircleRiverView mCircleRiverView;
    private Button mTestButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLikeAnimView = findViewById(R.id.weiboLikeAnimView);
        mCircleRiverView = findViewById(R.id.circleRiverView);
        mLikeAnimView.setmIsLiked(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                if (mLikeAnimView != null) {
                    mLikeAnimView.startAnim();
                }
                break;
            case R.id.button2:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mCircleRiverView.startRiverAnimation(new Animator.AnimatorListener() {
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
                    }
                });
                break;
        }
    }
}
