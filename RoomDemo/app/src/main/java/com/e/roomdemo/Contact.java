package com.e.roomdemo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Contact {

    @SerializedName("data")
    List<COntactDetail> cOntactDetails;

    public List<COntactDetail> getcOntactDetails() {
        return cOntactDetails;
    }

    class COntactDetail {


    String name, email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
}
