package com.x_mega.oculator.motion_picture.filter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.x_mega.oculator.motion_picture.BasicMotionPicture;
import com.x_mega.oculator.motion_picture.MotionPicture;

/**
 * Created by toomas on 21.10.2014.
 */
public class Crop implements Filter{

    int width;
    int height;
    int left;
    int top;

    public Crop(int width, int height, int left, int top) {
        this.width = width;
        this.height = height;
        this.left = left;
        this.top = top;
    }

    @Override
    public MotionPicture applyTo(MotionPicture motionPicture) {
        BasicMotionPicture newPic = new BasicMotionPicture();
        for (int i = 0; i < motionPicture.getFrameCount(); i++) {
            Bitmap newFrame = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(newFrame);
            canvas.drawBitmap(motionPicture.getFrame(i),
                    new Rect(left, top, left + width, top + height),
                    new Rect(0, 0, width, height),
                    null);
            newPic.addFrame(newFrame);
            newPic.setFrameDuration(i, motionPicture.getFrameDuration(i));
        }
        return newPic;
    }

}
