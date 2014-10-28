package com.x_mega.oculator.motion_picture.filter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.x_mega.oculator.motion_picture.BasicMotionPicture;
import com.x_mega.oculator.motion_picture.MotionPicture;

/**
 * Created by toomas on 22.10.2014.
 */
public class TightDistort implements Filter {

    @Override
    public MotionPicture applyTo(MotionPicture motionPicture) {
        BasicMotionPicture newPicture = new BasicMotionPicture();
        for (int i = 0; i < motionPicture.getFrameCount(); i++) {
            Bitmap oldFrame = motionPicture.getFrame(i);
            newPicture.addFrame(generateFrame(i, motionPicture, oldFrame.getWidth(), oldFrame.getHeight()));
            newPicture.setFrameDuration(i, motionPicture.getFrameDuration(i));
        }
        return newPicture;
    }

    private Bitmap generateFrame(int i, MotionPicture motionPicture, int width, int height) {
        Bitmap newFrame = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newFrame);
        for (int line = 0; line < height; line++) {
            int i1 = (i + line) % motionPicture.getFrameCount() * 2;
            i1 = Math.abs(i1 - ( motionPicture.getFrameCount() - 1));
            Bitmap frame = motionPicture.getFrame(i1);
            Rect rect = new Rect(0, line, width, line + 1);
            canvas.drawBitmap(frame, rect, rect, null);
        }
        return newFrame;
    }

}
