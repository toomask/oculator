package com.x_mega.oculator.motion_picture.filter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.x_mega.oculator.R;
import com.x_mega.oculator.motion_picture.BasicMotionPicture;
import com.x_mega.oculator.motion_picture.MotionPicture;

/**
 * Created by toomas on 8.10.2014.
 */
public class RainbowFilter implements Filter {

    Bitmap rainbow;
    Rect srcRect;

    public RainbowFilter(Context context) {
        rainbow = BitmapFactory.decodeResource(context.getResources(), R.drawable.rainbow);
        srcRect = new Rect(0, 0, rainbow.getWidth(), rainbow.getHeight());
    }

    @Override
    public MotionPicture applyTo(MotionPicture motionPicture) {
        BasicMotionPicture newPicture = new BasicMotionPicture();
        for (int i = 0; i < motionPicture.getFrameCount(); i++) {
            Bitmap newFrame = motionPicture.getFrame(i).copy(Bitmap.Config.ARGB_8888, true);
            Canvas canvas = new Canvas(newFrame);
            int offset = i * newFrame.getWidth() / motionPicture.getFrameCount() ;

            canvas.drawBitmap(rainbow, srcRect,
                    new Rect(offset , 0, offset + newFrame.getWidth(), newFrame.getHeight()), null);
            canvas.drawBitmap(rainbow, srcRect,
                    new Rect(offset - newFrame.getWidth(), 0, offset, newFrame.getHeight()), null);
            newPicture.addFrame(newFrame);
            newPicture.setFrameDuration(i, motionPicture.getFrameDuration(i));
        }
        return newPicture;
    }

}
