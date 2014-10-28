package com.x_mega.oculator.motion_picture.drawing_controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.x_mega.oculator.R;

/**
 * Created by toomas on 10.10.2014.
 */
public class RainbowBrush implements DrawingController {

    public RainbowBrush(Context context) {
        initDrawFrame();
        rainBow = BitmapFactory.decodeStream(context.getResources().openRawResource(R.raw.rainbow));
    }

    int color = Color.parseColor("#ffffff");

    int width;
    int height;

    Bitmap drawFrame;
    Point lastPoint;

    Bitmap rainBow;


    private void initDrawFrame() {
        if (! (width > 0)) {
            width = 1;
        }
        if (! (height > 0)) {
            height = 1;
        }
        drawFrame = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        drawFrame.setHasAlpha(true);
    }

    @Override
    public void onSizeChanged(int width, int height) {
        this.width = width;
        this.height = height;
        initDrawFrame();
    }

    @Override
    public void onTouchEvent(MotionEvent event) {
        updateColor();
        Point point = new Point(event);

        if (lastPoint == null) {
            lastPoint = point;
        }

        Canvas canvas = new Canvas(drawFrame);
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStrokeCap(Paint.Cap.ROUND);

        paint.setAlpha(30);

        float strokeWidth = 180f;
        while (strokeWidth > 10f) {
            paint.setStrokeWidth(strokeWidth);
            canvas.drawLine(lastPoint.x, lastPoint.y, point.x, point.y, paint);
            strokeWidth -= 7f;
        }

        lastPoint = point;

        if (event.getAction() == MotionEvent.ACTION_CANCEL
                || event.getAction() == MotionEvent.ACTION_UP) {
            lastPoint = null;
        }
    }

    @Override
    public Bitmap drawTo(Bitmap baseFrame) {
        Bitmap bitmap = baseFrame.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(drawFrame,
                new Rect(0, 0, drawFrame.getWidth(), drawFrame.getHeight()),
                new Rect(0, 0, baseFrame.getWidth(), baseFrame.getHeight()),
                null);
        reduceDrawFrameAlpha();
        return bitmap;
    }

    private void reduceDrawFrameAlpha() {
        Bitmap bitmap = Bitmap.createBitmap(drawFrame.getWidth(), drawFrame.getHeight(), Bitmap.Config.ARGB_8888);
        bitmap.setHasAlpha(true);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAlpha(230);

        canvas.drawBitmap(drawFrame, 0, 0, paint);
        drawFrame = bitmap;
    }

    class Point {
        Point(MotionEvent event) {
            this.x =Math.round(event.getX());
            this.y = Math.round(event.getY());
        }

        int x;
        int y;
    }

    int selectedPixel = 0;

    private void updateColor() {
        color = rainBow.getPixel(selectedPixel, 0);
        selectedPixel += 20;
        if (selectedPixel >= rainBow.getWidth()) {
            selectedPixel = 0;
        }
    }

}
