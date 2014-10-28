package com.x_mega.oculator.motion_picture.pixel_shader;

import android.graphics.Color;

import com.x_mega.oculator.motion_picture.MotionPicture;

import java.util.Random;

/**
 * Created by toomas on 22.10.2014.
 */
public class GrainShader implements PixelShader{

    int maxChange = 10;

    Random rand = new Random();

    @Override
    public int execute(int color, int x, int y, int frame, MotionPicture motionPicture) {
        return Color.argb(
                255,
                generateNewValue(Color.red(color)),
                generateNewValue(Color.green(color)),
                generateNewValue(Color.blue(color))
        );
    }

    private int generateNewValue(int oldValue) {
        int max = Math.min(255, oldValue + maxChange);
        int min = Math.max(0, oldValue - maxChange);

        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

}
