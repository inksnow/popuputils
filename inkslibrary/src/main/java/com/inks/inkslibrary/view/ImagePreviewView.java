package com.inks.inkslibrary.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.inks.inkslibrary.Utils.L;

/**
 * <pre>
 *     author : inks
 *     time   : 2022/07/29
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class ImagePreviewView extends FrameLayout {

    //手势检测
    private GestureDetector mGDetector;

    //缩放类手势检测
    private ScaleGestureDetector mSGDetector;

    //原始图片
    private Bitmap bitmap;
    //画布可显示区域
    private Rect displayRect = new Rect();

    //view的宽高
    private int width;
    private int height;


    private Matrix matrix = new Matrix();
    //现在的放大倍数,移动距离
    private float scanMultiple = 1.0f;
    private float moveX = 0;
    private float moveY = 0;

    private long scanTime;

    public ImagePreviewView(@NonNull Context context) {
        this(context, null);
    }

    public ImagePreviewView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImagePreviewView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ImagePreviewView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        //允许绘制
        setWillNotDraw(false);
        initialize(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.width = MeasureSpec.getSize(widthMeasureSpec);//宽
        this.height = MeasureSpec.getSize(heightMeasureSpec);//宽
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    private void initialize(Context context) {
        mGDetector = new GestureDetector(context, gestureDetectorListener);
        mSGDetector = new ScaleGestureDetector(context, scaleGestureDetectorListener);
    }

    public void setBitmap(Bitmap bitmap) {

        this.bitmap = bitmap;
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        L.ee("onDraw");
        super.onDraw(canvas);
        drawBitmap(canvas);
    }

    private void drawBitmap(Canvas canvas) {
        if (bitmap != null) {
            canvas.concat(matrix);
            canvas.save();
            Rect rect = new Rect();
            if ((bitmap.getWidth() / width) >= (bitmap.getHeight() / height)) {
                //宽大于高，以宽为主
                float scanSize = (float) width / bitmap.getWidth();

                rect.left = 0;
                rect.right = width;
                rect.top = (int) ((height - (bitmap.getHeight() * scanSize)) / 2);
                rect.bottom = (int) ((int) ((height - (bitmap.getHeight() * scanSize)) / 2) + (bitmap.getHeight() * scanSize));
            } else {
                float scanSize = (float) height / bitmap.getHeight();
                rect.left = (int) ((width - (bitmap.getWidth() * scanSize)) / 2);
                rect.right = (int) ((int) ((width - (bitmap.getWidth() * scanSize)) / 2) + (bitmap.getWidth() * scanSize));
                rect.top = 0;
                rect.bottom = height;
            }
            L.ee(rect);
            canvas.drawBitmap(bitmap, null, rect, null);
            canvas.restore();


        }

    }


    GestureDetector.OnGestureListener gestureDetectorListener = new GestureDetector.OnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if(scanTime>0 && (System.currentTimeMillis()-scanTime)>300 || scanTime==0){
                moveX = moveX + distanceX;
                moveY = moveY + distanceY;
                matrix.postTranslate(-distanceX, -distanceY);
                invalidate();
            }


            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    };


    ScaleGestureDetector.OnScaleGestureListener scaleGestureDetectorListener = new ScaleGestureDetector.OnScaleGestureListener() {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scanTime = System.currentTimeMillis();
            L.ee("onScale:", detector.getScaleFactor());
            float factor = detector.getScaleFactor();
            if (scanMultiple > 3 && factor > 1) {
                return true;
            }
            if (scanMultiple < 0.3 && factor < 1) {
                return true;
            }
            scanMultiple = scanMultiple * factor;
            matrix.postScale(factor, factor, width / 2.0f, height / 2.0f);
            invalidate();
            return true;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            L.ee("onScaleBegin");
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            L.ee("onScaleBegin");
        }
    };


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //  L.ee("onTouchEvent");
        if (event.getPointerCount() > 1) {
            return mSGDetector.onTouchEvent(event);
        } else {
            return mGDetector.onTouchEvent(event);
        }


    }
}
