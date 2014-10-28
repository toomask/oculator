package com.x_mega.oculator.motion_picture.filter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.RenderScript;

import com.andraskindler.sandbox.ScriptC_invert;
import com.x_mega.oculator.motion_picture.BasicMotionPicture;
import com.x_mega.oculator.motion_picture.MotionPicture;

import java.util.ArrayList;

/**
 * Created by toomas on 20.10.2014.
 */
public class InvertFilter implements Filter {

    Context context;

    RenderScript renderScript;
    ScriptC_invert script;

    public InvertFilter(Context context) {
        this.context = context;
        renderScript = RenderScript.create(context);
        script = new ScriptC_invert(renderScript);
    }

    @Override
    public MotionPicture applyTo(MotionPicture source) {
        final BasicMotionPicture motionPicture = BasicMotionPicture.copy(source);
        ArrayList<Thread> threads = new ArrayList<Thread>();
        for (int i = 0; i < motionPicture.getFrameCount() ; i++) {
            final int finalI = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    motionPicture.setFrame(finalI, applyTo(motionPicture.getFrame(finalI)));
                }
            };
            Thread thread = new Thread(runnable);
            threads.add(thread);
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return motionPicture;
    }

    private Bitmap applyTo(Bitmap bitmap) {
        final Allocation alloc1 = Allocation.createFromBitmap(renderScript, bitmap, Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);
        final Allocation alloc2 = Allocation.createTyped(renderScript, alloc1.getType());
        script.forEach_dim(alloc1, alloc2);
        alloc2.copyTo(bitmap);
        return bitmap;
    }
}
