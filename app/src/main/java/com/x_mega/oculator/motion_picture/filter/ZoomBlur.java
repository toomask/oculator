package com.x_mega.oculator.motion_picture.filter;

import com.x_mega.oculator.motion_picture.BasicMotionPicture;
import com.x_mega.oculator.motion_picture.MotionPicture;

/**
 * Created by toomas on 21.10.2014.
 */
public class ZoomBlur implements Filter{

    @Override
    public MotionPicture applyTo(MotionPicture motionPicture) {
        int originalWidth = motionPicture.getFrame(0).getWidth();
        int originalHeight = motionPicture.getFrame(0).getHeight();

        MotionPicture newPic = BasicMotionPicture.copy(motionPicture);

        for (int i = 1; i < 8; i++) {
            MotionPicture stretchedPic = new ChangeSizeFilter(originalWidth + i * 2, originalHeight + i * 2).applyTo(newPic);
            MotionPicture overlay = new Crop(originalWidth, originalHeight, i, i).applyTo(stretchedPic);
            newPic = new BlendFilter(overlay, 80).applyTo(newPic);
        }

        return newPic;
    }

}
