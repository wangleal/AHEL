package wang.leal.ahel.utils;

import android.view.View;

public class ViewUtil {

    public static boolean isTouchPointInView(float x, float y, View view){
        if (view == null) {
            return false;
        }
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        return y >= top && y <= bottom && x >= left
                && x <= right;
    }

    public static boolean isTouchYInView(float y, View view){
        if (view == null) {
            return false;
        }
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int top = location[1];
        int bottom = top + view.getMeasuredHeight();
        return y >= top && y <= bottom;
    }

    public static boolean isTouchXInView(float x, View view){
        if (view == null) {
            return false;
        }
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int right = left + view.getMeasuredWidth();
        return x >= left && x <= right;
    }

}
