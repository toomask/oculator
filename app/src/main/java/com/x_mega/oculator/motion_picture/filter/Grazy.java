package com.x_mega.oculator.motion_picture.filter;

import com.x_mega.oculator.motion_picture.MotionPicture;
import com.x_mega.oculator.motion_picture.pixel_shader.PixelShader;

/**
 * Created by toomas on 23.10.2014.
 */
public class Grazy extends ThreadedPixelFilter {

    public Grazy(PixelShader pixelShader) {
        super(pixelShader);
    }

    @Override
    public MotionPicture applyTo(MotionPicture sourcePicture) {
        this.mask = sourcePicture;
        return super.applyTo(sourcePicture);
    }
}
