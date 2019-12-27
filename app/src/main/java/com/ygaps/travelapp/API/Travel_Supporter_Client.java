package com.ygaps.travelapp.API;

import java.util.Calendar;

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

    @GET("/user/info")
    Call<UserInfoResponse> getUserInfo(@Header("Authorization") String header);

    @GET("/user/send-active")
    Call<SendEmailVerificationResponse> GetEmailVerification(@Query("userId") int userId,
                                                             @Query("type") String type);

    @GET("/user/active")
    Call<VerifyCode_EmailVerificationResponse> VerifyCode(@Query("userId") int userId,
                                                          @Query("type") String type,
                                                          @Query("verifyCode") String verifyCode);
    @GET("/tour/notification-list")
    Call<NotificationListResponse> getNotification (@Header("Authorization") String header,
                                                    @Query("pageIndex") String tourId,
                                                    @Query("pageIndex") int pageIndex,
                                                    @Query("pageSize") String pageSize);

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
    Call<MessageResponse> RecoveryPassword(@Body VerifyOTP_PasswordRecoveryRequest verifyOTP_passwordRecoveryRequest);

    @POST("tour/set-stop-points")
    Call<MessageResponse> addStopPoint(@Header("Authorization") String header,
                                       @Body AddStopPointRequest addStopPointRequest);

}
