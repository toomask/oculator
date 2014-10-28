package com.x_mega.oculator.motion_picture.pixel_shader;

import com.x_mega.oculator.motion_picture.MotionPicture;

/**
 * Created by toomas on 11.10.2014.
 */
public interface PixelShader {
    int execute(int color, int x, int y, int frame, MotionPicture motionPicture);
}
