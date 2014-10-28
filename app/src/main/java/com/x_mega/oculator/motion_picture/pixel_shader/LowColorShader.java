package com.x_mega.oculator.motion_picture.pixel_shader;

import android.graphics.Color;

import com.x_mega.oculator.motion_picture.MotionPicture;

/**
 * Created by toomas on 9.10.2014.
 */
public class LowColorShader implements PixelShader {

    int reduceConstant = 1;

    public LowColorShader(int reduceConstant) {
        this.reduceConstant = reduceConstant;
    }

    @Override
    public int execute(int color, int x, int y, int frame, MotionPicture motionPicture) {

        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);

        red = reduceColor(red);
        green = reduceColor(green);
        blue = reduceColor(blue);

        int newColor = Color.argb(255, red, green, blue);

        return newColor;
    }

    private  int reduceColor(int green) {
        return green - green % reduceConstant;
    }
}
