package com.x_mega.oculator.motion_picture;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by toomas on 8.10.2014.
 */
public class BasicMotionPicture implements MotionPicture{

    private static final int DEFAULT_FRAME_DURATION = 95;

    List<Frame> frames = new ArrayList<Frame>();

    class Frame {
        Bitmap bitmap;
        int duration = DEFAULT_FRAME_DURATION;

        Frame(Bitmap bitmap) {
            this.bitmap = bitmap;
        }
    }

    public BasicMotionPicture(List<Bitmap> bitmaps) {
        for (Bitmap bitmap : bitmaps) {
            frames.add(new Frame(bitmap));
        }
    }

    public BasicMotionPicture() {
    }

    public BasicMotionPicture(MotionPicture motionPicture) {
        addFrames(motionPicture);
    }

    public static BasicMotionPicture copy(MotionPicture motionPicture) {
        BasicMotionPicture newPicture = new BasicMotionPicture();
        for (int i = 0; i < motionPicture.getFrameCount(); i++) {
            newPicture.addFrame(motionPicture.getFrame(i).copy(Bitmap.Config.ARGB_8888, true));
            newPicture.setFrameDuration(i, motionPicture.getFrameDuration(i));
        }
        return newPicture;
    }

    @Override
    public int getFrameCount() {
        return frames.size();
    }

    @Override
    public Bitmap getFrame(int index) {
        return frames.get(index).bitmap;
    }

    @Override
    public int getFrameDuration(int index) {
        return frames.get(index).duration;
    }

    public void setFrameDuration(int index, int duration) {
        frames.get(index).duration = duration;
    }

    public void addFrame(Bitmap bitmap) {
        frames.add(new Frame(bitmap));
    }

    public void setFrame(int index, Bitmap frame) {
        Frame object = new Frame(frame);
        object.duration = frames.get(index).duration;
        frames.set(index, object);
    }

    public void addFrames(MotionPicture motionPicture) {
        for (int i = 0; i < motionPicture.getFrameCount(); i++) {
            Frame frame = new Frame(motionPicture.getFrame(i));
            frame.duration = motionPicture.getFrameDuration(i);
            frames.add(frame);
        }
    }
}
