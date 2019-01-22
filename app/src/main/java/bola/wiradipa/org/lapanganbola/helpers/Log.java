package bola.wiradipa.org.lapanganbola.helpers;

import bola.wiradipa.org.lapanganbola.BuildConfig;

public class Log {
    public static final String TAG = "LAPANGBOLA";

    public static void d(String message){
        if(BuildConfig.DEBUG)
            android.util.Log.d(TAG, message);
    }

    public static void d(String tag, String message){
        if(BuildConfig.DEBUG)
            android.util.Log.d(tag, message);
    }
}
