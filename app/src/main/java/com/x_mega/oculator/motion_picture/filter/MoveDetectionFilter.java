package com.x_mega.oculator.motion_picture.filter;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.x_mega.oculator.motion_picture.BasicMotionPicture;
import com.x_mega.oculator.motion_picture.MotionPicture;
import com.x_mega.oculator.motion_picture.pixel_shader.PixelShader;

import java.util.Random;

/**
 * Created by toomas on 18.10.2014.
 */
public class MoveDetectionFilter  implements Filter {

    @Override
    public MotionPicture applyTo(MotionPicture oldPicture) {
        MotionPicture motionPicture = BasicMotionPicture.copy(oldPicture);
        MotionPicture pic1 = new LowResMonoFilter().applyTo(motionPicture);
        MotionPicture pic2 = new ThreadedPixelFilter(new DifferenceShader()).applyTo(pic1);
        MotionPicture pic4 = new BlendFilter(pic2, 100).applyTo(motionPicture);
        return pic4;
    }

    class DifferenceShader implements PixelShader {
        @Override
        public int execute(int color, int x, int y, int frame, MotionPicture motionPicture) {
            int previousFrameId = frame - 1;
            if (previousFrameId < 0) {
                previousFrameId = motionPicture.getFrameCount() - 1;
            }
            Bitmap previousFrame = motionPicture.getFrame(previousFrameId);
            int previousColor = previousFrame.getPixel(x, y);

            if (previousColor == color) {
                return Color.BLACK;
            } else {
                return randomColor();
            }
        }
    }

    int randomColor() {
        return Color.argb(
                255,
                randInt(0, 255),
                randInt(0, 255),
                randInt(0, 255)
        );
    }

    public static int randInt(int min, int max) {

        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
}
