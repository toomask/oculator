package com.x_mega.oculator.camera;

import android.content.Context;
import android.hardware.Camera;
import android.os.Handler;
import android.widget.Toast;

public class CameraFeed implements Camera.PreviewCallback {

    public static final int PIC_WIDTH = 640;
    public static final int PIC_HEIGHT = 480;

    public interface FrameReceiver {
        public void onFrame(byte[] bytes, Camera camera);
    }

    Context context;
    FrameReceiver frameReceiver;
    Preview preview;
    Camera camera;
    Handler handler;

    public CameraFeed(Context context, FrameReceiver frameReceiver) {
        this.context = context;
        this.frameReceiver = frameReceiver;
    }

    public void startCamera() {
        Toast.makeText(context, "starting camera...", Toast.LENGTH_SHORT).show();
        handler = new Handler();
        preview = Preview.create(context);
        resumeCamera();
    }

    public void stopCamera() {
        Toast.makeText(context, "stopping camera...", Toast.LENGTH_SHORT).show();
        pauseCamera();
        preview.release();
    }

    private void resumeCamera() {
        try {
            camera = Camera.open(0);

            Camera.Parameters parameters = camera.getParameters();
            parameters.setPictureSize(PIC_WIDTH, PIC_HEIGHT);
            parameters.setPreviewSize(PIC_WIDTH, PIC_HEIGHT);
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
            camera.setParameters(parameters);

            camera.setPreviewCallback(this);
            camera.startPreview();
            preview.setCamera(camera);
        } catch (Exception e) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    resumeCamera();
                }
            }, 200);
        }
    }

    private void pauseCamera() {
        if(camera != null) {
            camera.setPreviewCallback(null);
            camera.stopPreview();
            preview.setCamera(null);
            camera.release();
            camera = null;
        }
    }

    @Override
    public void onPreviewFrame(byte[] bytes, Camera camera) {
        frameReceiver.onFrame(bytes, camera);
    }

}
