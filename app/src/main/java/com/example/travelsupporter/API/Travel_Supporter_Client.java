package com.example.travelsupporter.API;

import com.example.travelsupporter.Tour;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Travel_Supporter_Client {

    @Headers("Authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiIzNCIsInBob25lIjoiMDk5NzMzNzA5MCIsImVtYWlsIjoiZGluaDAwMUBnbWFpbC5jb20iLCJleHAiOjE1NzU3MzE0OTQ1NTQsImFjY291bnQiOiJ1c2VyIiwiaWF0IjoxNTczMTM5NDk0fQ.gXtMxnjlNp4Ruf5n4a-oolfS06wGkuc0I9SqUbLSKrk")
    @GET("tour/list")
    Call<TourListResponse> getTourList(@Query("pageNum") int pageNum, @Query("rowPerPage") int rowPerPage);

//    @POST("/user/login")
//    Call<LoginRequest> login(@Body LoginRequest body)
}
