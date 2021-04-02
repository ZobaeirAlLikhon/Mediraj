package com.example.mediraj.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("response")
    @Expose
    public String response;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("data")
    @Expose
    public List<UserDetail> data = null;

    public class UserDetail {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("full_name")
        @Expose
        public String fullName;
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
        @SerializedName("device")
        @Expose
        public String device;
        @SerializedName("device_type")
        @Expose
        public String deviceType;
        @SerializedName("device_token")
        @Expose
        public String deviceToken;
        @SerializedName("address")
        @Expose
        public Object address;
        @SerializedName("latitude")
        @Expose
        public String latitude;
        @SerializedName("longitude")
        @Expose
        public String longitude;
        @SerializedName("reset_code")
        @Expose
        public Object resetCode;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;

    }

}
