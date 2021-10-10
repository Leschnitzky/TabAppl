package com.example.taboolaexam;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import androidx.lifecycle.ViewModel;

import com.example.taboolaexam.repo.URLConnectionRepository;
import com.example.taboolaexam.ui.FirstTaskViewModel;

import org.junit.Before;
import org.junit.Test;

public class ViewModelTest {
    private FirstTaskViewModel viewModel;
    @Before
    public void setup(){
        URLConnectionRepository mockedRepository = mock(URLConnectionRepository.class);
        viewModel = new FirstTaskViewModel(mockedRepository);
    }

        @Test
        public void addition_isCorrect() {
            assertEquals(4, 2 + 2);
        }
}
