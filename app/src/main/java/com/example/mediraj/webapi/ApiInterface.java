package com.example.mediraj.webapi;


import com.example.mediraj.model.Department;
import com.example.mediraj.model.LogInMessage;
import com.example.mediraj.model.User;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("user/signup")
    Call<Map<String,String>> userSignUp(@Field("auth@api_key") String api_key,
                             @Field("Authenticate") String auth,
                             @FieldMap Map<String,String> params);
    @FormUrlEncoded
    @POST("user/login")
    Call<LogInMessage> userLogIn(@Field("auth@api_key") String api_key,
                                 @Field("Authenticate") String auth,
                                 @Field("email") String email,
                                 @Field("password") String password);
    @FormUrlEncoded
    @POST("department/all")
    Call<Department> department(@Field("auth@api_key") String api_key,
                                @Field("Authenticate") String auth);
}
