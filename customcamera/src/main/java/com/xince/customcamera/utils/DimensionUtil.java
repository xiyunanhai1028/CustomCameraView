package com.xince.customcamera.utils;

import android.content.res.Resources;

/**
 * author : dufeihu
 * date   : 2019/4/1810:47
 * desc   :
 */
public class DimensionUtil {

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

}
