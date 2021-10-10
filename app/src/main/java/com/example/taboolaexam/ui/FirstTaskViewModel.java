package com.example.taboolaexam.ui;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.taboolaexam.model.Arcticle;
import com.example.taboolaexam.repo.URLConnectionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FirstTaskViewModel extends ViewModel {
    URLConnectionRepository repository = new URLConnectionRepository();


    private MutableLiveData<List<Arcticle> > arcticleList;

    public FirstTaskViewModel(URLConnectionRepository urlRepository){
        repository = urlRepository;
    }

    public LiveData<List<Arcticle>> getListLiveData() {
        if (arcticleList == null) {
            arcticleList = new MutableLiveData<List<Arcticle>>();
        }
        return arcticleList;
    }


    public void getArcticleData() {
        ExecutorService service =  Executors.newSingleThreadExecutor();
        service.submit(new Runnable() {
            @Override
            public void run() {
                // on background thread, obtain a fresh list of users
                List<Arcticle> freshArcticleList = repository.getDataFromURL();

                // now that you have the fresh user data in freshUserList,
                // make it available to outside observers of the "users"
                // MutableLiveData object
                arcticleList.postValue(freshArcticleList);
            }
        });

    }
}
