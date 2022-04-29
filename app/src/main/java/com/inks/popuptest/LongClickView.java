package com.inks.popuptest;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;
// 原文链接：https://blog.csdn.net/qq_40785165/article/details/115265201
public class LongClickView extends View {
    public int DEFAULT_MAX_SECONDS = 15;
    public int DEFAULT_ANNULUS_WIDTH = 5;
    public int DEFAULT_ANNULUS_COLOR;
    public int DEFAULT_RATE = 50;
    private Paint mSmallCirclePaint;
    private Paint mMiddenCirclePaint;
    private Paint mBigCirclePaint;
    private Paint mAngleCirclePaint;
    private int mWidthSize;
    private Timer mTimer;//计时器
    private AtomicInteger mCount = new AtomicInteger(0);
    private MyClickListener mMyClickListener;
    private boolean mIsFinish = true;
    private long mStartTime;//点击的时间
    private long mEndTime;//点击结束的时间
    private int mMaxSeconds;
    private int mDelayMilliseconds;
    private int mAnnulusColor;
    private float mAnnulusWidth;
 
    public interface MyClickListener {
        void longClickFinish();//长按结束
 
        void singleClickFinish();//单击结束
    }
 
    public void setMyClickListener(MyClickListener myClickListener) {
        mMyClickListener = myClickListener;
    }
 
    public LongClickView(Context context) {
        this(context, null);
    }
 
    public LongClickView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }
 
    public LongClickView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttrs(context, attrs);
        initView();
    }
 
    private void getAttrs(Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LongClickView);
        //maxSeconds 最大的秒数
        mMaxSeconds = typedArray.getInt(R.styleable.LongClickView_maxSeconds, DEFAULT_MAX_SECONDS);
        //annulusWidth 圆环的宽度
        mAnnulusWidth = typedArray.getInt(R.styleable.LongClickView_annulusWidth, DEFAULT_ANNULUS_WIDTH);
        //annulusColor 圆环的颜色
        DEFAULT_ANNULUS_COLOR = 0XFF777777;
        mAnnulusColor = typedArray.getColor(R.styleable.LongClickView_annulusColor, DEFAULT_ANNULUS_COLOR);
        //delayMilliseconds 进度条隔多少时间走一次,值越小走的越快，显得更流畅
        mDelayMilliseconds = typedArray.getInt(R.styleable.LongClickView_delayMilliseconds, DEFAULT_RATE);
    }
 
    private static final String TAG = "LongClickView";
 
    private void initView() {
        mBigCirclePaint = new Paint();
        mSmallCirclePaint = new Paint();
        mMiddenCirclePaint = new Paint();
        mAngleCirclePaint = new Paint();
        mBigCirclePaint.setStyle(Paint.Style.FILL);
        mBigCirclePaint.setColor(Color.LTGRAY);
        mBigCirclePaint.setAntiAlias(true);
        mBigCirclePaint.setStrokeWidth(5);
        mSmallCirclePaint.setStrokeWidth(5);
        mSmallCirclePaint.setAntiAlias(true);
        mSmallCirclePaint.setColor(Color.WHITE);
        mSmallCirclePaint.setStyle(Paint.Style.FILL);
 
        mMiddenCirclePaint.setStrokeWidth(5);
        mMiddenCirclePaint.setAntiAlias(true);
        mMiddenCirclePaint.setColor(Color.LTGRAY);
        mMiddenCirclePaint.setStyle(Paint.Style.FILL);
        mAngleCirclePaint.setStrokeWidth(5);
        mAngleCirclePaint.setAntiAlias(true);
        mAngleCirclePaint.setColor(mAnnulusColor);
        mAngleCirclePaint.setStyle(Paint.Style.FILL);
        this.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mIsFinish = false;
                mCount.set(0);
                mTimer = new Timer();
                mTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        mCount.addAndGet(1);
                        invalidate();
                        if (mCount.get() * mDelayMilliseconds >= mMaxSeconds * 1000) {
                            mCount.set(0);
                            this.cancel();
                            invalidate();
                            mIsFinish = true;
                            if (mMyClickListener != null) {
                                mMyClickListener.longClickFinish();
                            }
                        }
                    }
                }, 0, mDelayMilliseconds);
                return true;
            }
        });
 
    }
 
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(mWidthSize, mWidthSize);
    }
 
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(mWidthSize / 2, mWidthSize / 2, mWidthSize / 2, mBigCirclePaint);//最外层的填充圆
        RectF rectF = new RectF(0, 0, mWidthSize, mWidthSize);//进度扇形
        if (mCount.get() > 0) {
            //求出每一次定时器执行所绘制的扇形度数
            float perAngle = 360f / mMaxSeconds / (1000f / mDelayMilliseconds);
            canvas.drawArc(rectF, 0, perAngle * mCount.get(), true, mAngleCirclePaint);
        }
        canvas.drawCircle(mWidthSize / 2, mWidthSize / 2, mWidthSize / 2 - mAnnulusWidth, mMiddenCirclePaint);//中间一层灰色的圆
        //最后绘制中心圆
        if (mIsFinish) {
            canvas.drawCircle(mWidthSize / 2, mWidthSize / 2, mWidthSize / 2 - mAnnulusWidth, mSmallCirclePaint);
        } else {
            canvas.drawCircle(mWidthSize / 2, mWidthSize / 2, mWidthSize / 8, mSmallCirclePaint);
        }
        super.onDraw(canvas);
    }
 
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            mEndTime = System.currentTimeMillis();
            new MyAsyncTask().execute();
        } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mStartTime = System.currentTimeMillis();
        }
        return super.onTouchEvent(event);
    }
 
    public class MyAsyncTask extends AsyncTask<Void, Void, Void> {
 
        @Override
        protected Void doInBackground(Void... voids) {
            if (mTimer != null) {
                mTimer.cancel();
            }
            return null;
        }
 
        @Override
        protected void onPostExecute(Void aVoid) {
            //使用时间戳的差来判断是单击或者长按
            if (mEndTime - mStartTime > 1000) {
                //防止在结束后松开手指有重新调用了一次长按结束的回调
                if (!mIsFinish) {
                    if (mMyClickListener != null) {
                        mMyClickListener.longClickFinish();
                    }
                }
            } else {
                mCount.set(0);
                invalidate();
                if (mMyClickListener != null) {
                    mMyClickListener.singleClickFinish();
                }
            }
            mIsFinish = true;
        }
    }
}