package com.x_mega.oculator.motion_picture.filter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.x_mega.oculator.motion_picture.BasicMotionPicture;
import com.x_mega.oculator.motion_picture.MotionPicture;

/**
 * Created by toomas on 10.10.2014.
 */
public class GridFilter implements Filter {

    @Override
    public MotionPicture applyTo(MotionPicture motionPicture) {
        int offset = motionPicture.getFrameCount() / 4;

        BasicMotionPicture newPicture = new BasicMotionPicture();
        for (int i = 0; i < motionPicture.getFrameCount(); i++ ) {
            Bitmap oldFrame = motionPicture.getFrame(i);
            Bitmap newFrame = Bitmap.createBitmap(oldFrame.getWidth(), oldFrame.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(newFrame);

            canvas.drawBitmap(oldFrame, new Rect(0, 0, oldFrame.getWidth(), oldFrame.getHeight()),
                    new Rect(0, 0, newFrame.getWidth()/2, newFrame.getHeight()/2), null);

            oldFrame = motionPicture.getFrame((i + offset) % motionPicture.getFrameCount());
            canvas.drawBitmap(oldFrame, new Rect(0, 0, oldFrame.getWidth(), oldFrame.getHeight()),
                    new Rect(newFrame.getWidth()/2, 0, newFrame.getWidth(), newFrame.getHeight()/2), null);

            oldFrame = motionPicture.getFrame((i + 2 * offset) % motionPicture.getFrameCount());
            canvas.drawBitmap(oldFrame, new Rect(0, 0, oldFrame.getWidth(), oldFrame.getHeight()),
                    new Rect(0, newFrame.getHeight()/2, newFrame.getWidth()/2, newFrame.getHeight()), null);

            oldFrame = motionPicture.getFrame((i + 3 * offset) % motionPicture.getFrameCount());
            canvas.drawBitmap(oldFrame, new Rect(0, 0, oldFrame.getWidth(), oldFrame.getHeight()),
                    new Rect(newFrame.getWidth()/2, newFrame.getHeight()/2, newFrame.getWidth(), newFrame.getHeight()), null);

            newPicture.addFrame(newFrame);
            newPicture.setFrameDuration(i, motionPicture.getFrameDuration(i));
        }

        return newPicture;
    }

}

