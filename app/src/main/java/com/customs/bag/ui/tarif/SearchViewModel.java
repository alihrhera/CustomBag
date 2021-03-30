package com.customs.bag.ui.tarif;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.customs.bag.data.online.ApiCall;
import com.customs.bag.util.DataManger;

public class SearchViewModel extends AndroidViewModel {
    private final MutableLiveData<String> responseLiveData = new MutableLiveData<>();

    public SearchViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String > getResponseLiveData() {
        return responseLiveData;
    }


    public void searchAboutSomething(String query) {
        ApiCall.getInstance().getAllPost(query, responseLiveData::postValue);
    }


}
