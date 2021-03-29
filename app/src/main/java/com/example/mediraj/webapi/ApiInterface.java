package com.example.mediraj.webapi;


import com.example.mediraj.model.Example;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("user/signup")
    Call<Example> sign_up_profile(@Field("auth@api_key") String api_key,
                             @Field("Authenticate") String auth,
                             @Field("name") String name,
                             @Field("mobile") String mobile,
                             @Field("email") String email,
                             @Field("password") String password);
}
