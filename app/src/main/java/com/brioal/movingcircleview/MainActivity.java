package com.brioal.movingcircleview;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.brioal.movingview.listener.OnAnimatorChangeListener;
import com.brioal.movingview.view.MovingDotView;


public class MainActivity extends AppCompatActivity {

    MovingDotView mMovingDotView;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);
        mMovingDotView = (MovingDotView) findViewById(R.id.main_movingView);
        mMovingDotView.setProgress(50);
        mMovingDotView.setToProgress(10);
        mMovingDotView.setChangeListener(new OnAnimatorChangeListener() {
            @Override
            public void onProgressChanged(float progress) {
                if (progress == 1) {
                    Toast.makeText(mContext,"清理成功",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (mMovingDotView.isCleaned()) {
            mMovingDotView.backClean();
        } else {
            super.onBackPressed();
        }
    }
}
