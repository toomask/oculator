package com.x_mega.oculator.motion_picture.filter;

import com.x_mega.oculator.motion_picture.MotionPicture;
import com.x_mega.oculator.motion_picture.pixel_shader.AlphaShader;
import com.x_mega.oculator.motion_picture.pixel_shader.MotionShader;

/**
 * Created by toomas on 20.10.2014.
 */
public class UltraFilter implements Filter {

    @Override
    public MotionPicture applyTo(MotionPicture motionPicture) {
        return new SmoothBlendFilter(
                new ThreadedPixelFilter(new AlphaShader()).applyTo(
                        new ThreadedPixelFilter(new MotionShader()).applyTo(motionPicture)
                )
        ).applyTo(motionPicture);
    }



}
