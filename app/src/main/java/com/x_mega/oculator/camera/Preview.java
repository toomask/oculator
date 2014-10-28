package com.x_mega.oculator.camera;

import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.x_mega.oculator.R;

import java.io.IOException;

public class Preview extends FrameLayout implements SurfaceHolder.Callback{

    public static Preview create(Context context) {
        Preview mView = new Preview(context);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.RIGHT | Gravity.TOP;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.addView(mView, params);
        return mView;
    }

    SurfaceView surfaceView;
    SurfaceHolder holder;
    Camera camera;

    public Preview(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.camera_preview, this, true);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        holder = surfaceView.getHolder();
        holder.addCallback(this);
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void surfaceCreated(SurfaceHolder holder) {
        try {
            if (camera != null) {
                camera.setPreviewDisplay(holder);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        if (camera != null) {
            camera.stopPreview();
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        if(camera != null) {
            camera.startPreview();
        }
    }

    public void release() {
            ((WindowManager) getContext().getSystemService(
                    Context.WINDOW_SERVICE)).removeView(this);
    }

}
