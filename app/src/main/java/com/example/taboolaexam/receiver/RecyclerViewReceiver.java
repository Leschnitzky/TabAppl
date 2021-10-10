package com.example.taboolaexam.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.taboolaexam.MainActivity;
import com.example.taboolaexam.ui.FirstTaskRecyclerViewAdapter;

public class RecyclerViewReceiver extends BroadcastReceiver {
    private static final String TAG = "RecViewReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        FirstTaskRecyclerViewAdapter adapter = MainActivity.getRecyclerViewAdapter();

        if(adapter == null){
            // Not implemented yet, don't do anything
            Log.d(TAG, "onReceive: No adapter");
        } else {
            Log.d(TAG, "onReceive: Changed");
            adapter.setShouldChangeBackgroundColor(true);
            adapter.notifyDataSetChanged();
        }
    }
}