package com.studys.lyle.radarview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by lyle on 2017/4/9.
 */

public class RadarView extends View {

    private int startColor = getResources().getColor(R.color.startColor);
    private int endColor = getResources().getColor(R.color.endColor);
    private int bgColor = getResources().getColor(R.color.bgColor);
    private int lineColor = getResources().getColor(R.color.lineColor);
    private int circleCount = 4;
    private int mStartColor;
    private int mEndcolor;
    private int mBgColorr;
    private int mLineColor;
    private int mCircleCount;
    private Paint mRadarPaint;
    private Paint mBgPaint;
    private SweepGradient mRadarShader;
    private Matrix mMatrix;
    private float mRadarRadius;
    private int mRotateAngel = 0;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mRotateAngel += 5;
            postInvalidate();
            mMatrix.reset();
            mMatrix.preRotate(mRotateAngel, 0, 0);
            mHandler.sendEmptyMessageDelayed(0, 10);
        }
    };

    public RadarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs);
        initPaint();
    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RadarView);
        mStartColor = ta.getColor(R.styleable.RadarView_startColor, startColor);
        mEndcolor = ta.getColor(R.styleable.RadarView_endColor, endColor);
        mBgColorr = ta.getColor(R.styleable.RadarView_bgColor, bgColor);
        mLineColor = ta.getColor(R.styleable.RadarView_lineColor, lineColor);
        mCircleCount = ta.getInteger(R.styleable.RadarView_circleCount, circleCount);
        ta.recycle();
    }

    private void initPaint() {
        mRadarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRadarPaint.setColor(mLineColor);
        mRadarPaint.setStyle(Paint.Style.STROKE);
        mRadarPaint.setStrokeWidth(2);

        mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint.setColor(mBgColorr);
        mBgPaint.setStyle(Paint.Style.FILL);

        mRadarShader = new SweepGradient(0, 0, mStartColor, mEndcolor);
        mMatrix = new Matrix();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = measureSize(1, 200, widthMeasureSpec);
        int height = measureSize(0, 200, heightMeasureSpec);
        int measureSize = Math.max(width, height);
        mRadarRadius = measureSize / 2.0f;
        setMeasuredDimension(measureSize, measureSize);

    }

    /**
     * @return 设置的宽/高像素值,默认为contentSize
     */
    private int measureSize(int specType, int contentSize, int measureSpec) {
        int result;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            result = Math.max(contentSize, size);
        } else {
            result = contentSize;
            if (specType == 1) {
                result += getPaddingLeft() + getPaddingRight();
            } else {
                result += getPaddingTop() + getPaddingBottom();
            }
        }
        System.out.println("result:" + result);

        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        canvas.translate(mRadarRadius, mRadarRadius);
        mBgPaint.setShader(null);
        canvas.drawCircle(0, 0, mRadarRadius, mBgPaint);

        for (int i = 1; i <= mCircleCount; i++) {
            canvas.drawCircle(0, 0, (float) (i * 1.0 / mCircleCount * mRadarRadius), mRadarPaint);
        }
        canvas.drawLine(-mRadarRadius, 0, mRadarRadius, 0, mRadarPaint);
        canvas.drawLine(0, -mRadarRadius, 0, mRadarRadius, mRadarPaint);

        mBgPaint.setShader(mRadarShader);
        canvas.concat(mMatrix);
        canvas.drawCircle(0, 0, mRadarRadius, mBgPaint);

    }

    public void startSweep() {
        mHandler.sendEmptyMessage(0);
    }

    public void stopSweep() {
        mRotateAngel=0;
//        mMatrix.preRotate(mRotateAngel, 0, 0);
//        mHandler.removeCallbacksAndMessages(null);
    }
}
