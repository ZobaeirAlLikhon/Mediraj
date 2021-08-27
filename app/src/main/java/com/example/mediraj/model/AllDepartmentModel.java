package com.example.mediraj.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllDepartmentModel {
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
    public List<Datum> data = null;

    public static class Datum {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("logo")
        @Expose
        public String logo;
        @SerializedName("is_featured")
        @Expose
        public String isFeatured;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;
        @SerializedName("isChecked")
        @Expose
        private boolean isChecked;

        public boolean getIsChecked() {
            return isChecked;
        }

        public void setIsChecked(boolean isChecked) {
            this.isChecked = isChecked;
        }
    }

}
