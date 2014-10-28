package com.x_mega.oculator.motion_picture.filter;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

import com.x_mega.oculator.motion_picture.BasicMotionPicture;
import com.x_mega.oculator.motion_picture.MotionPicture;

/**
 * Created by toomas on 8.10.2014.
 */
public class ColorFilter implements Filter {

    int color;

    public ColorFilter(String color) {
        this.color = Color.parseColor(color);
    }

    @Override
    public MotionPicture applyTo(MotionPicture motionPicture) {
        BasicMotionPicture newPicture = new BasicMotionPicture();
        for (int i = 0; i < motionPicture.getFrameCount(); i++) {
            Bitmap newFrame = motionPicture.getFrame(i).copy(Bitmap.Config.ARGB_8888, true);
            Canvas canvas = new Canvas(newFrame);
            canvas.drawColor(color);
            newPicture.addFrame(newFrame);
            newPicture.setFrameDuration(i, motionPicture.getFrameDuration(i));
        }
        return newPicture;
    }

}
