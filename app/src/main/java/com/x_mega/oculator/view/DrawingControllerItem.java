package com.x_mega.oculator.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.x_mega.oculator.R;
import com.x_mega.oculator.motion_picture.drawing_controller.DrawingController;

/**
 * Created by toomas on 10.10.2014.
 */
public class DrawingControllerItem extends FrameLayout implements View.OnClickListener {

    public interface DrawingControllerSelectionListener {
        public void onDrawingControllerSelected(DrawingController drawingController);
    }

    public DrawingControllerItem(Context context, DrawingControllerSelectionListener selectionListener,
                                 DrawingController drawingController, String name) {
        super(context);
        this.drawingController = drawingController;
        this.selectionListener = selectionListener;
        LayoutInflater.from(context).inflate(R.layout.view_drawing_controller_item, this, true);
        TextView nameTextView = (TextView) findViewById(R.id.name);
        nameTextView.setText(name);
        setOnClickListener(this);
    }

    DrawingController drawingController;
    DrawingControllerSelectionListener selectionListener;

    @Override
    public void onClick(View v) {
        selectionListener.onDrawingControllerSelected(drawingController);
    }

    public void setIsSelected(boolean isSelected) {
        if (isSelected) {
            setBackgroundResource(R.drawable.background_selection_item_selected);
        } else {
            setBackgroundResource(R.drawable.background_selection_item);
        }
    }

    public DrawingController getDrawingController() {
        return drawingController;
    }
}
