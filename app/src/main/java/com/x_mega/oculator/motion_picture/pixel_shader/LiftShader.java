package com.x_mega.oculator.motion_picture.pixel_shader;

import android.graphics.Bitmap;

import com.x_mega.oculator.motion_picture.MotionPicture;

/**
 * Created by toomas on 22.10.2014.
 */
public class LiftShader implements PixelShader {

    @Override
    public int execute(int color, int x, int y, int frame, MotionPicture motionPicture) {
        Bitmap bitmap = motionPicture.getFrame(frame);
        return bitmap.getPixel(x, (y + frame *  bitmap.getHeight() / motionPicture.getFrameCount()) % bitmap.getHeight());
    }
}
