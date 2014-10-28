package com.x_mega.oculator.motion_picture.pixel_shader;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.x_mega.oculator.motion_picture.MotionPicture;

/**
 * Created by toomas on 18.10.2014.
 */
public class ShiftShader implements PixelShader {

    @Override
    public int execute(int color, int x, int y, int frame, MotionPicture motionPicture) {
        Bitmap bitmap = motionPicture.getFrame(frame);

        int shift = Math.abs(( frame ) - ( motionPicture.getFrameCount() / 2 )) * 1;

        return Color.argb(255,
                Color.red(bitmap.getPixel(Math.min(bitmap.getWidth() - 1, x + shift), y)),
                Color.green(bitmap.getPixel(Math.max(0, x - shift), y)),
                Color.blue(color)
                );
    }
}
