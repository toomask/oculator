package com.x_mega.oculator.motion_picture;

import android.graphics.Bitmap;

/**
 * Created by toomas on 8.10.2014.
 */
public interface MotionPicture {
    public int getFrameCount();
    public Bitmap getFrame(int index);
    public int getFrameDuration(int index);
}
