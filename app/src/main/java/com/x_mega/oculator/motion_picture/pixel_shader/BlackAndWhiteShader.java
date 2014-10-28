package com.x_mega.oculator.motion_picture.pixel_shader;

import android.graphics.Color;

import com.x_mega.oculator.motion_picture.MotionPicture;

/**
 * Created by toomas on 18.10.2014.
 */
public class BlackAndWhiteShader implements PixelShader {

    @Override
    public int execute(int color, int x, int y, int frame, MotionPicture motionPicture) {
        int level = (Color.red(color) + Color.green(color) + Color.blue(color)) / 3;
        return Color.argb(255, level, level, level);
    }
}
