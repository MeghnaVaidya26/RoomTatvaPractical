package com.e.roomdemo;

import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactRepository {
    private static ContactRepository contactRepository;

    public static ContactRepository getInstance() {
        if (contactRepository == null) {
            contactRepository = new ContactRepository();
        }
        return contactRepository;
    }

    ApiInterface apiInterface;

    public ContactRepository() {
        apiInterface = RetrofitService.cteateService(ApiInterface.class);
    }

    public MutableLiveData<Contact> getContact() {

        final MutableLiveData<Contact> mutableLiveData = new MutableLiveData<Contact>();
        apiInterface.getClient().enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response) {
                if (response.isSuccessful()) {
                    mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Contact> call, Throwable t) {
                mutableLiveData.setValue(null);
            }
        });
        return mutableLiveData;
    }
}
