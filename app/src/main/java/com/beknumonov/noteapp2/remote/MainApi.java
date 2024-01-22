package com.beknumonov.noteapp2.remote;

import com.beknumonov.noteapp2.model.Note;
import com.beknumonov.noteapp2.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface MainApi {


    @POST("/v1/auth-token/")
        // base_url will be appended in the beginning by Retrofit
    Call<User> login(@Body User user);


    @GET("/v1/note/")
// endpoint: base_url/v1/note/
    Call<ArrayList<Note>> getNotes(@Header("Authorization") String bearerToken);
    // return type: ArrayList<Note> -> Notes.


    @POST("/v1/note/")
    Call<Note> createNote(@Header("Authorization") String bearerToken, @Body Note note);
}
