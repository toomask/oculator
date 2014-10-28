package com.x_mega.oculator.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.x_mega.oculator.motion_picture.BasicMotionPicture;
import com.x_mega.oculator.motion_picture.MotionPicture;
import com.x_mega.oculator.motion_picture.drawing_controller.DrawingController;

/**
 * Created by toomas on 8.10.2014.
 */
public class MotionPictureView extends View {

    public MotionPictureView(Context context) {
        super(context);
    }

    public MotionPictureView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MotionPictureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    MotionPicture motionPicture;
    int currentFrameIndex = 0;
    long currentFrameStartTime = System.currentTimeMillis();

    public void setMotionPicture(MotionPicture motionPicture) {
        if (this.motionPicture == null) {
            currentFrameStartTime = System.currentTimeMillis();
        }
        this.motionPicture = motionPicture;
        if (motionPicture.getFrameCount() <= currentFrameIndex) {
            currentFrameIndex = 0;
        }
        invalidate();
    }

    public interface FrameChangedListener {
        public void onFrameChanged(int newFrameIndex, MotionPicture motionPicture);
    }

    FrameChangedListener frameChangedListener;

    public void setFrameChangedListener(FrameChangedListener frameChangedListener) {
        this.frameChangedListener = frameChangedListener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (motionPicture != null) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - currentFrameStartTime > motionPicture.getFrameDuration(currentFrameIndex)) {
                currentFrameIndex++;
                if (currentFrameIndex >= motionPicture.getFrameCount()) {
                    currentFrameIndex = 0;
                }
                if (frameChangedListener != null) {
                    frameChangedListener.onFrameChanged(currentFrameIndex, motionPicture);
                }
                currentFrameStartTime = currentTime;
            }

            drawWithDrawingController();

            Bitmap frame = motionPicture.getFrame(currentFrameIndex);
            Paint paint = new Paint();
            paint.setAntiAlias(false);
            canvas.drawBitmap(frame,
                    new Rect(0, 0, frame.getWidth(), frame.getHeight()),
                    new Rect(0, 0, getWidth(), getHeight()),
                    paint);
            invalidate();
        }
    }

    DrawingController drawingController;

    public void setDrawingController(DrawingController drawingController) {
        this.drawingController = drawingController;
        this.drawingController.onSizeChanged(getWidth(), getHeight());
    }

    private void drawWithDrawingController() {
        if (drawingController != null) {
            Bitmap oldFrame = motionPicture.getFrame(currentFrameIndex);
            Bitmap newFrame = drawingController.drawTo(oldFrame);
            if (motionPicture instanceof BasicMotionPicture) {
                ((BasicMotionPicture) motionPicture).setFrame(currentFrameIndex, newFrame);
            }
        }
    }

    public interface DrawStartListener {
        public void onNewDrawEvent();
    }

    DrawStartListener drawStartListener;

    public void setDrawStartListener(DrawStartListener drawStartListener) {
        this.drawStartListener = drawStartListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (drawingController != null) {
            if (drawStartListener != null &&
                    (event.getAction() == MotionEvent.ACTION_DOWN)) {
                drawStartListener.onNewDrawEvent();
            }
            drawingController.onTouchEvent(event);
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (drawingController != null) {
            drawingController.onSizeChanged(w, h);
        }
    }
}
