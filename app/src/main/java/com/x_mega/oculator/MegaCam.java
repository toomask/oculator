package com.x_mega.oculator;

import android.app.Application;

import com.x_mega.oculator.motion_picture.MotionPicture;

/**
 * Created by toomas on 8.10.2014.
 */
public class MegaCam extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
    }

    static MotionPicture lastCapturedPicture;

    public static MotionPicture getLastCapturedPicture() {
        return lastCapturedPicture;
    }

    public static void setLastCapturedPicture(MotionPicture lastCapturedPicture) {
        MegaCam.lastCapturedPicture = lastCapturedPicture;
    }


    static MotionPicture pictureToShare;

    public static void setPictureToShare(MotionPicture pictureToShare) {
        MegaCam.pictureToShare = pictureToShare;
    }

    public static MotionPicture getPictureToShare() {
        return pictureToShare;
    }
}
