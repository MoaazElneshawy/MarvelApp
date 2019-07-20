package com.moaazfathyelneshawy.marvelapp.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.moaazfathyelneshawy.marvelapp.Models.Character;
import com.moaazfathyelneshawy.marvelapp.Networking.Response.CharacterResponse;
import com.moaazfathyelneshawy.marvelapp.Networking.ServiceBuilder;
import com.moaazfathyelneshawy.marvelapp.Networking.ServiceInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharactersViewModel extends ViewModel {

    private static final String TAG = "CharactersViewModel";
    private MutableLiveData<List<Character>> charactersLiveData = new MutableLiveData<>();

    public void loadCharacters(int offset, int limit) {
        Log.e(TAG, "loadCharacters: offset" + offset);
        ServiceBuilder serviceBuilder = new ServiceBuilder();
        ServiceInterface.GetCharacters getCharacters = serviceBuilder.getCharacters();
        Call<CharacterResponse> call = getCharacters.getCharacters(offset, limit);
        call.enqueue(new Callback<CharacterResponse>() {
            @Override
            public void onResponse(Call<CharacterResponse> call, Response<CharacterResponse> response) {
                Log.e(TAG, "onResponse: url > "+call.request().url() );

                if (response.isSuccessful() && response.code() == 200) {
                    try {
                        setCharactersWithSearch(response.body().getData().getCharacters());
                    } catch (Exception e) {
                        Log.e(TAG, "onResponse: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<CharacterResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

    public LiveData<List<Character>> getCharacters() {
        return charactersLiveData;
    }

    private void setCharacters(List<Character> characters) {
        charactersLiveData.setValue(characters);
    }

    ///// search
    private MutableLiveData<List<Character>> charactersWithSearch = new MutableLiveData<>();

    public void loadCharactersWithSearch(int offset, int limit, String searchKeyWord) {
        Log.e(TAG, "loadCharactersWithSearch: offset" + offset);
        ServiceBuilder serviceBuilder = new ServiceBuilder();
        ServiceInterface.GetCharactersWithSearch getCharacters = serviceBuilder.getCharactersWithSearch();
        Call<CharacterResponse> call = getCharacters.getCharacters(offset, limit, searchKeyWord);
        call.enqueue(new Callback<CharacterResponse>() {
            @Override
            public void onResponse(Call<CharacterResponse> call, Response<CharacterResponse> response) {

                if (response.isSuccessful() && response.code() == 200) {
                    try {
                        setCharactersWithSearch(response.body().getData().getCharacters());
                    } catch (Exception e) {
                        Log.e(TAG, "onResponse: loadCharactersWithSearch " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<CharacterResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: loadCharactersWithSearch " + t.getMessage());
            }
        });

    }

    public LiveData<List<Character>> getCharactersWithSearch() {
        return charactersWithSearch;
    }

    private void setCharactersWithSearch(List<Character> characters) {
        charactersWithSearch.setValue(characters);
    }


}
