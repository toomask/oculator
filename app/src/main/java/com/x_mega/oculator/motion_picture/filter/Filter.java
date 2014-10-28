package com.x_mega.oculator.motion_picture.filter;

import com.x_mega.oculator.motion_picture.MotionPicture;

/**
 * Created by toomas on 8.10.2014.
 */
public interface Filter {
    public MotionPicture applyTo(MotionPicture motionPicture);
}
