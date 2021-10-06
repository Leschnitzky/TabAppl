package com.example.taboolaexam.ui;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taboolaexam.databinding.FirstTaskRecyclerViewItemBinding;
import com.example.taboolaexam.model.Arcticle;

import java.util.List;

public class FirstTaskRecyclerViewAdapter extends RecyclerView.Adapter<
        FirstTaskRecyclerViewAdapter.FirstTaskViewHolder> {

    private final Activity activity;
    private List<Arcticle> recyclerDataSet;

    public void setRecyclerDataSet(List<Arcticle> recyclerDataSet) {
        this.recyclerDataSet = recyclerDataSet;
    }

    public FirstTaskRecyclerViewAdapter(
            Activity activity,
            List<Arcticle> dataSet) {
        this.activity = activity;
    }

    public static class FirstTaskViewHolder extends RecyclerView.ViewHolder {
        private boolean isViewCollapsed;
        private FirstTaskRecyclerViewItemBinding binding;

        public FirstTaskViewHolder(FirstTaskRecyclerViewItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.isViewCollapsed = false;
        }

        public void bind(Arcticle arcticle) {
            binding.recViewTitle.setText(
                    arcticle.getTitle()
            );

            binding.recViewDescription.setHint(
                    arcticle.getDescription()
            );


            binding.recViewDescription.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isViewCollapsed) {
                        binding.recViewDescription.setLayoutParams(
                                new ViewGroup.LayoutParams(
                                        binding.recViewDescription.getWidth(),
                                        40)
                        );
                    } else {
                        binding.recViewDescription.setLayoutParams(
                                new ViewGroup.LayoutParams(
                                        binding.recViewDescription.getWidth(),
                                        ViewGroup.LayoutParams.WRAP_CONTENT)
                        );
                    }

                    isViewCollapsed = !isViewCollapsed;
                }
            });

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

    @Override
    public void onBindViewHolder(@NonNull FirstTaskViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return recyclerDataSet.size();
    }

}
