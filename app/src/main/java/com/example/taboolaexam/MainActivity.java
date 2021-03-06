package com.example.taboolaexam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.taboolaexam.databinding.ActivityMainBinding;
import com.example.taboolaexam.model.Arcticle;
import com.example.taboolaexam.repo.URLConnectionRepository;
import com.example.taboolaexam.ui.FirstTaskRecyclerViewAdapter;
import com.example.taboolaexam.ui.FirstTaskViewModel;
import com.example.taboolaexam.util.Constants;
import com.taboola.android.PublisherInfo;
import com.taboola.android.Taboola;
import com.taboola.android.TaboolaWidget;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FirstTaskViewModel viewModel;
    private TaboolaWidget widget;
    private static FirstTaskRecyclerViewAdapter adapter;

    public static FirstTaskRecyclerViewAdapter getRecyclerViewAdapter(){
        if(adapter == null){
            return null;
        } else {
            return adapter;
        }
    }
    final Observer<List<Arcticle >> listObserver = new Observer<List<Arcticle >>() {
        @Override
        public void onChanged(List<Arcticle> arcticles) {
            if(arcticles == null){
                //Error problem
                Toast.makeText(getApplicationContext(),
                        "There was a problem displaying the data, please make sure you are connected to the internet",
                        Toast.LENGTH_SHORT).show();
            } else {
                adapter.setRecyclerDataSet(arcticles);
                adapter.notifyDataSetChanged();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        View rootView = binding.getRoot();
        initViewModel();

        setContentView(rootView);
        initTaboolaWidget();
        setupUI();
    }

    private void initViewModel() {
        ViewModelProvider.Factory factory = new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new FirstTaskViewModel(
                        new URLConnectionRepository()
                );
            }

        };

        viewModel = ViewModelProviders.of(this,factory).get(FirstTaskViewModel.class);
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
        viewModel.getArcticleData(Executors.newSingleThreadExecutor());
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