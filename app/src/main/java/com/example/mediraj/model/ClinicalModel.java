package com.example.mediraj.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClinicalModel {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("response")
    @Expose
    private Integer response;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getResponse() {
        return response;
    }

    public void setResponse(Integer response) {
        this.response = response;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }



    public class Datum {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("contact_name")
        @Expose
        private String contactName;
        @SerializedName("contact_mobile")
        @Expose
        private String contactMobile;
        @SerializedName("contact_address")
        @Expose
        private String contactAddress;
        @SerializedName("clinic_title")
        @Expose
        private String clinicTitle;
        @SerializedName("clinic_address")
        @Expose
        private String clinicAddress;
        @SerializedName("clinic_purpose")
        @Expose
        private String clinicPurpose;
        @SerializedName("order_status")
        @Expose
        private String orderStatus;
        @SerializedName("confirmed_date")
        @Expose
        private String confirmedDate;
        @SerializedName("cancelled_date")
        @Expose
        private Object cancelledDate;
        @SerializedName("cancelled_reason")
        @Expose
        private Object cancelledReason;
        @SerializedName("completed_date")
        @Expose
        private String completedDate;
        @SerializedName("net_amount")
        @Expose
        private Integer netAmount;
        @SerializedName("vat_amount")
        @Expose
        private Integer vatAmount;
        @SerializedName("service_charge")
        @Expose
        private Integer serviceCharge;
        @SerializedName("misc_cost_amount")
        @Expose
        private Integer miscCostAmount;
        @SerializedName("misc_cost_details")
        @Expose
        private Object miscCostDetails;
        @SerializedName("total_amount")
        @Expose
        private Integer totalAmount;
        @SerializedName("rating")
        @Expose
        private Integer rating;
        @SerializedName("review")
        @Expose
        private String review;
        @SerializedName("dates")
        @Expose
        private String dates;
        @SerializedName("months")
        @Expose
        private String months;
        @SerializedName("years")
        @Expose
        private Integer years;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getContactName() {
            return contactName;
        }

        public void setContactName(String contactName) {
            this.contactName = contactName;
        }

        public String getContactMobile() {
            return contactMobile;
        }

        public void setContactMobile(String contactMobile) {
            this.contactMobile = contactMobile;
        }

        public String getContactAddress() {
            return contactAddress;
        }

        public void setContactAddress(String contactAddress) {
            this.contactAddress = contactAddress;
        }

        public String getClinicTitle() {
            return clinicTitle;
        }

        public void setClinicTitle(String clinicTitle) {
            this.clinicTitle = clinicTitle;
        }

        public String getClinicAddress() {
            return clinicAddress;
        }

        public void setClinicAddress(String clinicAddress) {
            this.clinicAddress = clinicAddress;
        }

        public String getClinicPurpose() {
            return clinicPurpose;
        }

        public void setClinicPurpose(String clinicPurpose) {
            this.clinicPurpose = clinicPurpose;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getConfirmedDate() {
            return confirmedDate;
        }

        public void setConfirmedDate(String confirmedDate) {
            this.confirmedDate = confirmedDate;
        }

        public Object getCancelledDate() {
            return cancelledDate;
        }

        public void setCancelledDate(Object cancelledDate) {
            this.cancelledDate = cancelledDate;
        }

        public Object getCancelledReason() {
            return cancelledReason;
        }

        public void setCancelledReason(Object cancelledReason) {
            this.cancelledReason = cancelledReason;
        }

        public String getCompletedDate() {
            return completedDate;
        }

        public void setCompletedDate(String completedDate) {
            this.completedDate = completedDate;
        }

        public Integer getNetAmount() {
            return netAmount;
        }

        public void setNetAmount(Integer netAmount) {
            this.netAmount = netAmount;
        }

        public Integer getVatAmount() {
            return vatAmount;
        }

        public void setVatAmount(Integer vatAmount) {
            this.vatAmount = vatAmount;
        }

        public Integer getServiceCharge() {
            return serviceCharge;
        }

        public void setServiceCharge(Integer serviceCharge) {
            this.serviceCharge = serviceCharge;
        }

        public Integer getMiscCostAmount() {
            return miscCostAmount;
        }

        public void setMiscCostAmount(Integer miscCostAmount) {
            this.miscCostAmount = miscCostAmount;
        }

        public Object getMiscCostDetails() {
            return miscCostDetails;
        }

        public void setMiscCostDetails(Object miscCostDetails) {
            this.miscCostDetails = miscCostDetails;
        }

        public Integer getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(Integer totalAmount) {
            this.totalAmount = totalAmount;
        }

        public Integer getRating() {
            return rating;
        }

        public void setRating(Integer rating) {
            this.rating = rating;
        }

        public String getReview() {
            return review;
        }

        public void setReview(String review) {
            this.review = review;
        }

        public String getDates() {
            return dates;
        }

        public void setDates(String dates) {
            this.dates = dates;
        }

        public String getMonths() {
            return months;
        }

        public void setMonths(String months) {
            this.months = months;
        }

        public Integer getYears() {
            return years;
        }

        public void setYears(Integer years) {
            this.years = years;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

    }

}
