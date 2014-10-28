package com.x_mega.oculator.motion_picture.filter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.x_mega.oculator.motion_picture.BasicMotionPicture;
import com.x_mega.oculator.motion_picture.MotionPicture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by toomas on 23.10.2014.
 */
public class TriangularPatternGenerator implements Filter{

    @Override
    public MotionPicture applyTo(MotionPicture oldPicture) {
        MotionPicture motionPicture = BasicMotionPicture.copy(oldPicture);
        Bitmap frame = motionPicture.getFrame(0);
        Mesh mesh = generateMesh(frame.getWidth(), frame.getHeight());
        List<Line> lines = mesh.lines;
        for (int i = 0; i < motionPicture.getFrameCount(); i++) {
            Canvas canvas = new Canvas(motionPicture.getFrame(i));
            for (Line line : lines) {
                line.drawTo(canvas);
            }
            mesh.updatePoints(frame.getWidth(), frame.getHeight());
        }
        return motionPicture;
    }

    Random random = new Random();

    class Point {

        Point(int x, int y) {
            this.x = x;
            this.y = y;

            speedX = random.nextInt(7) - 3;
            speedY = random.nextInt(7) - 3;
        }

        int x;
        int y;

        int speedX;
        int speedY;

        int distanceFrom(Point point) {
            int dX = this.x - point.x;
            int dY = this.y - point.y;
            return (int) Math.round(Math.sqrt(dX*dX + dY*dY));
        }

        void move(int width, int height) {
            x += speedX;
            y += speedY;
            if (speedX > 0 && x > width) {
                speedX *= -1;
            } else if (speedX < 0 && x < 0) {
                speedX *= -1;
            }
            if (speedY > 0 && y > height) {
                speedY *= -1;
            } else if (speedY < 0 && y < 0) {
                speedY *= -1;
            }
        }

    }

    Point randomPoint(int width, int height) {
        return new Point(
                random.nextInt((width)),
                random.nextInt((height))
        );
    }

    class Line {
        Point point1;
        Point point2;

        Line(Point point1, Point point2) {
            this.point1 = point1;
            this.point2 = point2;
        }

        void drawTo(Canvas canvas) {
            Paint paint = new Paint();
            paint.setStrokeWidth(1.0f);
            paint.setAntiAlias(true);
            paint.setColor(Color.parseColor("#8800ffff"));
            canvas.drawLine(point1.x, point1.y, point2.x, point2.y, paint);
        }
    }

    List<Point> generateInnerPoints(int count, int width, int height) {
        int minDistance = (width + height) / 15;
        ArrayList<Point> points = new ArrayList<Point>();
        points.add(new Point(random.nextInt(width), random.nextInt(height)));
        while (points.size() < count) {
            Point newPoint = new Point(random.nextInt(width), random.nextInt(height));
            boolean isOk = true;
            for (Point point : points) {
                if (newPoint.distanceFrom(point) < minDistance) {
                    isOk = false;
                    break;
                }
            }
            if (isOk) {
                points.add(newPoint);
            }
        }
        return points;
    }

    class Mesh {

        Mesh(List<Point> points, List<Line> lines) {
            this.points = points;
            this.lines = lines;
        }

        List<Point> points;
        List<Line> lines;

        void updatePoints(int width, int height) {
            for (Point point : points) {
                point.move(width, height);
            }
        }
    }

    Mesh generateMesh(int width, int height) {
        List<Point> innerPoints = generateInnerPoints((width*height) / 2000, width, height);
        List<Point> allPoints = innerPoints;

        ArrayList<Line> lines = new ArrayList<Line>();
        for (Point innerPoint : innerPoints) {
            ArrayList<Point> currentPoints = new ArrayList<Point>();
            for (Point comparisonPoint : allPoints) {
                if (comparisonPoint == innerPoint) {
                    // do nothing
                } else if (currentPoints.size() < 6) {
                    currentPoints.add(comparisonPoint);
                } else if (innerPoint.distanceFrom(comparisonPoint)
                        < currentPoints.get(5).distanceFrom(innerPoint) ) {
                    for (int i = 0 ; i < 6 ; i++) {
                        if (innerPoint.distanceFrom(currentPoints.get(i)) > innerPoint.distanceFrom(comparisonPoint)) {
                            currentPoints.add(i, comparisonPoint);
                            currentPoints.remove(6);
                            break;
                        }
                    }
                }
            }
            for (Point point : currentPoints) {
                lines.add(new Line(innerPoint, point));
            }
        }
        return new Mesh(innerPoints, lines);
    }

}
