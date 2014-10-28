package com.x_mega.oculator.motion_picture;

import android.graphics.Bitmap;
import android.os.Build;

/**
 * Created by toomas on 15.10.2014.
 */
public class MotionPictureUtils {

    public static int calculateSize(MotionPicture motionPicture) {
        int totalSize = 0;
        for (int i = 0; i < motionPicture.getFrameCount(); i++) {
            totalSize += sizeOf(motionPicture.getFrame(i));
        }
        return totalSize;
    }

    private static int sizeOf(Bitmap data) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR1) {
            return data.getRowBytes() * data.getHeight();
        } else {
            return data.getByteCount();
        }
    }

}
