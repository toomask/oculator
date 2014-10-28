package com.x_mega.oculator.motion_picture.filter;

import com.x_mega.oculator.motion_picture.BasicMotionPicture;
import com.x_mega.oculator.motion_picture.MotionPicture;

/**
 * Created by toomas on 10.10.2014.
 */
public class ReverseFilter implements Filter {

    @Override
    public MotionPicture applyTo(MotionPicture motionPicture) {
        BasicMotionPicture newPicture = new BasicMotionPicture();
        for (int i = motionPicture.getFrameCount() - 1; !(i < 0) ; i--) {
            newPicture.addFrame(motionPicture.getFrame(i));
            newPicture.setFrameDuration(newPicture.getFrameCount() - 1, motionPicture.getFrameDuration(i));
        }
        return newPicture;
    }
}
