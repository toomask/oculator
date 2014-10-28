package com.x_mega.oculator.motion_picture.filter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.RenderScript;

import com.andraskindler.sandbox.ScriptC_dim;
import com.x_mega.oculator.motion_picture.BasicMotionPicture;
import com.x_mega.oculator.motion_picture.MotionPicture;

/**
 * Created by toomas on 20.10.2014.
 */
public class DimFilter implements Filter {

    Context context;

    public DimFilter(Context context) {
        this.context = context;
    }

    @Override
    public MotionPicture applyTo(MotionPicture motionPicture) {
        BasicMotionPicture copy = BasicMotionPicture.copy(motionPicture);
        for (int i = 0; i < copy.getFrameCount(); i++) {
            copy.setFrame(i, applyTo(copy.getFrame(i)));
        }
        return copy;
    }

    private Bitmap applyTo(Bitmap bitmap) {
        final RenderScript renderScript = RenderScript.create(context);
        final ScriptC_dim script = new ScriptC_dim(renderScript);
        script.set_dimmingValue(0.6f);
        final Allocation alloc1 = Allocation.createFromBitmap(renderScript, bitmap, Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);
        final Allocation alloc2 = Allocation.createTyped(renderScript, alloc1.getType());
        script.forEach_dim(alloc1, alloc2);
        alloc2.copyTo(bitmap);
        return bitmap;
    }
}
