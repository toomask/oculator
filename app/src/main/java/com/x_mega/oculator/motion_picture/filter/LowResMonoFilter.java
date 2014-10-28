package com.x_mega.oculator.motion_picture.filter;

import com.x_mega.oculator.motion_picture.MotionPicture;
import com.x_mega.oculator.motion_picture.pixel_shader.BlackAndWhiteShader;
import com.x_mega.oculator.motion_picture.pixel_shader.LowColorShader;

/**
 * Created by toomas on 18.10.2014.
 */
public class LowResMonoFilter implements Filter {

    @Override
    public MotionPicture applyTo(MotionPicture motionPicture) {
        MotionPicture motionPicture1 = new ChangeSizeFilter(0.1f, 0.1f).applyTo(motionPicture);
        MotionPicture motionPicture2 = new ThreadedPixelFilter(new BlackAndWhiteShader()).applyTo(motionPicture1);
        MotionPicture motionPicture3 = new ThreadedPixelFilter(new LowColorShader(128)).applyTo(motionPicture2);
        MotionPicture motionPicture4 = new ChangeSizeFilter(10f, 10f).applyTo(motionPicture3);
        return motionPicture4;

    }
}
