package com.e.roomdemo;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class ContactViewModel extends ViewModel {

    MutableLiveData<Contact> mutableLiveData;
    ContactRepository contactRepository;

    public void init() {

        if (mutableLiveData != null) {
            return;
        }
        contactRepository = ContactRepository.getInstance();
        mutableLiveData = contactRepository.getContact();
    }

    public LiveData<Contact> getContactRepo() {
        return mutableLiveData;
    }

}
