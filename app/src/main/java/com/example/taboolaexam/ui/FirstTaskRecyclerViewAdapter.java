package com.example.taboolaexam.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.taboolaexam.R;
import com.example.taboolaexam.databinding.FirstTaskRecyclerViewItemBinding;
import com.example.taboolaexam.model.Arcticle;
import com.example.taboolaexam.util.Constants;
import com.taboola.android.TaboolaWidget;
import com.taboola.android.listeners.TaboolaEventListener;
import com.taboola.android.utils.SdkDetailsHelper;

import java.util.HashMap;
import java.util.List;

public class FirstTaskRecyclerViewAdapter extends RecyclerView.Adapter<
        RecyclerView.ViewHolder> {


    private static final String TAG = "FirstTaskAdapter";

    private boolean shouldChangeBackgroundColor;
    private final Activity activity;
    private List<Arcticle> recyclerDataSet;
    private TaboolaWidget widget;

    final int VIEW_TYPE_JSON_FROM_WEB = 0;
    final int VIEW_TYPE_TABOOLA = 1;
    final int VIEW_TYPE_TABOOLA_FEED = 2;

    public void setRecyclerDataSet(List<Arcticle> recyclerDataSet) {
        this.recyclerDataSet = recyclerDataSet;
    }


    public void setShouldChangeBackgroundColor(boolean shouldChangeBackgroundColor) {
        this.shouldChangeBackgroundColor = shouldChangeBackgroundColor;
    }

    public FirstTaskRecyclerViewAdapter(
            TaboolaWidget widget,
            Activity activity,
            List<Arcticle> dataSet) {
        this.activity = activity;
        this.recyclerDataSet = dataSet;
        this.widget = widget;
    }


    public static class SecondTaskTaboolaViewHolder extends RecyclerView.ViewHolder {
        private boolean isFeed;
        private Context context;
        private TaboolaWidget widget;

        public SecondTaskTaboolaViewHolder(@NonNull View itemView, Context context, boolean isFeed) {
            super(itemView);
            this.context = context;
            this.widget = (TaboolaWidget) itemView;
            this.isFeed = isFeed;
        }

        public void bind(){

            widget.setPublisher(Constants.PUBLISHER_ID)
                    .setPageUrl(Constants.URL_STRING)
                    .setPageType(Constants.TYPE_WIDGET)
                    .setTargetType(Constants.TARGET_WIDGET);


            if(isFeed){
                widget.setMode(Constants.MODE_ID_FEED)
                        .setPlacement(Constants.PLACEMENT_ID_FEED);
                widget.setInterceptScroll(true);
                widget.getLayoutParams().height = SdkDetailsHelper.getDisplayHeight(widget.getContext()) * 2;
            } else {
                widget.setMode(Constants.MODE_ID_WIDGET)
                        .setPlacement(Constants.PLACEMENT_ID_WIDGET);
                widget.setAutoResizeHeight(true);

            }
            widget.fetchContent();
        }
    }

    public static class FirstTaskViewHolder extends RecyclerView.ViewHolder {
        private FirstTaskRecyclerViewItemBinding binding;
        private boolean shouldChangeBackground;

        public FirstTaskViewHolder(FirstTaskRecyclerViewItemBinding binding, boolean shouldChangeColor) {
            super(binding.getRoot());
            this.binding = binding;
            this.shouldChangeBackground = shouldChangeColor;

        }

        public void bind(Arcticle arcticle, Context context) {
            if(shouldChangeBackground){
                binding.getRoot().setBackgroundColor(context.getResources().getColor(R.color.design_default_color_secondary));
            }

            binding.recViewTitle.setText(
                    arcticle.getTitle()
            );

            binding.recViewDescription.setHint(
                    arcticle.getDescription()
            );

            Glide.with(context)
                    .load(arcticle.getImageURL())
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(binding.recViewImage);

        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case VIEW_TYPE_JSON_FROM_WEB:
                return new FirstTaskViewHolder
                        (FirstTaskRecyclerViewItemBinding.inflate(
                                activity.getLayoutInflater()
                        ), shouldChangeBackgroundColor);

            case VIEW_TYPE_TABOOLA:
                TaboolaWidget tbWidget = (TaboolaWidget) LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.second_task_taboola_widget_item, parent, false);
                return new SecondTaskTaboolaViewHolder(
                        tbWidget,
                        activity,
                        false
                );
            case VIEW_TYPE_TABOOLA_FEED:
                TaboolaWidget tbFeedWidget = (TaboolaWidget) LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.second_task_taboola_widget_item, parent, false);
                return new SecondTaskTaboolaViewHolder(

                    tbFeedWidget,
                        activity,
                        true
                );
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 2){
            return VIEW_TYPE_TABOOLA;
        } else if(position == 9){
            return VIEW_TYPE_TABOOLA_FEED;
        } else {
            return VIEW_TYPE_JSON_FROM_WEB;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            switch (getItemViewType(position)){
                case VIEW_TYPE_JSON_FROM_WEB:
                    int modifiedIndex = position - Constants.getNumberOfEmptySpaces(position) ;
                    Log.d(TAG, "onBindViewHolder: Modifying position " + modifiedIndex);

                    if(recyclerDataSet.size() > modifiedIndex){
                        Log.d(TAG, "binding " + recyclerDataSet.get(modifiedIndex) .toString() + " to position " + position);

                        FirstTaskViewHolder holder1 = (FirstTaskViewHolder) holder;
                        holder1.bind(recyclerDataSet.get(modifiedIndex),activity);
                    }
                    break;
                case VIEW_TYPE_TABOOLA_FEED:
                case VIEW_TYPE_TABOOLA:
                    SecondTaskTaboolaViewHolder holder2 = (SecondTaskTaboolaViewHolder) holder;
                    holder2.bind();
            }
    }

    @Override
    public int getItemCount() {
        return recyclerDataSet.size() + Constants.EMPTY_INDEX_LIST.size();
    }

}
