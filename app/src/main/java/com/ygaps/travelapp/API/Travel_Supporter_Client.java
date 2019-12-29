package com.ygaps.travelapp.API;

import com.ygaps.travelapp.API.Requests.AddStopPointRequest;
import com.ygaps.travelapp.API.Requests.CreateTourRequest;
import com.ygaps.travelapp.API.Requests.LoginFacebookRequest;
import com.ygaps.travelapp.API.Requests.LoginRequest;
import com.ygaps.travelapp.API.Requests.RegisterRequest;
import com.ygaps.travelapp.API.Requests.RequestOTP_PasswordRecoveryRequest;
import com.ygaps.travelapp.API.Requests.RequestOTP_PasswordRecoveryResponse;
import com.ygaps.travelapp.API.Requests.SuggestedStopPointRequest;
import com.ygaps.travelapp.API.Requests.UpdateUserInfoRequest;
import com.ygaps.travelapp.API.Requests.VerifyOTP_PasswordRecoveryRequest;
import com.ygaps.travelapp.API.Responses.CreateTourResponse;
import com.ygaps.travelapp.API.Responses.LoginFacebookResponse;
import com.ygaps.travelapp.API.Responses.LoginResponse;
import com.ygaps.travelapp.API.Responses.MessageResponse;
import com.ygaps.travelapp.API.Responses.NotificationListResponse;
import com.ygaps.travelapp.API.Responses.RegisterResponse;
import com.ygaps.travelapp.API.Responses.SendEmailVerificationResponse;
import com.ygaps.travelapp.API.Responses.SuggestedStopPointResponse;
import com.ygaps.travelapp.API.Responses.TourListResponse;
import com.ygaps.travelapp.API.Responses.UserInfoResponse;
import com.ygaps.travelapp.API.Responses.VerifyCode_EmailVerificationResponse;
import com.ygaps.travelapp.API.Responses.getTourInfoResponse;

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
    Call<NotificationListResponse> getNotification(@Header("Authorization") String header,
                                                   @Query("pageIndex") String tourId,
                                                   @Query("pageIndex") int pageIndex,
                                                   @Query("pageSize") String pageSize);

    @GET("/tour/info")
    Call<getTourInfoResponse> getTour(@Header("Authorization") String header,
                                      @Query("tourId") long tourId);

    @POST("/tour/suggested-destination-list")
    Call<SuggestedStopPointResponse> getSuggestedStopPoint(@Header("Authorization") String header,
                                                           @Body SuggestedStopPointRequest suggestedStopPointRequest);

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
    Call<MessageResponse> setStopPoint(@Header("Authorization") String header,
                                       @Body AddStopPointRequest addStopPointRequest);

}
