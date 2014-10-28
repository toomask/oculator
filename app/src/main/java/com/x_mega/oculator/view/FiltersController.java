package com.x_mega.oculator.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.x_mega.oculator.R;
import com.x_mega.oculator.motion_picture.BasicMotionPicture;
import com.x_mega.oculator.motion_picture.filter.BackToBackFilter;
import com.x_mega.oculator.motion_picture.filter.BlendFilter;
import com.x_mega.oculator.motion_picture.filter.BlurFilter;
import com.x_mega.oculator.motion_picture.filter.ColorFilter;
import com.x_mega.oculator.motion_picture.filter.DimFilter;
import com.x_mega.oculator.motion_picture.filter.Filter;
import com.x_mega.oculator.motion_picture.filter.FutureFilter;
import com.x_mega.oculator.motion_picture.filter.GridFilter;
import com.x_mega.oculator.motion_picture.filter.InvertFilter;
import com.x_mega.oculator.motion_picture.filter.LowResMonoFilter;
import com.x_mega.oculator.motion_picture.filter.MoveDetectionFilter;
import com.x_mega.oculator.motion_picture.filter.PixelateFilter;
import com.x_mega.oculator.motion_picture.filter.RainbowFilter;
import com.x_mega.oculator.motion_picture.filter.ReverseFilter;
import com.x_mega.oculator.motion_picture.filter.Rotate;
import com.x_mega.oculator.motion_picture.filter.SpeedFilter;
import com.x_mega.oculator.motion_picture.filter.ThreadedPixelFilter;
import com.x_mega.oculator.motion_picture.filter.TightDistort;
import com.x_mega.oculator.motion_picture.filter.TriangularPatternGenerator;
import com.x_mega.oculator.motion_picture.filter.UltraFilter;
import com.x_mega.oculator.motion_picture.filter.ZoomBlur;
import com.x_mega.oculator.motion_picture.pixel_shader.BlackAndWhiteShader;
import com.x_mega.oculator.motion_picture.pixel_shader.GrainShader;
import com.x_mega.oculator.motion_picture.pixel_shader.IntensifyShader;
import com.x_mega.oculator.motion_picture.pixel_shader.InterlacingShader;
import com.x_mega.oculator.motion_picture.pixel_shader.InvertShader;
import com.x_mega.oculator.motion_picture.pixel_shader.LiftShader;
import com.x_mega.oculator.motion_picture.pixel_shader.LightenShader;
import com.x_mega.oculator.motion_picture.pixel_shader.LowColorShader;
import com.x_mega.oculator.motion_picture.pixel_shader.MotionShader;
import com.x_mega.oculator.motion_picture.pixel_shader.ShiftShader;
import com.x_mega.oculator.motion_picture.pixel_shader.ShuffleShader;
import com.x_mega.oculator.motion_picture.pixel_shader.ZigZagShader;

/**
 * Created by toomas on 14.10.2014.
 */
public class FiltersController extends FrameLayout implements FilterItemView.OnFilterSelectedListener{

    public interface FilterSelectionListener {
        public void onFilterSelected(Filter filter);
    }

    ViewGroup filterSelectionLayout;

    FilterSelectionListener filterSelectionListener;

    public FiltersController(Context context) {
        super(context);
        init(context);
    }

    public FiltersController(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FiltersController(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_filters_controller, this, true);
        filterSelectionLayout = (ViewGroup) findViewById(R.id.filterSelectionLayout);

        initFilters();
    }

    public void setFilterSelectionListener(FilterSelectionListener filterSelectionListener) {
        this.filterSelectionListener = filterSelectionListener;
    }

    private void initFilters() {
        addFilter(new SpeedFilter(1.5f), "Slow");
        addFilter(new SpeedFilter(0.7f), "Fast");
        addFilter(new ReverseFilter(), "Reverse");
        addFilter(new BackToBackFilter(), "B2B");
        addFilter(new RainbowFilter(getContext()), "Gay");
        addFilter(new BlurFilter(), "Blur");
        addFilter(new ColorFilter("#88ba4cba"), "Purple");
        addFilter(new ColorFilter("#8874e732"), "Green");
        addFilter(new ColorFilter("#88e2a215"), "Orange");
        addFilter(new ThreadedPixelFilter(new LowColorShader(20)), "-Color");
        addFilter(new InvertFilter(getContext()), "Invert");
        addFilter(new ThreadedPixelFilter(new ShuffleShader()), "Shuffle");
        addFilter(new GridFilter(), "Grid");
        addFilter(new PixelateFilter(), "PixeLe.?");
        addFilter(new BlendFilter(FilterItemView.getCat(getContext()), 85), "Cat");
        addFilter(new ThreadedPixelFilter(new ShiftShader()), "Shift");
        addFilter(new ThreadedPixelFilter(new BlackAndWhiteShader()), "B/W");
        addFilter(new LowResMonoFilter(), "Mono");
        addFilter(new MoveDetectionFilter(), "Move");
        addFilter(new ThreadedPixelFilter(new MotionShader()), "Motion");
        addFilter(new FutureFilter(), "Future");
        addFilter(new DimFilter(getContext()), "Dim");
        addFilter(new ThreadedPixelFilter(new ZigZagShader()), "ZigZag");
        addFilter(new ThreadedPixelFilter(new LightenShader(10)), "Lighten");
        addFilter(new ThreadedPixelFilter(new IntensifyShader()), "Intensify");
        addFilter(new UltraFilter(), "Ultra");
        addFilter(new ThreadedPixelFilter(new InterlacingShader()), "Interlace");
        addFilter(new ZoomBlur(), "Zoom");
        addFilter(new ThreadedPixelFilter(new GrainShader()), "Grain");
        addFilter(new ThreadedPixelFilter(new LiftShader()), "Lift");
        addFilter(new TightDistort(), "Distort");
        addFilter(new Rotate(), "Rotate");
        addFilter(new TriangularPatternGenerator(), "Triangles");

        BasicMotionPicture mask = new BasicMotionPicture();
        mask.addFrame(BitmapFactory.decodeStream(getResources().openRawResource(R.raw.dotted_pattern)));
        addFilter(new ThreadedPixelFilter(new InvertShader(), mask), "Dots");
    }

    private void addFilter(Filter filter, String name) {
        addFilterViewToLayout(new FilterItemView((Activity) getContext(), filter, name, this));
    }

    private void addFilterViewToLayout(FilterItemView filterSelectionView) {
        if (filterSelectionLayout.getChildCount() > 0) {
            LayoutInflater.from(getContext()).inflate(R.layout.layout_tool_item_divider, filterSelectionLayout, true);
        }
        filterSelectionLayout.addView(filterSelectionView);
    }

    @Override
    public void onFilterSelected(Filter filter) {
        if (filterSelectionListener != null) {
            filterSelectionListener.onFilterSelected(filter);
        }
    }
}
