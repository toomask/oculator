package com.x_mega.oculator.motion_picture.brush;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by toomas on 21.10.2014.
 */
public interface Brush {

    public static class Event {

        public static final int ACTION_UP = 1;
        public static final int ACTION_DOWN = 2;
        public static final int ACTION_MOVE = 3;

        public static class Pointer {
            int action;
            int x;
            int y;
        }

        public List<Pointer> pointers;
    }

    public void onPaintEvent(Event event);
    public Bitmap drawTo(Bitmap frame);

}
