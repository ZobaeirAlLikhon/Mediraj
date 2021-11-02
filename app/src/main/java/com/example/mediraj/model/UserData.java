package com.example.mediraj.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserData {
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("response")
    @Expose
    public Integer response;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("data")
    @Expose
    public Data data;

    public static class Data {
        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("mobile")
        @Expose
        public String mobile;
        @SerializedName("email")
        @Expose
        public String email;
        @SerializedName("gender")
        @Expose
        public String gender;
        @SerializedName("birth_date")
        @Expose
        public String birthDate;
        @SerializedName("avatar")
        @Expose
        public String avatar;
        @SerializedName("password")
        @Expose
        public String password;
        @SerializedName("device_type")
        @Expose
        public String deviceType;
        @SerializedName("device_name")
        @Expose
        public String deviceName;
        @SerializedName("device_token")
        @Expose
        public String deviceToken;
        @SerializedName("notification")
        @Expose
        public String notification;
        @SerializedName("address")
        @Expose
        public String address;
        @SerializedName("latitude")
        @Expose
        public String latitude;
        @SerializedName("longitude")
        @Expose
        public String longitude;
        @SerializedName("otp_type")
        @Expose
        public String otpType;
        @SerializedName("otp_code")
        @Expose
        public String otpCode;
        @SerializedName("is_verified_account")
        @Expose
        public String isVerifiedAccount;
        @SerializedName("reset_code")
        @Expose
        public String resetCode;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("isLogIn")
        @Expose
        public String isLogIn;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;
    }
}
