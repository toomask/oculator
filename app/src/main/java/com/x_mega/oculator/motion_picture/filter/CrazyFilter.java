package com.x_mega.oculator.motion_picture.filter;

import android.view.View;
import android.view.ViewGroup;

import com.x_mega.oculator.R;
import com.x_mega.oculator.motion_picture.MotionPicture;
import com.x_mega.oculator.view.FilterItemView;
import com.x_mega.oculator.view.FiltersController;

import java.lang.reflect.Field;
import java.util.Random;

/**
 * Created by toomas on 18.10.2014.
 */
public class CrazyFilter implements Filter {

    FiltersController controller;

    public CrazyFilter(FiltersController controller) {
        this.controller = controller;
    }

    @Override
    public MotionPicture applyTo(MotionPicture motionPicture) {
        MotionPicture picture = motionPicture;
        for (int i = 0; i < 10; i++) {
            View filterItemView;
            do {
                ViewGroup filterItemsLayout = (ViewGroup) controller.findViewById(R.id.filterSelectionLayout);
                filterItemView = filterItemsLayout.getChildAt(randInt(0, filterItemsLayout.getChildCount() - 1));
            } while (!(filterItemView instanceof FilterItemView));
            Field field = null; //NoSuchFieldException
            try {
                field = filterItemView.getClass().getDeclaredField("filter");
                field.setAccessible(true);
                Filter filter = (Filter) field.get(filterItemView);
                picture = filter.applyTo(picture);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return picture;
    }

    private int randInt(int min, int max) {

        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
}
