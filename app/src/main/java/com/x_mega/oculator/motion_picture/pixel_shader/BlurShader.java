package com.x_mega.oculator.motion_picture.pixel_shader;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.x_mega.oculator.motion_picture.MotionPicture;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by toomas on 9.10.2014.
 */
public class BlurShader implements PixelShader {

    private static int BLUR_RADIUS = 3;

    @Override
    public int execute(int color, int sourceX, int sourceY, int frameIndex, MotionPicture motionPicture) {
        ArrayList<Integer> colors = new ArrayList<Integer>();
        Bitmap b = motionPicture.getFrame(frameIndex);
        for(int x = sourceX - BLUR_RADIUS > 0 ? sourceX - BLUR_RADIUS : 0;
            x< ( sourceX + BLUR_RADIUS > b.getWidth() ? b.getWidth() :  sourceX + BLUR_RADIUS );
            x++){
            for(int y = sourceY - BLUR_RADIUS > 0 ? sourceY - BLUR_RADIUS : 0;
                y< ( sourceY + BLUR_RADIUS > b.getHeight() ? b.getHeight() :  sourceY + BLUR_RADIUS );
                y++){
                colors.add(b.getPixel(x, y));

            }
        }
        return meanColor(colors);
    }

    private int meanColor(List<Integer> colors) {
        int totalRed = 0;
        int totalGreen = 0;
        int totalBlue = 0;
        for (Integer color : colors) {
            totalRed += Color.red(color);
            totalGreen += Color.green(color);
            totalBlue += Color.blue(color);
        }
        int colorCount = colors.size();
        return Color.argb(255,
                totalRed / colorCount,
                totalGreen / colorCount,
                totalBlue / colorCount);
    }

    private int distanceBetween(int x1, int y1, int x2, int y2) {

        int dX = x1 - x2;    int dY = y1 - y2;

        return (int) Math.round( Math.sqrt(dX*dX + dY*dY) );
    }
}
