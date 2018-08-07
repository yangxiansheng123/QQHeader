package com.imitate.qqheader;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * @author yang
 */
public class ImitateQQHeaderSrollView extends ListView {

    private static final String TAG = "---------------->";
    private ImageView mImageView;
    /**
     * 初始高度
     */
    private int mImageViewHeight;

    /**
     * 获取头部imagview控件
     * @param iv
     */
    public void setZoomImageView(ImageView iv) {
        mImageView = iv;
    }
    public ImitateQQHeaderSrollView(Context context) {
        super(context);
    }

    public ImitateQQHeaderSrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mImageViewHeight=context.getResources().getDimensionPixelSize(R.dimen.size_default_height);
    }

    public ImitateQQHeaderSrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if(action==MotionEvent.ACTION_UP){

            ResetAnimation resetAnimation = new ResetAnimation(mImageViewHeight);
            resetAnimation.setInterpolator(new OvershootInterpolator());
            resetAnimation.setDuration(600);
            mImageView.startAnimation(resetAnimation);
        }
        return super.onTouchEvent(ev);
    }

    public class ResetAnimation extends Animation {

        private int extraHeight;
        private int currentHeight;

        public ResetAnimation(int targetHeight) {
            currentHeight = mImageView.getHeight();
            extraHeight = mImageView.getHeight() - targetHeight;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {

            mImageView.getLayoutParams().height = (int) (currentHeight - extraHeight * interpolatedTime);
            mImageView.requestLayout();
            super.applyTransformation(interpolatedTime, t);
        }
    }
    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        Log.i(TAG, "overScrollBy: "+deltaY);
        if (deltaY < 0) {
    //            int heig = mImageView.getHeight();
            mImageView.getLayoutParams().height = mImageView.getHeight() - deltaY;
            mImageView.requestLayout();
        }else {
        //  上拉
            mImageView.getLayoutParams().height = mImageView.getHeight() - deltaY;
            mImageView.requestLayout();
        }
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        View header = (View) mImageView.getParent();
        Log.i(TAG, "onSizeChanged: "+header.getTop());
        //ListView会滑出去的高度（负数）
        int deltaY = header.getTop();
//        只有image的高度大于 原始的高度   那我们就缩小
        if (mImageView.getHeight() > mImageViewHeight) {
            mImageView.getLayoutParams().height = mImageView.getHeight() + deltaY;
            header.layout(header.getLeft(), 0, header.getRight(), header.getHeight());
            mImageView.requestLayout();
        }
        super.onSizeChanged(w, h, oldw, oldh);
    }

}
