package com.example.lockdownlife.Views.ui.close;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CloseViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CloseViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is close fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}