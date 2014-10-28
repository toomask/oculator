package com.x_mega.oculator.motion_picture.drawing_controller;

import android.graphics.Bitmap;
import android.view.MotionEvent;

/**
 * Created by toomas on 10.10.2014.
 */
public interface DrawingController {

    public void onSizeChanged(int width, int height);
    public void onTouchEvent(MotionEvent event);
    public Bitmap drawTo(Bitmap baseFrame);

}
