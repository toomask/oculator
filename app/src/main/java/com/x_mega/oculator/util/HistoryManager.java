package com.x_mega.oculator.util;

import android.app.ActivityManager;
import android.content.Context;

import com.x_mega.oculator.motion_picture.BasicMotionPicture;
import com.x_mega.oculator.motion_picture.MotionPicture;
import com.x_mega.oculator.motion_picture.MotionPictureUtils;

import java.util.ArrayList;

public class HistoryManager {

    private static final int MINIMUM_AMOUNT_OF_MEMORY_TO_KEEP_FREE = 50; //mb
    private static final int AMOUNT_OF_MEMORY_TO_FREE_UP_WHILE_RESTRICTING_HISTORY_SIZE = 10; //mb

    private ArrayList<MotionPicture> history = new ArrayList<MotionPicture>();

    ActivityManager activityManager;

    public HistoryManager(Context context) {
        activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    }

    public void addToHistory(MotionPicture motionPicture) {
        int availableMemory = getAvailableMemory();
        if (availableMemory < MINIMUM_AMOUNT_OF_MEMORY_TO_KEEP_FREE) {
            int freedMemory = 0;
            while (toMegaBytes(freedMemory) < AMOUNT_OF_MEMORY_TO_FREE_UP_WHILE_RESTRICTING_HISTORY_SIZE
                    && history.size() > 0) {
                freedMemory += MotionPictureUtils.calculateSize(history.remove(0));
            }
            System.gc();
        } else if (availableMemory < toMegaBytes(MotionPictureUtils.calculateSize(motionPicture))) {
            if (history.size() > 0) {
                history.remove(0);
            }
        }
     //   Log.d("memory_test", "HS:  " + history.size());
        history.add(BasicMotionPicture.copy(motionPicture));
    }

    public MotionPicture removeLastPicture() {
        if (history.size() > 0) {
            return history.remove(history.size() - 1);
        } else {
            return null;
        }
    }

    public int pictureCount() {
        return history.size();
    }

    private int getAvailableMemory() {
        long usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        int maxUsableMemory = activityManager.getLargeMemoryClass();
        int availableMemory = maxUsableMemory - toMegaBytes(usedMemory);
      //  Log.d("memory_test", "MEM: " + availableMemory);
        return availableMemory;
    }

    private int toMegaBytes(long bytes) {
        return (int) (bytes / 1048576L);
    }
}