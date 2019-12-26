package com.example.travelsupporter.API;

import com.ygaps.travelapp.API.CreateTourRequest;
import com.ygaps.travelapp.API.CreateTourResponse;
import com.ygaps.travelapp.API.LoginFacebookRequest;
import com.ygaps.travelapp.API.LoginFacebookResponse;
import com.ygaps.travelapp.API.LoginRequest;
import com.ygaps.travelapp.API.LoginResponse;
import com.ygaps.travelapp.API.RegisterRequest;
import com.ygaps.travelapp.API.RegisterResponse;
import com.ygaps.travelapp.API.TourListResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Travel_Supporter_Client {

    @GET("tour/list")
    Call<TourListResponse> getTourList(@Header("Authorization") String header,
                                       @Query("pageNum") int pageNum,
                                       @Query("rowPerPage") int rowPerPage);

    @GET("tour/history-user")
    Call<TourListResponse> getPersonalTourList(@Header("Authorization") String header,
                                       @Query("pageIndex") int pageIndex,
                                       @Query("pageSize") int pageSize);

    @POST("user/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("user/login/by-facebook")
    Call<LoginFacebookResponse> loginByFacebook(@Body LoginFacebookRequest loginRequest);

    @POST("user/register")
    Call<RegisterResponse> register(@Body RegisterRequest registerRequest);

    @POST("tour/create")
    Call<CreateTourResponse> createTour(@Header("Authorization") String header,
                                        @Body CreateTourRequest createTourRequest);

}
