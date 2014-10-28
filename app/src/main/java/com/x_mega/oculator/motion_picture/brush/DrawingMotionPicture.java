package com.x_mega.oculator.motion_picture.brush;

import android.graphics.Bitmap;
import android.view.MotionEvent;
import android.view.View;

import com.x_mega.oculator.motion_picture.MotionPicture;

import java.util.ArrayList;

/**
 * Created by toomas on 21.10.2014.
 */
public class DrawingMotionPicture implements MotionPicture, View.OnTouchListener {

    MotionPicture basePicture;

    public void setBasePicture(MotionPicture motionPicture) {
        this.basePicture = motionPicture;
    }

    Brush brush;

    public void setBrush(Brush brush) {
        this.brush = brush;
    }

    @Override
    public int getFrameCount() {
        return basePicture.getFrameCount();
    }

    @Override
    public Bitmap getFrame(int index) {
        return null;
    }

    @Override
    public int getFrameDuration(int index) {
        return basePicture.getFrameDuration(index);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Brush.Event brushEvent = new Brush.Event();
        brushEvent.pointers = new ArrayList<Brush.Event.Pointer>();
        for (int i = 0; i < event.getPointerCount(); i++) {
            Brush.Event.Pointer pointer = new Brush.Event.Pointer();

        }
        return true;
    }
}
