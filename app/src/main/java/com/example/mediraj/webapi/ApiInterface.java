package com.example.mediraj.webapi;


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
}
