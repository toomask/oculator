package com.x_mega.oculator.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.x_mega.oculator.R;
import com.x_mega.oculator.motion_picture.drawing_controller.DrawingController;
import com.x_mega.oculator.motion_picture.drawing_controller.PatternPrinter;
import com.x_mega.oculator.motion_picture.drawing_controller.RainbowBrush;
import com.x_mega.oculator.motion_picture.drawing_controller.SharpPencil;
import com.x_mega.oculator.motion_picture.drawing_controller.SimpleBrush;
import com.x_mega.oculator.motion_picture.drawing_controller.SoftBrush;

/**
 * Created by toomas on 14.10.2014.
 */
public class BrushesController extends FrameLayout implements DrawingControllerItem.DrawingControllerSelectionListener{

    public interface BrushSelectionListener {
        public void onBrushSelected(DrawingController drawingController);
    }

    ViewGroup drawingControllerSelectionLayout;
    BrushSelectionListener brushSelectionListener;

    public BrushesController(Context context) {
        super(context);
        init(context);
    }

    public BrushesController(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BrushesController(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public void setBrushSelectionListener(BrushSelectionListener brushSelectionListener) {
        this.brushSelectionListener = brushSelectionListener;
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_brushes_controller, this, true);
        drawingControllerSelectionLayout = (ViewGroup) findViewById(R.id.drawingControllerSelectionLayout);

        initDrawingControllers(context);
    }

    private void initDrawingControllers(Context c) {
        drawingControllerSelectionLayout.addView(new DrawingControllerItem(c, this,
                new PatternPrinter(c, R.raw.doge_face), "Doge"));
        drawingControllerSelectionLayout.addView(new DrawingControllerItem(c, this,
                new SoftBrush(Color.parseColor("#ffffffff")), "Soft White"));
        drawingControllerSelectionLayout.addView(new DrawingControllerItem(c, this,
                new SoftBrush(Color.parseColor("#FF8995")), "Soft Pink"));
        drawingControllerSelectionLayout.addView(new DrawingControllerItem(c, this,
                new SharpPencil(Color.parseColor("#000000")), "Sharp Black"));
        drawingControllerSelectionLayout.addView(new DrawingControllerItem(c, this,
                new SharpPencil(Color.parseColor("#100000A5"), 30f), "Blue Marker"));
        drawingControllerSelectionLayout.addView(new DrawingControllerItem(c, this,
                new SimpleBrush(Color.parseColor("#FFE30F")), "Lemon"));
        drawingControllerSelectionLayout.addView(new DrawingControllerItem(c, this,
                new SimpleBrush(Color.parseColor("#00FF11")), "Green"));
        drawingControllerSelectionLayout.addView(new DrawingControllerItem(c, this,
                new RainbowBrush(c), "Rainbow"));
    }

    @Override
    public void onDrawingControllerSelected(DrawingController drawingController) {
        for (int i = 0; i < drawingControllerSelectionLayout.getChildCount(); i++) {
            DrawingControllerItem drawingControllerSelectionView =
                    (DrawingControllerItem) drawingControllerSelectionLayout.getChildAt(i);
            if (drawingControllerSelectionView.getDrawingController() == drawingController) {
                drawingControllerSelectionView.setIsSelected(true);
            } else {
                drawingControllerSelectionView.setIsSelected(false);
            }
        }
        if (brushSelectionListener != null) {
            brushSelectionListener.onBrushSelected(drawingController);
        }
    }
}
