package com.example.taboolaexam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.example.taboolaexam.databinding.ActivityMainBinding;
import com.example.taboolaexam.ui.FirstTaskRecyclerViewAdapter;
import com.example.taboolaexam.ui.FirstTaskViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FirstTaskViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(FirstTaskViewModel.class);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        View rootView = binding.getRoot();
        setContentView(rootView);

        setupUI();
    }

    private void setupUI() {
        setupRecyclerView();
        setupDataObservers();
        getInitialData();
    }

    private void setupDataObservers() {
    }

    private void getInitialData() {
    }


    private void setupRecyclerView() {

        binding.mainActivityRecyclerView.setAdapter(
                new FirstTaskRecyclerViewAdapter(
                        this,
                        new ArrayList<>()
                )
        );
    }


}