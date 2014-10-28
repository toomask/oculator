package com.x_mega.oculator.motion_picture.filter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.x_mega.oculator.motion_picture.BasicMotionPicture;
import com.x_mega.oculator.motion_picture.MotionPicture;

/**
 * Created by toomas on 22.10.2014.
 */
public class Distort implements Filter {

    @Override
    public MotionPicture applyTo(MotionPicture motionPicture) {
        BasicMotionPicture newPicture = new BasicMotionPicture();
        Bitmap frame1 = motionPicture.getFrame(0);
        int width = frame1.getWidth();
        int height = frame1.getHeight();
        int sliceHeight = height/motionPicture.getFrameCount();
        for (int i = 0; i < motionPicture.getFrameCount(); i++) {
            Bitmap newFrame = Bitmap.createBitmap(height, width,
                    Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(newFrame);
            for (int j = 0; j < motionPicture.getFrameCount(); j++) {
                Bitmap frame = motionPicture.getFrame((i + j) % motionPicture.getFrameCount());
                Rect rect = new Rect(0, sliceHeight * j, width, sliceHeight * (j + 1));
                canvas.drawBitmap(frame, rect, rect, null);
            }
            newPicture.addFrame(newFrame);
            newPicture.setFrameDuration(i, motionPicture.getFrameDuration(i));
        }
        return newPicture;
    }
}
