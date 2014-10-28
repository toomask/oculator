package com.x_mega.oculator.motion_picture.filter;

import android.graphics.Bitmap;

import com.x_mega.oculator.motion_picture.MotionPicture;

/**
 * Created by toomas on 20.10.2014.
 */
public class StabilizerFilter implements Filter {

    @Override
    public MotionPicture applyTo(MotionPicture motionPicture) {
        MotionPicture smallPic = new ChangeSizeFilter(10f, 10f).applyTo(new ChangeSizeFilter(0.1f, 0.1f).applyTo(motionPicture));


        return null;
    }

    private Point calculateOffset(Bitmap from, Bitmap to) {
        return null;
    }

    private int calculateDif(Bitmap from, Bitmap to, Point offset) {
        int minX = Math.max(0, offset.x);
        int maxX = Math.min(from.getWidth(), from.getWidth() - offset.x);
        int minY = Math.max(0, offset.y);
        int maxY = Math.min(from.getHeight(), from.getHeight() - offset.y);
        int dif = 0;
        int count = 0;
        for (int x = minX; x < maxX; x++) {
            for (int y = minY; y < maxY; y++) {
                int sourcePixel = from.getPixel(x, y);
                int comparisonPixel = to.getPixel(x - offset.x, y - offset.y);

            }
        }
        return 0;
    }


    class Point { int x, y; }

}
