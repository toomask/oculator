package com.x_mega.oculator.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ViewFlipper;

import com.x_mega.oculator.MegaCam;
import com.x_mega.oculator.R;
import com.x_mega.oculator.motion_picture.BasicMotionPicture;
import com.x_mega.oculator.motion_picture.MotionPicture;
import com.x_mega.oculator.motion_picture.drawing_controller.DrawingController;
import com.x_mega.oculator.motion_picture.filter.Filter;
import com.x_mega.oculator.util.HistoryManager;
import com.x_mega.oculator.view.BrushesController;
import com.x_mega.oculator.view.FiltersController;
import com.x_mega.oculator.view.MotionPictureView;
import com.x_mega.oculator.view.PicturePositionIndicator;

/**
 * Created by toomas on 8.10.2014.
 */
public class ApplyFiltersActivity extends Activity implements
        FiltersController.FilterSelectionListener,
        BrushesController.BrushSelectionListener,
        MotionPictureView.DrawStartListener {

    HistoryManager historyManager;

    MotionPicture picture;
    MotionPictureView motionPictureView;
    FiltersController filtersSelector;
    PicturePositionIndicator picturePositionIndicator;
    BrushesController brushSelector;
    ViewFlipper toolsViewFlipper;
    View filtersButton;
    View brushesButton;
    View undoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        historyManager = new HistoryManager(this);
        setContentView(R.layout.activity_apply_filters);
        picturePositionIndicator = (PicturePositionIndicator) findViewById(R.id.picturePositionIndicator);
        motionPictureView = (MotionPictureView) findViewById(R.id.motionPictureView);
        motionPictureView.setFrameChangedListener(picturePositionIndicator);
        filtersSelector = (FiltersController) findViewById(R.id.filtersSelector);
        filtersSelector.setFilterSelectionListener(this);
        brushSelector = (BrushesController) findViewById(R.id.brushSelector);
        brushSelector.setBrushSelectionListener(this);
        toolsViewFlipper = (ViewFlipper) findViewById(R.id.toolsViewFlipper);
        filtersButton = findViewById(R.id.filtersButton);
        brushesButton = findViewById(R.id.brushesButton);
        undoButton = findViewById(R.id.undoButton);
        resetPicture();
        onFiltersClick(null);
    }

    @Override
    public void onFilterSelected(Filter filter) {
        historyManager.addToHistory(picture);
        picture = filter.applyTo(picture);
        motionPictureView.setMotionPicture(picture);
    }

    @Override
    public void onBrushSelected(DrawingController drawingController) {
        motionPictureView.setDrawStartListener(this);
        motionPictureView.setDrawingController(drawingController);
    }

    private void resetPicture() {
        picture = new BasicMotionPicture(MegaCam.getLastCapturedPicture());
        motionPictureView.setMotionPicture(picture);
    }

    public void saveAndShare(View view) {
        MegaCam.setPictureToShare(picture);
        startActivity(new Intent(this, ShareActivity.class));
    }

    public void onBrushesClick(View view) {
        toolsViewFlipper.setDisplayedChild(1);
        filtersButton.setBackgroundResource(R.drawable.background_filters_button);
        brushesButton.setBackgroundResource(R.drawable.background_filters_button_selected);
//        filtersButton.setTextColor(getResources().getColor(R.color.tool_button_text_default));
//        brushesButton.setTextColor(getResources().getColor(R.color.tool_button_text_selected));
    }

    public void onFiltersClick(View view) {
        toolsViewFlipper.setDisplayedChild(0);
        filtersButton.setBackgroundResource(R.drawable.background_brushes_button_selected);
        brushesButton.setBackgroundResource(R.drawable.background_brushes_button);
//        filtersButton.setTextColor(getResources().getColor(R.color.tool_button_text_selected));
//        brushesButton.setTextColor(getResources().getColor(R.color.tool_button_text_default));
    }

    public void onUndoClicked(View view) {
        undo();
    }

    @Override
    public void onNewDrawEvent() {
        historyManager.addToHistory(picture);
    }

    public void undo() {
        MotionPicture lastPicture = historyManager.removeLastPicture();
        if (lastPicture != null) {
            picture = lastPicture;
            motionPictureView.setMotionPicture(picture);
        } else {
            resetPicture();
        }
    }

    private void onPictureModified() {
        undoButton.setEnabled(true);
    }
}
