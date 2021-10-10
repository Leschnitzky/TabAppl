package com.example.taboolaexam;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.taboolaexam.model.Arcticle;
import com.example.taboolaexam.repo.URLConnectionRepository;
import com.example.taboolaexam.ui.FirstTaskViewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ViewModelTest {


    private FirstTaskViewModel viewModel;
    URLConnectionRepository mockedRepository;
    ExecutorService service;
    ArrayList<Arcticle> testList;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule =
            new InstantTaskExecutorRule();

    @Before
    public void setup() {
        mockedRepository = mock(URLConnectionRepository.class);
        service = Executors.newSingleThreadExecutor();
        viewModel = new FirstTaskViewModel(mockedRepository);
        testList = new ArrayList<>();
    }


    @Test
    public void viewModel_MockReturnsEmptyList_ShouldReturnEmptyArcticleList() {
        when(mockedRepository.getDataFromURL())
                .thenReturn(testList);


        viewModel.getArcticleData(service);

        try {
            assertEquals(LiveDataTestUtil.getOrAwaitValue(viewModel.getListLiveData()), new ArrayList<>());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void viewModel_MockReturnsFullList_LiveDataShouldEmitFullList(){
        testList.add(new Arcticle("Test", "Test", "Test"));
        testList.add(new Arcticle("Test2", "Test","Test"));
        when(mockedRepository.getDataFromURL())
                .thenReturn(testList);


        viewModel.getArcticleData(service);

        try {
            assertEquals(LiveDataTestUtil.getOrAwaitValue(viewModel.getListLiveData()), testList);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void viewModel_BadInput_LiveDataShouldEmitNull(){
        when(mockedRepository.getDataFromURL())
                .thenReturn(null);

        viewModel.getArcticleData(service);

        try {
            assertEquals(LiveDataTestUtil.getOrAwaitValue(viewModel.getListLiveData()), null);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
