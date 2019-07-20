package com.moaazfathyelneshawy.marvelapp.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.moaazfathyelneshawy.marvelapp.Models.Character;
import com.moaazfathyelneshawy.marvelapp.Models.Item;
import com.moaazfathyelneshawy.marvelapp.Networking.Response.DetailsResponse;
import com.moaazfathyelneshawy.marvelapp.Networking.ServiceBuilder;
import com.moaazfathyelneshawy.marvelapp.Networking.ServiceInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsViewModel extends ViewModel {

    private static final String TAG = "DetailsViewModel";

    //// comics
    private MutableLiveData<List<Item>> comics = new MutableLiveData<>();

    public LiveData<List<Item>> getComics() {
        return comics;
    }

    private void setComics(List<Item> comicsList) {
        comics.setValue(comicsList);
    }

    public void loadComics(int id) {
        ServiceBuilder builder = new ServiceBuilder();
        ServiceInterface.GetComics comics = builder.getComics();
        Call<DetailsResponse> call = comics.getComics(id, 0, 10);
        call.enqueue(new Callback<DetailsResponse>() {
            @Override
            public void onResponse(Call<DetailsResponse> call, Response<DetailsResponse> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    try {
                        Log.e(TAG, "onResponse: comics " + response.body().getData().getItems().size());
                        setComics(response.body().getData().getItems());
                    } catch (Exception e) {
                        Log.e(TAG, "onResponse: comics ex >> " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<DetailsResponse> call, Throwable t) {
                Log.e(TAG, "onFailure:comics >> " + t.getMessage());
            }
        });
    }

    ////////// stories
    private MutableLiveData<List<Item>> stories = new MutableLiveData<>();

    private void setStories(List<Item> list) {
        stories.setValue(list);
    }

    public LiveData<List<Item>> getStories() {
        return stories;
    }

    public void loadStories(int id) {
        ServiceBuilder builder = new ServiceBuilder();
        ServiceInterface.GetStories getStories = builder.getStories();
        Call<DetailsResponse> call = getStories.getStories(id, 0, 20);
        call.enqueue(new Callback<DetailsResponse>() {
            @Override
            public void onResponse(Call<DetailsResponse> call, Response<DetailsResponse> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    try {
                        Log.e(TAG, "onResponse: stories " + response.body().getData().getItems().size());
                        setStories(response.body().getData().getItems());
                    } catch (Exception e) {
                        Log.e(TAG, "onResponse: stories ex >> " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<DetailsResponse> call, Throwable t) {
                Log.e(TAG, "onFailure:stories >> " + t.getMessage());
            }
        });
    }

    //// events
    private MutableLiveData<List<Item>> events = new MutableLiveData<>();

    private void setEvents(List<Item> list) {
        events.setValue(list);
    }

    public LiveData<List<Item>> getEvents() {
        return events;
    }

    public void loadEvents(int id) {
        ServiceBuilder builder = new ServiceBuilder();
        ServiceInterface.GetEvents getEvents = builder.getEvents();
        Call<DetailsResponse> call = getEvents.getEvents(id, 0, 10);
        call.enqueue(new Callback<DetailsResponse>() {
            @Override
            public void onResponse(Call<DetailsResponse> call, Response<DetailsResponse> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    try {
                        Log.e(TAG, "onResponse: events " + response.body().getData().getItems().size());
                        setEvents(response.body().getData().getItems());
                    } catch (Exception e) {
                        Log.e(TAG, "onResponse: events ex >> " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<DetailsResponse> call, Throwable t) {
                Log.e(TAG, "onFailure:events >> " + t.getMessage());
            }
        });
    }

    //// series
    private MutableLiveData<List<Item>> series = new MutableLiveData<>();

    private void setSeries(List<Item> list) {
        series.setValue(list);
    }

    public LiveData<List<Item>> getSeries() {
        return series;
    }

    public void loadSeries(int id) {
        ServiceBuilder builder = new ServiceBuilder();
        ServiceInterface.GetSeries getSeries = builder.getSeries();
        Call<DetailsResponse> call = getSeries.getSeries(id, 0, 10);
        call.enqueue(new Callback<DetailsResponse>() {
            @Override
            public void onResponse(Call<DetailsResponse> call, Response<DetailsResponse> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    try {
                        Log.e(TAG, "onResponse: series " + response.body().getData().getItems().size());
                        setSeries(response.body().getData().getItems());
                    } catch (Exception e) {
                        Log.e(TAG, "onResponse: series ex >> " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<DetailsResponse> call, Throwable t) {
                Log.e(TAG, "onFailure:series >> " + t.getMessage());
            }
        });
    }

}
