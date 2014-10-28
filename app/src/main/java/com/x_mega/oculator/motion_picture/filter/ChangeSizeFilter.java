package com.x_mega.oculator.motion_picture.filter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.x_mega.oculator.motion_picture.BasicMotionPicture;
import com.x_mega.oculator.motion_picture.MotionPicture;

/**
 * Created by toomas on 11.10.2014.
 */
public class ChangeSizeFilter implements Filter {

    private static final int MODE_MULTIPLY = 0;
    private static final int MODE_FIXED = 1;

    float widthMultiplier;
    float heigthMultiplier;

    int width;
    int height;

    int mode;

    public ChangeSizeFilter(float widthMultiplier, float heigthMultiplier) {
        mode = MODE_MULTIPLY;
        this.widthMultiplier = widthMultiplier;
        this.heigthMultiplier = heigthMultiplier;
    }

    public ChangeSizeFilter(int width, int height) {
        mode = MODE_FIXED;
        this.width = width;
        this.height = height;
    }

    @Override
    public MotionPicture applyTo(MotionPicture motionPicture) {
        BasicMotionPicture newPicture = new BasicMotionPicture();
        for (int i = 0; i < motionPicture.getFrameCount(); i++) {
            Bitmap oldFrame = motionPicture.getFrame(i);
            int newWidth;
            int newHeigth;
            if (mode == MODE_MULTIPLY) {
                newWidth = Math.round(oldFrame.getWidth() * widthMultiplier);
                newHeigth = Math.round(oldFrame.getHeight() * heigthMultiplier);
            } else { // MODE_FIXED
                newWidth = width;
                newHeigth = height;
            }
            Bitmap newFrame = Bitmap.createBitmap(newWidth, newHeigth, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(newFrame);
            Paint paint = new Paint();
            paint.setAntiAlias(false);
            canvas.drawBitmap(oldFrame,
                    new Rect(0, 0, oldFrame.getWidth(), oldFrame.getHeight()),
                    new Rect(0, 0, newWidth, newHeigth),
                    paint);
            newPicture.addFrame(newFrame);
            newPicture.setFrameDuration(i, motionPicture.getFrameDuration(i));
        }
        return newPicture;
    }
}
