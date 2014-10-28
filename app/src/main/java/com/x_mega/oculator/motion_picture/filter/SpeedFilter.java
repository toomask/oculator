package com.x_mega.oculator.motion_picture.filter;

import com.x_mega.oculator.motion_picture.BasicMotionPicture;
import com.x_mega.oculator.motion_picture.MotionPicture;

/**
 * Created by toomas on 8.10.2014.
 */
public class SpeedFilter implements Filter {

    float speedMultiplier;

    public SpeedFilter(float speedMultiplier) {
        this.speedMultiplier = speedMultiplier;
    }

    @Override
    public MotionPicture applyTo(MotionPicture motionPicture) {
        BasicMotionPicture newPicture = new BasicMotionPicture(motionPicture);
        for (int i = 0; i < newPicture.getFrameCount(); i++) {
            newPicture.setFrameDuration(i, Math.round(newPicture.getFrameDuration(i)*speedMultiplier));
        }
        return newPicture;
    }
}
