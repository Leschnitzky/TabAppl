package com.example.taboolaexam.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.taboolaexam.R;
import com.example.taboolaexam.databinding.FirstTaskRecyclerViewItemBinding;
import com.example.taboolaexam.model.Arcticle;
import com.example.taboolaexam.util.Constants;
import com.taboola.android.TaboolaWidget;

import java.util.List;

public class FirstTaskRecyclerViewAdapter extends RecyclerView.Adapter<
        FirstTaskRecyclerViewAdapter.FirstTaskViewHolder> {


    private static final String TAG = "FirstTaskAdapter";

    private final Activity activity;
    private List<Arcticle> recyclerDataSet;
    private TaboolaWidget widget;

    public void setRecyclerDataSet(List<Arcticle> recyclerDataSet) {
        this.recyclerDataSet = recyclerDataSet;
    }

    public FirstTaskRecyclerViewAdapter(
            TaboolaWidget widget,
            Activity activity,
            List<Arcticle> dataSet) {
        this.activity = activity;
        this.recyclerDataSet = dataSet;
        this.widget = widget;
    }

    public static class FirstTaskViewHolder extends RecyclerView.ViewHolder {
        private boolean isViewCollapsed;
        private FirstTaskRecyclerViewItemBinding binding;

        public FirstTaskViewHolder(FirstTaskRecyclerViewItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.isViewCollapsed = false;
        }



        public void bind(Arcticle arcticle, Context context) {
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
    public FirstTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FirstTaskViewHolder
                (FirstTaskRecyclerViewItemBinding.inflate(
                        activity.getLayoutInflater()
                ));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull FirstTaskViewHolder holder, int position) {
        if (Constants.EMPTY_INDEX_LIST.contains(position )) {
            if(position == 2){
                holder.bind(new Arcticle(
                        widget.getUrl(),
                        widget.getUrl(),
                        "test"
                        ),activity);
            }


        } else {
            int modifiedIndex = position - Constants.getNumberOfEmptySpaces(position) ;
            Log.d(TAG, "onBindViewHolder: Modifying position " + modifiedIndex);
            if(recyclerDataSet.size() > modifiedIndex){
                Log.d(TAG, "binding " + recyclerDataSet.get(modifiedIndex) .toString() + " to position " + position);

                holder.bind(recyclerDataSet.get(modifiedIndex),activity);
            }
        }
    }

    @Override
    public int getItemCount() {
        return recyclerDataSet.size() + Constants.EMPTY_INDEX_LIST.size();
    }

}
