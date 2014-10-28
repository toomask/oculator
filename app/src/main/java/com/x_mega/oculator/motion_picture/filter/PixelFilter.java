package com.x_mega.oculator.motion_picture.filter;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.x_mega.oculator.motion_picture.BasicMotionPicture;
import com.x_mega.oculator.motion_picture.MotionPicture;
import com.x_mega.oculator.motion_picture.pixel_shader.PixelShader;

/**
 * Created by toomas on 9.10.2014.
 */
public class PixelFilter implements Filter {

     PixelShader pixelShader;
     MotionPicture mask;

    public PixelFilter(PixelShader pixelShader) {
        this.pixelShader = pixelShader;
    }

    public PixelFilter(PixelShader pixelShader, MotionPicture mask) {
        this.pixelShader = pixelShader;
        this.mask = mask;
    }

    @Override
    public MotionPicture applyTo(MotionPicture motionPicture) {
        BasicMotionPicture copy = BasicMotionPicture.copy(motionPicture);
        for (int i = 0; i < motionPicture.getFrameCount() ; i++) {
            copy.setFrame(i, applyToFrame(motionPicture.getFrame(i), i, motionPicture));
        }
        return copy;
    }

    protected Bitmap applyToFrame(Bitmap frame, int frameIndex, MotionPicture motionPicture) {
        if (mask == null) {
           return applyToFrameWithoutMask(frame, frameIndex, motionPicture);
        } else {
           return applyToFrameWithMask(frame, frameIndex, motionPicture);
        }
    }

    protected Bitmap applyToFrameWithoutMask(Bitmap frame, int frameIndex, MotionPicture motionPicture) {
        Bitmap copy = copy(frame);
        Bitmap b = copy;
        for(int x = 0; x<b.getWidth(); x++){
            for(int y = 0; y<b.getHeight(); y++){
                int oldColor = b.getPixel(x, y);
                int newColor = pixelShader.execute(oldColor, x, y, frameIndex, motionPicture);
                b.setPixel(x, y, newColor);
            }
        }
        return b;
    }

    protected Bitmap applyToFrameWithMask(Bitmap frame, int frameIndex, MotionPicture motionPicture) {
        Bitmap copy = copy(frame);
        Bitmap b = copy;
        MotionPicture mask = this.mask;
        Bitmap maskFrame = mask.getFrame(frameIndex % mask.getFrameCount());
        for(int x = 0; x<b.getWidth(); x++){
            for(int y = 0; y<b.getHeight(); y++){
                if (Color.red(maskFrame.getPixel(x, y)) >= 128) {
                    int oldColor = b.getPixel(x, y);
                    int newColor = pixelShader.execute(oldColor, x, y, frameIndex, motionPicture);
                    b.setPixel(x, y, newColor);
                }
            }
        }
        return b;
    }

    private Bitmap copy(Bitmap frame) {
        Bitmap copy = frame.copy(frame.getConfig(), true);
        if (copy == null) {
            Bitmap.createScaledBitmap(frame, frame.getWidth(), frame.getHeight(), false);
        }
        if (copy == null) {
            copy = frame;
        }
        copy.setHasAlpha(true);
        return copy;
    }
}
