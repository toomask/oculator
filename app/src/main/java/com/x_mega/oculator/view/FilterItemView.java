package com.x_mega.oculator.view;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.x_mega.oculator.R;
import com.x_mega.oculator.gif.GifUtil;
import com.x_mega.oculator.motion_picture.BasicMotionPicture;
import com.x_mega.oculator.motion_picture.MotionPicture;
import com.x_mega.oculator.motion_picture.filter.Filter;

import org.apache.commons.io.IOUtils;

import java.io.IOException;

/**
 * Created by toomas on 8.10.2014.
 */
public class FilterItemView extends FrameLayout implements View.OnClickListener {

    public interface OnFilterSelectedListener {
        public void onFilterSelected(Filter filter);
    }

    Filter filter;
    String filterName;
    MotionPictureView motionPictureView;
    TextView nameTextView;
    OnFilterSelectedListener onFilterSelectedListener;

    public FilterItemView(Activity context, Filter filter, String name, OnFilterSelectedListener onFilterSelectedListener) {
        super(context);
        this.filter = filter;
        this.filterName = name;
        this.onFilterSelectedListener = onFilterSelectedListener;
        LayoutInflater.from(context).inflate(R.layout.view_filter_item, this, true);
        motionPictureView = (MotionPictureView) findViewById(R.id.filterMotionPicture);
        nameTextView = (TextView) findViewById(R.id.filterName);
        setOnClickListener(this);
        nameTextView.setText(name);
        initPreview(context, filter);
    }

    private void initPreview(final Activity context, final Filter filter) {
        final MotionPicture cat1 = getCat(context);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(750);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                final MotionPicture filteredCatPic = filter.applyTo(cat1);
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        fadeInMotionPictureView();
                        motionPictureView.setMotionPicture(filteredCatPic);
                    }
                });
            }
        }).start();
    }

    private void fadeInMotionPictureView() {
        AlphaAnimation animation = new AlphaAnimation(0f, 1f);
        animation.setDuration(500);
        motionPictureView.startAnimation(animation);
    }

    @Override
    public void onClick(View v) {
        playSelectionSound();
        onFilterSelectedListener.onFilterSelected(filter);
    }

    static public MotionPicture cat;

    static public MotionPicture getCat(final Context context) {
        if (cat != null) {
            return cat;
        }
        try {
            cat = new BasicMotionPicture(GifUtil.decodeGif(
                    IOUtils.toByteArray(
                            context.getResources().openRawResource(R.raw.cat)
                    )
            ));
            return cat;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void playSelectionSound() {
        MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), R.raw.sound_apply_filter);
  //      mediaPlayer.start();
    }
}
