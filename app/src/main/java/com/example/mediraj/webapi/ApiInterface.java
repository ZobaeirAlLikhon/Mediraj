package com.example.mediraj.webapi;


import com.example.mediraj.model.Department;
import com.example.mediraj.model.UserData;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("userRegistration")
    Call<UserData> userSignUp(@Header("Authorization") String auth,
                              @FieldMap Map<String,String> params);
    @FormUrlEncoded
    @POST("userLogin")
    Call<UserData> userLogIn(@Header("Authorization") String auth,
                                 @FieldMap Map<String,String> params);

    @FormUrlEncoded
    @POST("forgotPassword")
    Call<UserData> forgotPass(@Header("Authorization") String auth,
                              @Field("mobile") String mobile,
                              @Field("token") String token);

    @FormUrlEncoded
    @POST("resetPassword")
    Call<UserData> resetPass(@Header("Authorization") String auth,
                             @FieldMap Map<String,String> params);



    @GET("departments")
    Call<Department> department(@Header("Authorization") String auth);
}
