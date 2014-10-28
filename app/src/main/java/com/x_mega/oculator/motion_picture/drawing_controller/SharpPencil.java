package com.x_mega.oculator.motion_picture.drawing_controller;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

/**
 * Created by toomas on 10.10.2014.
 */
public class SharpPencil implements DrawingController {

    public SharpPencil(int color) {
        this.color = color;
        initDrawFrame(width, height);
    }

    public SharpPencil(int color, float pencilWidth) {
        this.color = color;
        this.pencilWidth = pencilWidth;
        initDrawFrame(width, height);
    }

    int color;
    float pencilWidth = 2f;

    int width = 1;
    int height = 1;

    Bitmap drawFrame;
    Point lastPoint;


    private void initDrawFrame(int width, int height) {
        drawFrame = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        drawFrame.setHasAlpha(true);
    }

    @Override
    public void onSizeChanged(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void onTouchEvent(MotionEvent event) {
        Point point = new Point(event);

        point = point.translate(width, height, drawFrame.getWidth(), drawFrame.getHeight());

        if (lastPoint == null) {
            lastPoint = point;
        }

        Canvas canvas = new Canvas(drawFrame);
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStrokeWidth(pencilWidth);
        paint.setAlpha(255);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setAntiAlias(true);
        canvas.drawLine(lastPoint.x, lastPoint.y, point.x, point.y, paint);

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
        if (baseFrame.getWidth() != drawFrame.getWidth()
                || baseFrame.getHeight() != drawFrame.getHeight()) {
            initDrawFrame(baseFrame.getWidth(), baseFrame.getHeight());
        }
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

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        Point(MotionEvent event) {
            this.x =Math.round(event.getX());
            this.y = Math.round(event.getY());
        }

        int x;
        int y;

        Point translate(int sourceWidth, int sourceHeigth, int targetWidth, int targetHeigth) {
            return new Point(
                    this.x * targetWidth / sourceWidth,
                    this.y * targetHeigth / sourceHeigth
            );
        }
    }
}
