package com.example.travelsupporter.API;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Travel_Supporter_Client {

    @GET("tour/list")
    Call<TourListResponse> getTourList(@Query("pageNum") int pageNum, @Query("rowPerPage") int rowPerPage);

    @POST("user/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
}
