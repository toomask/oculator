package com.x_mega.oculator.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.ViewGroup;

import com.x_mega.oculator.MegaCam;
import com.x_mega.oculator.R;
import com.x_mega.oculator.gif.GifUtil;
import com.x_mega.oculator.video.VideoUtil;
import com.x_mega.oculator.view.ShareButton;

import java.util.List;

/**
 * Created by toomas on 17.10.2014.
 */
public class ShareActivity extends Activity{

    ViewGroup videoShareButtonsContainer;
    ViewGroup gifShareButtonsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        videoEncoderThread.start();
        gifEncoderThread.start();
        setContentView(R.layout.activity_share);
        videoShareButtonsContainer = (ViewGroup) findViewById(R.id.videoShareButtonsContainer);
        gifShareButtonsContainer = (ViewGroup) findViewById(R.id.gifShareButtonsContainer);

        PackageManager manager = getPackageManager();

        Intent gifShareIntent = new Intent(Intent.ACTION_SEND);
        gifShareIntent.setType("image/gif");
        List<ResolveInfo> gifInfos = manager.queryIntentActivities (gifShareIntent,
                PackageManager.GET_RESOLVED_FILTER);
        for (ResolveInfo info : gifInfos) {
            gifShareButtonsContainer.addView(new ShareButton(this, info, gifProvider));
        }

        Intent videoShareIntent = new Intent(Intent.ACTION_SEND);
        videoShareIntent.setType("video/mp4");
        List<ResolveInfo> videoInfos = manager.queryIntentActivities (videoShareIntent,
                PackageManager.GET_RESOLVED_FILTER);
        for (ResolveInfo info : videoInfos) {
            videoShareButtonsContainer.addView(new ShareButton(this, info, videoProvider));
        }
    }

    String gifPath;
    String videoPath;

    Thread gifEncoderThread = new Thread(new Runnable() {
        @Override
        public void run() {
            gifPath = GifUtil.encodeGifAndSaveToSdCard(MegaCam.getPictureToShare());
        }
    });

    Thread videoEncoderThread = new Thread(new Runnable() {
        @Override
        public void run() {
            videoPath = VideoUtil.encodeVideoAndSaveToSdCard(MegaCam.getPictureToShare());
        }
    });

    ShareButton.IntentExtrasProvider videoProvider = new ShareButton.IntentExtrasProvider() {
        @Override
        public void addExtrasToIntent(Intent intent) {
            try {
                videoEncoderThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String filePath = videoPath;
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("video/mp4");
            intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + filePath));
        }
    };

    ShareButton.IntentExtrasProvider gifProvider = new ShareButton.IntentExtrasProvider() {
        @Override
        public void addExtrasToIntent(Intent intent) {
            try {
                gifEncoderThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String filePath = gifPath;
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("image/gif");
            intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + filePath));
        }
    };
}
