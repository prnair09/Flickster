package com.rakesh.flickster.Helper;

import android.content.res.Resources;

/**
 * Created by rparuthi on 3/13/2017.
 */

public class SizeHelper {
    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
}
