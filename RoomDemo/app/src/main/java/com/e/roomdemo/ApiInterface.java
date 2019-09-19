package com.e.roomdemo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("upload/contactjson.json")
    Call<Contact> getClient();


}
