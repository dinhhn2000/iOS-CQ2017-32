package com.ygaps.travelapp.API;

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
    @GET("/user/info")
    Call<UserInfoResponse> getUserInfo(@Header("Authorization") String header);

    @GET("/user/send-active")
    Call<SendEmailVerificationResponse> GetEmailVerification();

    @POST("user/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("user/login/by-facebook")
    Call<LoginFacebookResponse> loginByFacebook(@Body LoginFacebookRequest loginRequest);

    @POST("user/register")
    Call<RegisterResponse> register(@Body RegisterRequest registerRequest);

    @POST("tour/create")
    Call<CreateTourResponse> createTour(@Header("Authorization") String header,
                                        @Body CreateTourRequest createTourRequest);
    @POST("/user/edit-info")
    Call<MessageResponse> updateUserInfo(@Header("Authorization") String header,
                                               @Body UpdateUserInfoRequest updateUserInfoRequest);

    @POST("user/request-otp-recovery")
    Call<RequestOTP_PasswordRecoveryResponse> RequestOTP(@Body RequestOTP_PasswordRecoveryRequest requestOTP_passwordRecoveryRequest);

    @POST("/user/verify-otp-recovery")
    Call<MessageResponse> RecoveryPassword (@Body PasswordRecoveryRequest passwordRecoveryRequest);

}
