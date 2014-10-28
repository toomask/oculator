package com.x_mega.oculator.motion_picture.pixel_shader;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.x_mega.oculator.motion_picture.MotionPicture;

/**
 * Created by toomas on 19.10.2014.
 */
public class MotionShader implements PixelShader {

    @Override
    public int execute(int color1, int x, int y, int frame, MotionPicture motionPicture) {
        int previousFrameId = frame - 1;
        if (previousFrameId < 0) {
            previousFrameId = motionPicture.getFrameCount() - 1;
        }
        Bitmap previousFrame = motionPicture.getFrame(previousFrameId);
        int color2 = previousFrame.getPixel(x, y);
        return Color.argb(
                255,
                Math.abs(Color.red(color1) - Color.red(color2)),
                Math.abs(Color.green(color1) - Color.green(color2)),
                Math.abs(Color.blue(color1) - Color.blue(color2))
        );
    }

}
