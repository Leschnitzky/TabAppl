package com.example.taboolaexam.util;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

public class Constants {
    private static final String TAG = "CONSTS";
    public static final String PUBLISHER_ID= "sdk-tester";
    public static final String MODE_ID_WIDGET = "alternating-widget-without-video";
    public static final String PLACEMENT_ID_WIDGET = "Below Article";

    public static final String MODE_ID_FEED = "thumbs-feed-01";
    public static final String PLACEMENT_ID_FEED = "Feed without video";
    public static final String TYPE_WIDGET= "article";
    public static final String TARGET_WIDGET="mix";
    public static String URL_STRING = "https://s3-us-west-2.amazonaws.com/taboola-mobile-sdk/public/home_assignment/data.json";
    public static List<Integer > EMPTY_INDEX_LIST= new ArrayList<Integer>() {
        {
            add(2);
            add(9);
        }
    };



    @RequiresApi(api = Build.VERSION_CODES.N)
    public static int getNumberOfEmptySpaces(int index){
        int number =  (int) EMPTY_INDEX_LIST.stream().filter(
                integer -> index >= integer
        ).count();

        Log.d(TAG, "getNumberOfEmptySpaces: RETURNS FOR " + index + " : " + number + "Spaces");

        return number;

    }
}
