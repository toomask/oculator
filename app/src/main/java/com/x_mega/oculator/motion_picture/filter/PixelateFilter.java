package com.x_mega.oculator.motion_picture.filter;

import com.x_mega.oculator.motion_picture.MotionPicture;

/**
 * Created by toomas on 14.10.2014.
 */
public class PixelateFilter implements Filter {

    @Override
    public MotionPicture applyTo(MotionPicture motionPicture) {
        MotionPicture smallPickture = new ChangeSizeFilter(0.33f, 0.33f).applyTo(motionPicture);
        return new ChangeSizeFilter(3f, 3f).applyTo(smallPickture);
    }
}
