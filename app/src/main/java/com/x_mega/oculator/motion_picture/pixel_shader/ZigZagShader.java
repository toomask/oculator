package com.x_mega.oculator.motion_picture.pixel_shader;

import android.graphics.Bitmap;

import com.x_mega.oculator.motion_picture.MotionPicture;

/**
 * Created by toomas on 20.10.2014.
 */
public class ZigZagShader implements PixelShader{

    @Override
    public int execute(int color, int x, int y, int frame, MotionPicture motionPicture) {
        Bitmap bitmap = motionPicture.getFrame(frame);
        int changa = (y + x % 10) % 20;
        changa = Math.abs(changa - (y + x % 10) %10);
        return bitmap.getPixel((x + changa) % bitmap.getWidth(), y);
    }
}
