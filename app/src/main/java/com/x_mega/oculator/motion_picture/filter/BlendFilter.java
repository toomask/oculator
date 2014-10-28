package com.x_mega.oculator.motion_picture.filter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.x_mega.oculator.motion_picture.BasicMotionPicture;
import com.x_mega.oculator.motion_picture.MotionPicture;

/**
 * Created by toomas on 8.10.2014.
 */
public class BlendFilter implements Filter {

    MotionPicture overlay;
    int alpha;

    public BlendFilter(MotionPicture overlay, int alpha) {
        this.overlay = overlay;
        this.alpha = alpha;
    }

    @Override
    public MotionPicture applyTo(MotionPicture motionPicture) {
        BasicMotionPicture newPicture = BasicMotionPicture.copy(motionPicture);
        for (int i = 0; i < newPicture.getFrameCount(); i++) {
            Bitmap frame = newPicture.getFrame(i);
            Canvas canvas = new Canvas(frame);
            Bitmap overlayFrame = overlay.getFrame(i % overlay.getFrameCount());
            Paint paint = new Paint();
            paint.setAlpha(alpha);
            canvas.drawBitmap(
                    overlayFrame,
                    new Rect(0, 0, overlayFrame.getWidth(), overlayFrame.getHeight()),
                    new Rect(0, 0, frame.getWidth(), frame.getHeight()),
                    paint
            );
        }
        return newPicture;
    }
}
