package com.moaazfathyelneshawy.marvelapp.Networking.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.moaazfathyelneshawy.marvelapp.Models.Character;
import com.moaazfathyelneshawy.marvelapp.Models.DataOfCharacters;

import java.util.List;

public class CharacterResponse {

    @Expose
    @SerializedName("data")
    private Data data;
    @Expose
    @SerializedName("etag")
    private String etag;
    @Expose
    @SerializedName("attributionHTML")
    private String attributionhtml;
    @Expose
    @SerializedName("attributionText")
    private String attributiontext;
    @Expose
    @SerializedName("copyright")
    private String copyright;
    @Expose
    @SerializedName("status")
    private String status;
    @Expose
    @SerializedName("code")
    private int code;

    public Data getData() {
        return data;
    }

    public String getEtag() {
        return etag;
    }

    public String getAttributionhtml() {
        return attributionhtml;
    }

    public String getAttributiontext() {
        return attributiontext;
    }

    public String getCopyright() {
        return copyright;
    }

    public String getStatus() {
        return status;
    }

    public int getCode() {
        return code;
    }

    public class Data {
        @Expose
        @SerializedName("count")
        private int count;
        @Expose
        @SerializedName("total")
        private int total;
        @Expose
        @SerializedName("limit")
        private int limit;
        @Expose
        @SerializedName("offset")
        private int offset;
        @Expose
        @SerializedName("results")
        List<Character> characters;

        public int getCount() {
            return count;
        }

        public int getTotal() {
            return total;
        }

        public int getLimit() {
            return limit;
        }

        public int getOffset() {
            return offset;
        }

        public List<Character> getCharacters() {
            return characters;
        }
    }

}
