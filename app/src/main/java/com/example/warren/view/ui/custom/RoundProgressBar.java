package com.example.warren.view.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.example.warren.view.R;


/**
 * 圆形进度条更新
 * Created by warren on 12/10/15.
 */
public class RoundProgressBar extends View {

    private final static int DEFAULT_TEXT_SIZE = 20;
    private final static int DEFAULT_ROUND_WIDTH = 5;
    private final static int DEFAULT_MAX =100;
    private Paint mPaint;
    //圆环的颜色
    private int mRoundColor;
    //圆环进度的颜色
    private int mRoundProgressColor;
    //字体大小
    private float mTextSize;
    //字体的颜色
    private int mTextColor;
    //环宽
    private float mRoundWidth;
    //最大进度
    private int max;
    //当前进度
    private int current;
    //是否显示进度的字
    private boolean mIsTextShow;
    //进度的样式
    private int mStyle;
    /**
     * 样式：
     * 0:空心
     * 1:实心
     */
    public static final int STROKE = 0;
    public static final int FILL = 1;

    public RoundProgressBar(Context context) {
        this(context, null);
    }

    public RoundProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressBar);
        //默认圆环的颜色为透明
        mRoundColor = mTypedArray.getColor(R.styleable.RoundProgressBar_rpi_round_color, Color.TRANSPARENT);
        mRoundProgressColor = mTypedArray.getColor(R.styleable.RoundProgressBar_rpi_round_progress_color, Color.RED);
        mTextColor = mTypedArray.getColor(R.styleable.RoundProgressBar_rpi_text_color, Color.WHITE);
        mTextSize = mTypedArray.getDimension(R.styleable.RoundProgressBar_rpi_text_size, DEFAULT_TEXT_SIZE);
        mRoundWidth = mTypedArray.getDimension(R.styleable.RoundProgressBar_rpi_round_width, DEFAULT_ROUND_WIDTH);
        max = mTypedArray.getInt(R.styleable.RoundProgressBar_rpi_max, DEFAULT_MAX);
        mIsTextShow =  mTypedArray.getBoolean(R.styleable.RoundProgressBar_rpi_textIsDisplayable, true);
        mStyle = mTypedArray.getInt(R.styleable.RoundProgressBar_style, 0);
        mTypedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         * 最外层大圆环
         */
        int center = getWidth()/2;
        int radius = (int)(center-mRoundWidth/2);//半径
        mPaint.setColor(mRoundColor);
        mPaint.setStyle(Paint.Style.STROKE);

        mPaint.setStrokeWidth(mRoundWidth);
        mPaint.setAntiAlias(true);  // 消除锯齿
        canvas.drawCircle(center, center, radius, mPaint);

        /**
         * 画进度百分比
         */
        mPaint.setStrokeWidth(0);
        mPaint.setColor(mTextColor);
        mPaint.setTextSize(mTextSize);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        //这里一定要转化为float先哦，不然都为0
        int percent = (int)(((float)current / (float)max) * 100);
        float textWidth = mPaint.measureText(percent+"%");
        if(mIsTextShow&&percent!=0&&mStyle==STROKE){
            canvas.drawText(percent+"%",center-textWidth/2,center+mTextSize/2,mPaint);
        }

        /**
         * 画出圆环进度
         */
        mPaint.setStrokeWidth(mRoundWidth);
        mPaint.setColor(mRoundProgressColor);
        RectF oval = new RectF(center-radius,center-radius,center+radius,center+radius);
        switch (mStyle){
            case STROKE:
                mPaint.setStyle(Paint.Style.STROKE);
                canvas.drawArc(oval,0,360*current/max,false,mPaint);
                break;
            case FILL:
                mPaint.setStyle(Paint.Style.FILL);
                if(current!=0)
                    canvas.drawArc(oval, 0, 360 * current / max, true, mPaint);  //根据进度画圆弧
                break;
        }

    }
    public synchronized int getMax(){
        return max;
    }
    public synchronized void setMax(int max){
        if(max < 0){
            throw new IllegalArgumentException("max not less than 0");
        }
        this.max = max;
    }
    /**
     * 获取进度.需要同步
     * @return
     */
    public synchronized int getCurrent() {
        return current;
    }

    /**
     * 设置进度，此为线程安全控件，由于考虑多线的问题，需要同步
     * 刷新界面调用postInvalidate()能在非UI线程刷新
     * @param current
     */
    public synchronized void setCurrent(int current) {
        if(current < 0){
            throw new IllegalArgumentException("progress not less than 0");
        }
        if(current > max){
            current = max;
        }
        if(current <= max){
            this.current = current;
            postInvalidate();
        }

    }
}
