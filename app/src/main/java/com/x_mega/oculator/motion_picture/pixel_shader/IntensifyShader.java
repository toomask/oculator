package com.x_mega.oculator.motion_picture.pixel_shader;

import android.graphics.Color;

import com.x_mega.oculator.motion_picture.MotionPicture;

/**
 * Created by toomas on 20.10.2014.
 */
public class IntensifyShader implements PixelShader{

    int intensity = 5;

    public IntensifyShader() {}

    public IntensifyShader(int intensity) {
        this.intensity = intensity;
    }

    @Override
    public int execute(int color, int x, int y, int frame, MotionPicture motionPicture) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);

        int newColor = Color.argb(255,
                intensify(red),
                intensify(green),
                intensify(blue)
        );

        return newColor;
    }

    private int intensify(int value) {
        if (value < 128) {
            return value - (value / intensity);
        } else {
            return value + (255 - value) / intensity;
        }
    }
}
