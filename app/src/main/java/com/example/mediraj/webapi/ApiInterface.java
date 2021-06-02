package com.example.mediraj.webapi;


import com.example.mediraj.model.AllDiagonosticModel;
import com.example.mediraj.model.AllPathologyModel;
import com.example.mediraj.model.Clinic_add_booking;
import com.example.mediraj.model.ClinicalModel;
import com.example.mediraj.model.Department;
import com.example.mediraj.model.UserData;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface ApiInterface {

    //sign up
    @FormUrlEncoded
    @POST("user/registration")
    Call<UserData> userSignUp(@Header("Authorization") String auth,
                              @FieldMap Map<String,String> params);
    //Login
    @FormUrlEncoded
    @POST("user/login")
    Call<UserData> userLogIn(@Header("Authorization") String auth,
                                 @FieldMap Map<String,String> params);

    //forgot password
    @FormUrlEncoded
    @POST("user/forgot-password")
    Call<UserData> forgotPass(@Header("Authorization") String auth,
                              @Field("mobile") String mobile,
                              @Field("token") String token);

    //reset password
    @FormUrlEncoded
    @POST("user/reset-password")
    Call<UserData> resetPass(@Header("Authorization") String auth,
                             @FieldMap Map<String,String> params);

    //logout
    @FormUrlEncoded
    @POST("user/logout")
    Call<Map<String,String>> userLogout(@Header("Authorization") String auth,@Field("id") String userId);

    //update profile
    @Multipart
    @POST("user/update-profile")
    Call<UserData> updateProfile(@Header("Authorization") String auth,
                                 @PartMap Map<String,RequestBody> params,
                                 @Part MultipartBody.Part file);



    //department list
    @GET("departments")
    Call<Department> department(@Header("Authorization") String auth);


    //clinical_services
    @GET("clinic/all")
    Call<ClinicalModel> clinicalServices(@Header("Authorization") String auth);

    @FormUrlEncoded
    @POST("clinic/add-booking")
    Call<Clinic_add_booking> clinicBooking(@Header("Authorization") String auth,
                                           @Field("clinic_id") String clinic_id,
                                           @Field("user_id") String user_id,
                                           @Field("name") String name,
                                           @Field("mobile") String mobile,
                                           @Field("address") String address,
                                           @Field("purpose") String purpose

                                           );


    //get all diagonstic Services
    @GET("diagnostic/all")
    Call<AllDiagonosticModel> allDiagonsticServices(@Header("Authorization") String auth);

    //get all Homepathology
    @GET("pathology/all")
    Call<AllPathologyModel> allPathologyServices(@Header("Authorization") String auth);

}
