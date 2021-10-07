package com.example.taboolaexam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.taboolaexam.databinding.ActivityMainBinding;
import com.example.taboolaexam.model.Arcticle;
import com.example.taboolaexam.ui.FirstTaskRecyclerViewAdapter;
import com.example.taboolaexam.ui.FirstTaskViewModel;
import com.example.taboolaexam.util.Constants;
import com.taboola.android.PublisherInfo;
import com.taboola.android.Taboola;
import com.taboola.android.TaboolaWidget;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FirstTaskViewModel viewModel;
    private TaboolaWidget widget;
    private FirstTaskRecyclerViewAdapter adapter;

    final Observer<List<Arcticle >> listObserver = new Observer<List<Arcticle >>() {
        @Override
        public void onChanged(List<Arcticle> arcticles) {
            adapter.setRecyclerDataSet(arcticles);
            adapter.notifyDataSetChanged();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(FirstTaskViewModel.class);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        View rootView = binding.getRoot();
        setContentView(rootView);
        initTaboolaWidget();
        setupUI();
    }

    private void initTaboolaWidget() {
        PublisherInfo publisherInfo = new PublisherInfo(Constants.PUBLISHER_ID);
        Taboola.init(publisherInfo);

        widget = new TaboolaWidget(this);

        widget.setPublisher(Constants.PUBLISHER_ID)
                .setMode(Constants.MODE_ID_WIDGET)
                .setPlacement(Constants.PLACEMENT_ID_WIDGET)
                .setPageUrl(Constants.URL_STRING)
                .setPageType(Constants.TYPE_WIDGET)
                .setTargetType(Constants.TARGET_WIDGET);
        widget.fetchContent();
    }

    private void setupUI() {
        setupRecyclerView();
        setupDataObservers();
        getInitialData();
    }

    private void setupDataObservers() {
        viewModel.getListLiveData().observeForever(listObserver);
    }

    private void getInitialData() {
        viewModel.getArcticleData();
    }


    private void setupRecyclerView() {
        adapter = new FirstTaskRecyclerViewAdapter(
                widget,
                this,
                new ArrayList<>()
        );
        binding.mainActivityRecyclerView.setAdapter(
                adapter
        );

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.mainActivityRecyclerView.setLayoutManager(layoutManager);
    }


}