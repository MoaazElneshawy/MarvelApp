package com.moaazfathyelneshawy.marvelapp.Networking;


import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceBuilder {
    private static final String TAG = "ServiceBuilder";

    private static Retrofit retrofit;
    private static String BASE_URL = "https://gateway.marvel.com:443/v1/public/";
    private static String PUBLIC_KEY = "8d369670c97029aee5ff2f17d40d3341";
    private static String PRIVATE_KEY = "ad24b7ba9f24dd0e7b9df1e119efebb4607244eb";

    public ServiceBuilder() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getHeader())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }


    private static OkHttpClient getHeader() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okClient = new OkHttpClient.Builder().addInterceptor(logging)
                .addNetworkInterceptor(
                        chain -> {
                            Request request = chain.request();
                            String ts = Long.toString(System.currentTimeMillis() / 1000);
                            Log.e(TAG, "getHeader: ts > " + ts);
                            Log.e(TAG, "getHeader: hast > " + md5(ts + PRIVATE_KEY + PUBLIC_KEY));
                            HttpUrl url = request.url().newBuilder()
                                    .addQueryParameter("apikey", PUBLIC_KEY)
                                    .addQueryParameter("ts", ts)
                                    .addQueryParameter("hash", md5(ts + PRIVATE_KEY + PUBLIC_KEY))
                                    .build();
                            request = request.newBuilder().url(url).build();

                            return chain.proceed(request);
                        })
                .build();
        return okClient;
    }


    static String md5(String s) {
        String hash = "";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            hash = new String(Strings.hexEncode(digest.digest()));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash;
    }


    ///////////////
    public ServiceInterface.GetCharacters getCharacters() {
        return retrofit.create(ServiceInterface.GetCharacters.class);
    }

    public ServiceInterface.GetCharactersWithSearch getCharactersWithSearch() {
        return retrofit.create(ServiceInterface.GetCharactersWithSearch.class);
    }

    public ServiceInterface.GetComics getComics() {
        return retrofit.create(ServiceInterface.GetComics.class);
    }

    public ServiceInterface.GetEvents getEvents() {
        return retrofit.create(ServiceInterface.GetEvents.class);
    }

    public ServiceInterface.GetSeries getSeries() {
        return retrofit.create(ServiceInterface.GetSeries.class);
    }

    public ServiceInterface.GetStories getStories() {
        return retrofit.create(ServiceInterface.GetStories.class);
    }

}
