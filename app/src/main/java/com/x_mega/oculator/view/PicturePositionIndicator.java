package com.x_mega.oculator.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.x_mega.oculator.motion_picture.MotionPicture;

/**
 * Created by toomas on 11.10.2014.
 */
public class PicturePositionIndicator extends View implements MotionPictureView.FrameChangedListener{

    public PicturePositionIndicator(Context context) {
        super(context);
        init();
    }

    public PicturePositionIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PicturePositionIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        drawFrame = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
    }

    float picturePosition = 0f;
    float indicatorWidth = 0.1f;

    @Override
    public void onFrameChanged(int newFrameIndex, MotionPicture motionPicture) {
        picturePosition = (float) (newFrameIndex ) / (float) ( motionPicture.getFrameCount() - 1) ;
        indicatorWidth = 1f / (float) motionPicture.getFrameCount();
        invalidate();
    }

    Bitmap drawFrame;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        drawFrame = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
    }

    @Override
    protected void onDraw(Canvas viewCanvas) {
        super.onDraw(viewCanvas);
        Canvas canvas = new Canvas(drawFrame);
        canvas.drawColor(Color.parseColor("#10000000"));
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#292929"));
        canvas.drawRect(
                picturePosition * getWidth() -  getWidth() * indicatorWidth,
                0,
                picturePosition * getWidth(),
                getHeight(),
                paint
        );
        viewCanvas.drawBitmap(drawFrame, 0, 0, null);
        invalidate();
    }
}
