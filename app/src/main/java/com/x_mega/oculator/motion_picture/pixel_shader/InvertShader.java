package com.x_mega.oculator.motion_picture.pixel_shader;

import android.graphics.Color;

import com.x_mega.oculator.motion_picture.MotionPicture;

/**
 * Created by toomas on 9.10.2014.
 */
public class InvertShader implements PixelShader {

    @Override
    public int execute(int color, int x, int y, int frame, MotionPicture motionPicture) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);


        int newColor = Color.argb(255,
                255 - red,
                255 - green,
                255 - blue
        );

        return newColor;
    }

}
