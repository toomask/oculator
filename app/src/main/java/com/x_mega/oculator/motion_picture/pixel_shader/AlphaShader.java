package com.x_mega.oculator.motion_picture.pixel_shader;

import android.graphics.Color;

import com.x_mega.oculator.motion_picture.MotionPicture;

/**
 * Created by toomas on 20.10.2014.
 */
public class AlphaShader implements PixelShader {

    @Override
    public int execute(int color, int x, int y, int frame, MotionPicture motionPicture) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(
                (red + green + blue) / 3,
                red, green, blue );
    }
}
