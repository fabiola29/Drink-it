package com.example.progettolso.ui.drinks;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DrinksViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public DrinksViewModel() {
        mText = new MutableLiveData<>();
       // mText.setValue("This is drink fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}