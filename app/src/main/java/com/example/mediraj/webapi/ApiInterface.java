package com.example.mediraj.webapi;


import com.example.mediraj.model.AllDepartmentModel;
import com.example.mediraj.model.AllDiagnosticModel;
import com.example.mediraj.model.AllDoctorList;
import com.example.mediraj.model.AllPathologyModel;
import com.example.mediraj.model.AllSurgicalModel;
import com.example.mediraj.model.AllbloodModel;
import com.example.mediraj.model.BloodBooking_Model;
import com.example.mediraj.model.Clinic_add_booking;
import com.example.mediraj.model.ClinicalModel;
import com.example.mediraj.model.Department;
import com.example.mediraj.model.DoctorBookingModel;
import com.example.mediraj.model.MedicinRequestModel;
import com.example.mediraj.model.ProductConfirmation;
import com.example.mediraj.model.SingleDepartment;
import com.example.mediraj.model.SingleDoctor;
import com.example.mediraj.model.UserData;
import com.example.mediraj.model.checkout.CheckoutModel;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
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
    Call<Clinic_add_booking> clinicBooking(@Header("Authorization") String auth, @FieldMap Map<String,String> params);


    //get all diagonstic Services
    @GET("diagnostic/all")
    Call<AllDiagnosticModel> allDiagonsticServices(@Header("Authorization") String auth);

    //get all Homepathology
    @GET("pathology/all")
    Call<AllPathologyModel> allPathologyServices(@Header("Authorization") String auth);

    //get all Surgical
    @GET("surgical/all")
    Call<AllSurgicalModel> allSurgicalServices(@Header("Authorization") String auth);

    //api for notification status
    //get all Surgical
    @FormUrlEncoded
    @POST("user/notification")
    Call<UserData> notificationStatus(@Header("Authorization") String auth,@Field("id") String userId);

    //get all Department
    @GET("department/all")
    Call<AllDepartmentModel> allDepartment(@Header("Authorization") String auth);

    //get all Doctor
    @GET("doctor/all")
    Call<AllDoctorList> allDoctorServices(@Header("Authorization") String auth);

    //get all Blood
    @GET("blood-group/all")
    Call<AllbloodModel> allBloodServices(@Header("Authorization") String auth);

    @FormUrlEncoded
    @POST("blood-group/add-booking")
    Call<BloodBooking_Model> blood_booking(@Header("Authorization") String auth, @FieldMap Map<String,String> params);

    //MedicineServices
    @Multipart
    @POST("medicine/add-request")
    Call<MedicinRequestModel> medicine_services(@Header("Authorization") String auth,
                                                @PartMap Map<String,RequestBody> params,
                                                @Part MultipartBody.Part file);

    //api for checkout
    @POST("diagnostic/checkout")
    Call<ProductConfirmation> sendCheckoutDiagnostic(@Header("Authorization") String auth,
                                                     @Body CheckoutModel checkoutModel);

    @POST("pathology/checkout")
    Call<ProductConfirmation> sendCheckoutPathology(@Header("Authorization") String auth,
                                                   @Body CheckoutModel checkoutModel);

    @POST("surgical/checkout")
    Call<ProductConfirmation> sendCheckoutSurgical(@Header("Authorization") String auth,
                                                 @Body CheckoutModel checkoutModel);

    //
    @FormUrlEncoded
    @POST("department/single")
    Call<SingleDepartment> getDoctorByDept(@Header("Authorization") String auth,
                                           @Field("id") int deptId);

    @FormUrlEncoded
    @POST("doctor/single")
    Call<SingleDoctor> getSingleDoctorInfo(@Header("Authorization") String auth,
                                           @Field("id") String deptId,
                                           @Field("user") String userId);

    @FormUrlEncoded
    @POST("appointment/create")
    Call<DoctorBookingModel> doctorBooking(@Header("Authorization") String auth,@FieldMap Map<String,String> params);

}
