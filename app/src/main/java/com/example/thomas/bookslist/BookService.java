package com.example.thomas.bookslist;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BookService {

    public static final String BASEURL = "https://www.googleapis.com/books/v1/";

    @GET("volumes")
    Call<Book> listBooks(@Query("q") String query);
}
