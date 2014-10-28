package com.x_mega.oculator.motion_picture.filter;

import com.x_mega.oculator.motion_picture.BasicMotionPicture;
import com.x_mega.oculator.motion_picture.MotionPicture;

/**
 * Created by toomas on 11.10.2014.
 */
public class BackToBackFilter implements Filter {

    @Override
    public MotionPicture applyTo(MotionPicture motionPicture) {
        BasicMotionPicture newPicture = new BasicMotionPicture(motionPicture);
        newPicture.addFrames(new ReverseFilter().applyTo(motionPicture));
        return newPicture;
    }

}
