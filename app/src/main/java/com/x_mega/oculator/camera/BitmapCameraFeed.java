package com.x_mega.oculator.camera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;

import java.io.ByteArrayOutputStream;

/**
 * Created by toomas on 18.09.2014.
 */
public class BitmapCameraFeed implements CameraFeed.FrameReceiver {

    CameraFeed cameraFeed;
    FrameReceiver frameReceiver;

    public interface FrameReceiver {
        public void onFrame(Bitmap frame);
    }

    public BitmapCameraFeed(Context context, FrameReceiver frameReceiver) {
        this.frameReceiver = frameReceiver;
        cameraFeed = new CameraFeed(context, this);
    }

    public void startCamera() {
        cameraFeed.startCamera();
    }

    public void stopCamera() {
        cameraFeed.stopCamera();
    }

    @Override
    public void onFrame(byte[] bytes, Camera camera) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        YuvImage yuvImage = new YuvImage(bytes, camera.getParameters().getPreviewFormat(),
                CameraFeed.PIC_WIDTH, CameraFeed.PIC_HEIGHT, null);
        yuvImage.compressToJpeg(new Rect(0, 0, CameraFeed.PIC_WIDTH, CameraFeed.PIC_HEIGHT), 98, out);
        byte[] imageBytes = out.toByteArray();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        bitmap = cropToSquareAndRotate(bitmap);
        frameReceiver.onFrame(bitmap);
    }

    private Bitmap cropToSquareAndRotate(Bitmap source) {
        Bitmap bitmap = Bitmap.createBitmap(240, 240, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        if (source.getWidth() >= source.getHeight()) {
            int difference = source.getWidth() - source.getHeight();
            canvas.drawBitmap(source,
                    new Rect(difference/2, 0, source.getWidth() - difference/2, source.getHeight()),
                    new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), null);
        } else {
            int difference = source.getHeight() - source.getWidth();
            canvas.drawBitmap(source,
                    new Rect(0, difference/2, source.getWidth(), source.getHeight() - difference/2),
                    new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), null);
        }

        Matrix matrix = new Matrix();
        matrix.setRotate(90);

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

       // return bitmap;
    }
}
