package com.example.mymovieapp_v1.domain.response;

import com.example.mymovieapp_v1.domain.Configuration;

import java.io.Serializable;
import java.util.ArrayList;

public class ConfigurationResponse implements Serializable {
    private Configuration images;
    private ArrayList<String> change_keys;

    public ConfigurationResponse(Configuration images, ArrayList<String> change_keys) {
        this.images = images;
        this.change_keys = change_keys;
    }

    public Configuration getImages() {
        return images;
    }

    public void setImages(Configuration images) {
        this.images = images;
    }

    public ArrayList<String> getChange_keys() {
        return change_keys;
    }

    public void setChange_keys(ArrayList<String> change_keys) {
        this.change_keys = change_keys;
    }
}
