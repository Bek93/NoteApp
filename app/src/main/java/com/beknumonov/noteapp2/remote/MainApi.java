package com.beknumonov.noteapp2.remote;

import com.beknumonov.noteapp2.model.Book;
import com.beknumonov.noteapp2.model.News;
import com.beknumonov.noteapp2.model.Note;
import com.beknumonov.noteapp2.model.User;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface MainApi {


    @POST("/v1/auth-token/")
        // base_url will be appended in the beginning by Retrofit
    Call<User> login(@Body User user);


    @POST("/v1/user/")
    Call<User> createUser(@Body User user);


    @GET("/v1/user/")
    Call<ArrayList<User>> getUserList();

    @PUT("/v1/user/{id}/")
    Call<User> registerDeviceToken(@Path("id") int userId, @Body User user);

    @GET("/v1/note/")
// endpoint: base_url/v1/note/
    Call<ArrayList<Note>> getNotes();
    // return type: ArrayList<Note> -> Notes.


    @POST("/v1/note/")
    Call<Note> createNote( @Body Note note);


    @GET("/v1/note/{id}/")
    Call<Note> getNote(@Path("id") int noteId);


    @PUT("/v1/note/{id}/")
    Call<Note> updateNote( @Path("id") int noteId, @Body Note note);

    @DELETE("/v1/note/{id}/")
    Call<Void> deleteNote( @Path("id") int noteId);

    // News

    @GET("/v1/news/")
    Call<ArrayList<News>> getNews(@Query("page") int page);


    @POST("/v1/news/{id}/share/")
    Call<String> shareNews( @Path("id") int newsId, @Body JsonObject body);

    @Multipart
    @POST("/v1/book/")
    Call<Book> createBook(
                          @Part("title") RequestBody title,
                          @Part("description") RequestBody description,
                          @Part MultipartBody.Part image
    );

}
