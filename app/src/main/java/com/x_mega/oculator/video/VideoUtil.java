package com.x_mega.oculator.video;

import android.graphics.Bitmap;

import com.x_mega.oculator.motion_picture.MotionPicture;

import org.jcodec.api.SequenceEncoder;
import org.jcodec.common.model.ColorSpace;
import org.jcodec.common.model.Picture;

import java.io.File;
import java.io.IOException;

/**
 * Created by toomas on 16.10.2014.
 */
public class VideoUtil {

    public static String encodeVideoAndSaveToSdCard(MotionPicture motionPicture) {
        String path = "/sdcard/" + generateFileName();
        try {
            File file = new File(path);
            SequenceEncoder encoder = new SequenceEncoder(file);

            // only 5 frames in total
            for (int i = 0; i < motionPicture.getFrameCount(); i++) {

                Bitmap bitmap = motionPicture.getFrame(i);
                Picture picture = fromBitmap(bitmap);
                int remainingTime = motionPicture.getFrameDuration(i);
                while (remainingTime > 0) {
                    encoder.encodeNativeFrame(picture);
                    remainingTime -= 40;
                }
            }
            encoder.finish();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    // convert from Bitmap to Picture (jcodec native structure)
    public static Picture fromBitmap(Bitmap src) {
        Picture dst = Picture.create((int)src.getWidth(), (int)src.getHeight(), ColorSpace.RGB);
        fromBitmap(src, dst);
        return dst;
    }

    public static void fromBitmap(Bitmap src, Picture dst) {
        int[] dstData = dst.getPlaneData(0);
        int[] packed = new int[src.getWidth() * src.getHeight()];

        src.getPixels(packed, 0, src.getWidth(), 0, 0, src.getWidth(), src.getHeight());

        for (int i = 0, srcOff = 0, dstOff = 0; i < src.getHeight(); i++) {
            for (int j = 0; j < src.getWidth(); j++, srcOff++, dstOff += 3) {
                int rgb = packed[srcOff];
                dstData[dstOff]     = (rgb >> 16) & 0xff;
                dstData[dstOff + 1] = (rgb >> 8) & 0xff;
                dstData[dstOff + 2] = rgb & 0xff;
            }
        }
    }

    private static String generateFileName() {
        return "video_" + System.currentTimeMillis() + ".mp4";
    }
}
