package com.example.mediraj.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SingleDoctor {

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
        public Integer id;
        @SerializedName("department_id")
        @Expose
        public Integer departmentId;
        @SerializedName("full_name")
        @Expose
        public String fullName;
        @SerializedName("email")
        @Expose
        public String email;
        @SerializedName("mobile")
        @Expose
        public String mobile;
        @SerializedName("gender")
        @Expose
        public String gender;
        @SerializedName("avatar")
        @Expose
        public String avatar;
        @SerializedName("specialty")
        @Expose
        public String specialty;
        @SerializedName("degree")
        @Expose
        public String degree;
        @SerializedName("designation")
        @Expose
        public String designation;
        @SerializedName("organization")
        @Expose
        public String organization;
        @SerializedName("address")
        @Expose
        public String address;
        @SerializedName("chember")
        @Expose
        public String chember;
        @SerializedName("location")
        @Expose
        public String location;
        @SerializedName("visiting_fees")
        @Expose
        public Integer visitingFees;
        @SerializedName("schedule_days")
        @Expose
        public String scheduleDays;
        @SerializedName("schedule_start")
        @Expose
        public String scheduleStart;
        @SerializedName("schedule_end")
        @Expose
        public String scheduleEnd;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("experience")
        @Expose
        public Integer experience;
        @SerializedName("about_doctor")
        @Expose
        public String aboutDoctor;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public Object updatedAt;
        @SerializedName("isWishListed")
        @Expose
        public String isWishListed;
        @SerializedName("wishlist_id")
        @Expose
        public Object wishlistId;

    }

}
