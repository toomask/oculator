package com.x_mega.oculator.motion_picture.pixel_shader;

import android.graphics.Bitmap;

import com.x_mega.oculator.motion_picture.MotionPicture;

/**
 * Created by toomas on 21.10.2014.
 */
public class InterlacingShader implements PixelShader {

    @Override
    public int execute(int color, int x, int y, int frame, MotionPicture motionPicture) {
        if (y % 2 == 0) {
            return color;
        } else {
            Bitmap bitmap = motionPicture.getFrame((frame + 1) % motionPicture.getFrameCount());
            return bitmap.getPixel(x, y);
        }
    }

}
