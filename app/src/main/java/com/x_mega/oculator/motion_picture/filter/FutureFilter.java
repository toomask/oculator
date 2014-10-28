package com.x_mega.oculator.motion_picture.filter;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.x_mega.oculator.motion_picture.MotionPicture;
import com.x_mega.oculator.motion_picture.pixel_shader.PixelShader;

/**
 * Created by toomas on 19.10.2014.
 */
public class FutureFilter implements Filter {

    @Override
    public MotionPicture applyTo(MotionPicture motionPicture) {
        MotionPicture overlay = new ThreadedPixelFilter(new Shader()).applyTo(motionPicture);
        MotionPicture darkenedPicture = new ColorFilter("#60000000").applyTo(motionPicture);
        return new BlendFilter(overlay, 255).applyTo(darkenedPicture);
    }

    public class Shader implements PixelShader {

        @Override
        public int execute(int color1, int x, int y, int frame, MotionPicture motionPicture) {
            int previousFrameId = frame - 1;
            if (previousFrameId < 0) {
                previousFrameId = motionPicture.getFrameCount() - 1;
            }
            Bitmap previousFrame = motionPicture.getFrame(previousFrameId);
            int color2 = previousFrame.getPixel(x, y);
            int red = Math.abs(Color.red(color1) - Color.red(color2));
            int green = Math.abs(Color.green(color1) - Color.green(color2));
            int blue = Math.abs(Color.blue(color1) - Color.blue(color2));
            int biggestDif = Math.max(red, Math.max(green, blue));
            return Color.argb(
                    biggestDif,
                    0,
                    biggestDif,
                    0
            );
        }

    }
}
