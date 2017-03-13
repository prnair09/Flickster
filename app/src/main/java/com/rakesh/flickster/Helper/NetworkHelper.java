package com.rakesh.flickster.Helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by rparuthi on 3/13/2017.
 */

public class NetworkHelper {
    public static boolean isNetworkAvailable(Context ctx){
        boolean isConected = false;

        ConnectivityManager connManager;
        connManager = (ConnectivityManager)ctx.getSystemService(ctx.CONNECTIVITY_SERVICE);

        NetworkInfo nwInfo = connManager.getActiveNetworkInfo();
        if(nwInfo != null){
            isConected = true;
        }
        return isConected;

    }
}
