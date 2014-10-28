package com.x_mega.oculator.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.x_mega.oculator.MegaCam;
import com.x_mega.oculator.R;
import com.x_mega.oculator.camera.BitmapCameraFeed;
import com.x_mega.oculator.motion_picture.BasicMotionPicture;
import com.x_mega.oculator.view.MotionPictureView;
import com.x_mega.oculator.view.ShutterControllerView;

/**
 * Created by toomas on 8.10.2014.
 */
public class CaptureActivity extends Activity {

    BitmapCameraFeed cameraFeed;
    Bitmap lastFrame;
    MotionPictureView motionPictureView;
    ShutterControllerView shutterControllerView;
    BasicMotionPicture motionPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);
        motionPictureView = (MotionPictureView) findViewById(R.id.motionPictureView);
        shutterControllerView = (ShutterControllerView) findViewById(R.id.shutterView);

        cameraFeed = new BitmapCameraFeed(this, new BitmapCameraFeed.FrameReceiver() {

            @Override
            public void onFrame(Bitmap frame) {
                lastFrame = frame;
                BasicMotionPicture motionPicture1 = new BasicMotionPicture();
                motionPicture1.addFrame(frame);
                motionPictureView.setMotionPicture(motionPicture1);
            }
        });

        shutterControllerView.setShutterListener(new ShutterControllerView.ShutterListener() {
            @Override
            public void onStart() {
                motionPicture = new BasicMotionPicture();
            }

            @Override
            public void onCaptureFrame() {
                if (lastFrame != null) {
                    motionPicture.addFrame(lastFrame);
                }
            }

            @Override
            public void onFinish() {
                MegaCam.setLastCapturedPicture(motionPicture);
                startActivity(new Intent(CaptureActivity.this, ApplyFiltersActivity.class));
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraFeed.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraFeed.stopCamera();
    }
}
