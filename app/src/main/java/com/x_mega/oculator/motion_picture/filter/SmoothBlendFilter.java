package com.x_mega.oculator.motion_picture.filter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.x_mega.oculator.motion_picture.BasicMotionPicture;
import com.x_mega.oculator.motion_picture.MotionPicture;

/**
 * Created by toomas on 20.10.2014.
 */
public class SmoothBlendFilter implements Filter{

    int alpha = 255;
    MotionPicture overlay;

    public SmoothBlendFilter(MotionPicture overlay) {
        this.overlay = overlay;
    }

    @Override
    public MotionPicture applyTo(MotionPicture motionPicture) {
        BasicMotionPicture copy = BasicMotionPicture.copy(motionPicture);
        Bitmap drawBitmap = Bitmap.createBitmap(motionPicture.getFrame(0).getWidth(),
                motionPicture.getFrame(0).getHeight(), Bitmap.Config.ARGB_8888);
        for (int i = 0; i < motionPicture.getFrameCount(); i++) {
            Canvas canvas = new Canvas(drawBitmap);
            canvas.drawBitmap(overlay.getFrame(i), 0, 0, null);
            Bitmap frame = copy.getFrame(i);
            Canvas canvas1 = new Canvas(frame);
            Paint paint = new Paint();
            paint.setAlpha(alpha);
            canvas1.drawBitmap(drawBitmap, 0, 0, paint);
            copy.setFrame(i, frame);
            drawBitmap = reduceDrawFrameAlpha(drawBitmap);
        }

        return copy;
    }

    private Bitmap reduceDrawFrameAlpha(Bitmap bitmap) {
        Bitmap newFrame = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        newFrame.setHasAlpha(true);
        Canvas canvas = new Canvas(newFrame);
        Paint paint = new Paint();
        paint.setAlpha(120);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return newFrame;
    }
}
