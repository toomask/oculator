package com.x_mega.oculator.motion_picture.filter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.x_mega.oculator.motion_picture.BasicMotionPicture;
import com.x_mega.oculator.motion_picture.MotionPicture;

/**
 * Created by toomas on 8.10.2014.
 */
public class BlurFilter implements Filter {

    @Override
    public MotionPicture applyTo(MotionPicture motionPicture) {
        BasicMotionPicture newPicture = new BasicMotionPicture();

        Bitmap lastFrame = motionPicture.getFrame(0);

        for (int i = 0; i < motionPicture.getFrameCount(); i++) {
            Bitmap newFrame = lastFrame.copy(Bitmap.Config.ARGB_8888, true);
            Canvas canvas = new Canvas(newFrame);
            Paint paint = new Paint();
            paint.setAlpha(200);
            canvas.drawBitmap(motionPicture.getFrame(i), 0, 0, paint);
            newPicture.addFrame(newFrame);
            newPicture.setFrameDuration(i, motionPicture.getFrameDuration(i));
            lastFrame = newFrame;
        }

        for (int i = 0; i < newPicture.getFrameCount(); i++) {
            Bitmap newFrame = lastFrame.copy(Bitmap.Config.ARGB_8888, true);
            Canvas canvas = new Canvas(newFrame);
            Paint paint = new Paint();
            paint.setAlpha(200);
            canvas.drawBitmap(newPicture.getFrame(i), 0, 0, paint);
            newPicture.setFrame(i, newFrame);
            lastFrame = newFrame;
        }

        for (int i = 0; i < newPicture.getFrameCount(); i++) {
            Bitmap newFrame = lastFrame.copy(Bitmap.Config.ARGB_8888, true);
            Canvas canvas = new Canvas(newFrame);
            Paint paint = new Paint();
            paint.setAlpha(200);
            canvas.drawBitmap(newPicture.getFrame(i), 0, 0, paint);
            newPicture.setFrame(i, newFrame);
            lastFrame = newFrame;
        }


        return newPicture;
    }
}
