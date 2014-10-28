package com.x_mega.oculator.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.x_mega.oculator.R;

/**
 * Created by toomas on 8.10.2014.
 */
public class ShutterControllerView extends View {

    public interface ShutterListener {
        public void onStart();
        public void onCaptureFrame();
        public void onFinish();
    }

    ShutterListener shutterListener;

    public ShutterControllerView(Context context) {
        super(context);
    }

    public ShutterControllerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ShutterControllerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setShutterListener(ShutterListener shutterListener) {
        this.shutterListener = shutterListener;
    }

    class Point {
        Point(MotionEvent event) {
            this.x =Math.round(event.getX());
            this.y = Math.round(event.getY());
        }

        int distanceFrom(Point point) {
            int xDelta = point.x - this.x;
            int yDelta = point.y - this.y;
            return (int) Math.sqrt(xDelta*xDelta + yDelta*yDelta);
        }

        int x;
        int y;
    }

    public static int SHUTTER_DISTANCE = 105;

    Point lastShutterPoint;
    Point lastPoint;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            if (shutterListener != null) {
                shutterListener.onStart();
            }
        }

        lastPoint = new Point(event);
        if (lastShutterPoint == null ||
                lastPoint.distanceFrom(lastShutterPoint) >= SHUTTER_DISTANCE) {
            playShutterSound();
            if (shutterListener != null) {
                shutterListener.onCaptureFrame();
            }
            lastShutterPoint = lastPoint;
        }

        if (action == MotionEvent.ACTION_UP
                || action == MotionEvent.ACTION_CANCEL) {
            if (shutterListener != null) {
                shutterListener.onFinish();
            }
            lastPoint = null;
        }

        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (lastPoint != null && lastShutterPoint != null) {
            Paint paint = new Paint();
            paint.setColor(Color.parseColor("#22ffffff"));
            paint.setStrokeWidth(30);
            paint.setStyle(Paint.Style.STROKE);

            int distanceFromLastShutterPoint = lastPoint.distanceFrom(lastShutterPoint);
            if (distanceFromLastShutterPoint <= 0) {
                distanceFromLastShutterPoint = 1;
            }
            int realDistanceIndicator = SHUTTER_DISTANCE + 75;
            int indicatorRadius = Math.round( realDistanceIndicator * 1.2f);
            int indicatorCircleRadius = realDistanceIndicator + indicatorRadius - indicatorRadius * distanceFromLastShutterPoint / SHUTTER_DISTANCE ;
            canvas.drawCircle(lastShutterPoint.x, lastShutterPoint.y, realDistanceIndicator, paint);
            canvas.drawCircle(lastShutterPoint.x, lastShutterPoint.y, indicatorCircleRadius, paint);

            paint.setColor(Color.parseColor("#22ffffff"));
            paint.setStrokeWidth(15);
            canvas.drawCircle(lastShutterPoint.x, lastShutterPoint.y, realDistanceIndicator, paint);
            canvas.drawCircle(lastShutterPoint.x, lastShutterPoint.y, indicatorCircleRadius, paint);


            paint.setColor(Color.parseColor("#99ffffff"));
            paint.setStrokeWidth(2);
            canvas.drawCircle(lastShutterPoint.x, lastShutterPoint.y, realDistanceIndicator, paint);
            canvas.drawCircle(lastShutterPoint.x, lastShutterPoint.y, indicatorCircleRadius, paint);

            if (lastShutterPoint == lastPoint) {
                canvas.drawColor(Color.parseColor("#ffffff"));
            }
        }
    }

    private void playShutterSound() {
        MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), R.raw.sound_camera_shutter);
        mediaPlayer.start();
    }
}
