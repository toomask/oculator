package com.x_mega.oculator.motion_picture.filter;

import com.x_mega.oculator.motion_picture.BasicMotionPicture;
import com.x_mega.oculator.motion_picture.MotionPicture;
import com.x_mega.oculator.motion_picture.pixel_shader.PixelShader;

import java.util.ArrayList;

/**
 * Created by toomas on 9.10.2014.
 */
public class ThreadedPixelFilter extends PixelFilter {

    public ThreadedPixelFilter(PixelShader pixelShader) {
        super(pixelShader);
    }

    public ThreadedPixelFilter(PixelShader pixelShader, MotionPicture mask) {
        super(pixelShader, mask);
    }

    @Override
    public MotionPicture applyTo(final MotionPicture sourcePicture) {
        final BasicMotionPicture motionPicture = BasicMotionPicture.copy(sourcePicture);
        ArrayList<Thread> threads = new ArrayList<Thread>();
        for (int i = 0; i < motionPicture.getFrameCount() ; i++) {
            final int finalI = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    motionPicture.setFrame(finalI,
                            applyToFrame(sourcePicture.getFrame(finalI),
                                    finalI, sourcePicture));
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
}
