package com.moaazfathyelneshawy.marvelapp.Networking;

import com.moaazfathyelneshawy.marvelapp.Networking.Response.CharacterResponse;
import com.moaazfathyelneshawy.marvelapp.Networking.Response.DetailsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServiceInterface {

    interface GetCharacters {
        @GET("characters")
        Call<CharacterResponse> getCharacters(@Query("offset") int offset, @Query("limit") int limit);
    }

    interface GetCharactersWithSearch {
        @GET("characters")
        Call<CharacterResponse> getCharacters(@Query("offset") int offset, @Query("limit") int limit, @Query("nameStartsWith") String name);
    }

    interface GetComics {
        @GET("characters/{character_id}/comics")
        Call<DetailsResponse> getComics(@Path("character_id") int characterId, @Query("offset") int offset, @Query("limit") int limit);
    }

    interface GetEvents {
        @GET("characters/{character_id}/events")
        Call<DetailsResponse> getEvents(@Path("character_id") int characterId, @Query("offset") int offset, @Query("limit") int limit);
    }

    interface GetSeries {
        @GET("characters/{character_id}/series")
        Call<DetailsResponse> getSeries(@Path("character_id") int characterId, @Query("offset") int offset, @Query("limit") int limit);
    }

    interface GetStories {
        @GET("characters/{character_id}/stories")
        Call<DetailsResponse> getStories(@Path("character_id") int characterId, @Query("offset") int offset, @Query("limit") int limit);
    }
}
