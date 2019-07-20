package com.moaazfathyelneshawy.marvelapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Thumbnail implements Serializable {
    @Expose
    @SerializedName("extension")
    private String extension;
    @Expose
    @SerializedName("path")
    private String path;

    public String getExtension() {
        return extension;
    }

    public String getPath() {
        return path;
    }
}