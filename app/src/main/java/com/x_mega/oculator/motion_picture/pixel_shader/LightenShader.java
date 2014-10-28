package com.x_mega.oculator.motion_picture.pixel_shader;

import android.graphics.Color;

import com.x_mega.oculator.motion_picture.MotionPicture;

/**
 * Created by toomas on 20.10.2014.
 */
public class LightenShader implements PixelShader{

    int intensity;

    public LightenShader(int intensity) {
        this.intensity = intensity;
    }

    @Override
    public int execute(int color, int x, int y, int frame, MotionPicture motionPicture) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);

        int newColor = Color.argb(255,
                red + (255 - red) / intensity,
                green + (255 - green) / intensity,
                blue + (255 - blue) / intensity
        );

        return newColor;
    }
}
