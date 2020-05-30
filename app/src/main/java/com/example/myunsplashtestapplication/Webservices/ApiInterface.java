package com.example.myunsplashtestapplication.Webservices;

import com.example.myunsplashtestapplication.Models.Collection;
import com.example.myunsplashtestapplication.Models.Photo;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("photos")
    Call<List<Photo>> getPhotos(
            @Query("page") int page
    );

//    @GET("collections/featured")
//    Call<List<Collection>> getCollections();
//
//    @GET("collections/{id}")
//    Call<List<Collection>> getInformationOfCollection(@Path("id") int id);
//
//    @GET("collections/{id}/photos")
//    Call<List<Photo>> getPhotosOfCollection(@Path("id") int id);

    @GET("photos/{id}")
    Call<Photo> getPhoto(@Path("id") String id);
}