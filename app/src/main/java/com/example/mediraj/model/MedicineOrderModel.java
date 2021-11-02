package com.example.mediraj.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MedicineOrderModel {
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
        @SerializedName("user_id")
        @Expose
        public Integer userId;
        @SerializedName("contact_name")
        @Expose
        public String contactName;
        @SerializedName("contact_mobile")
        @Expose
        public String contactMobile;
        @SerializedName("contact_address")
        @Expose
        public String contactAddress;
        @SerializedName("medicines")
        @Expose
        public String medicines;
        @SerializedName("prescription")
        @Expose
        public String prescription;
        @SerializedName("service_status")
        @Expose
        public String serviceStatus;
        @SerializedName("confirmed_date")
        @Expose
        public Object confirmedDate;
        @SerializedName("cancelled_date")
        @Expose
        public Object cancelledDate;
        @SerializedName("cancelled_reason")
        @Expose
        public Object cancelledReason;
        @SerializedName("completed_date")
        @Expose
        public Object completedDate;
        @SerializedName("net_amount")
        @Expose
        public Object netAmount;
        @SerializedName("vat_amount")
        @Expose
        public Object vatAmount;
        @SerializedName("service_charge")
        @Expose
        public Object serviceCharge;
        @SerializedName("misc_cost_amount")
        @Expose
        public Object miscCostAmount;
        @SerializedName("misc_cost_details")
        @Expose
        public Object miscCostDetails;
        @SerializedName("total_amount")
        @Expose
        public Object totalAmount;
        @SerializedName("rating")
        @Expose
        public Object rating;
        @SerializedName("review")
        @Expose
        public Object review;
        @SerializedName("dates")
        @Expose
        public String dates;
        @SerializedName("months")
        @Expose
        public String months;
        @SerializedName("years")
        @Expose
        public Integer years;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public Object updatedAt;
    }
}
