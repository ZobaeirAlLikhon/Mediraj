package com.example.mediraj.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Clinic_add_booking {
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
    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }




    public class Data {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("clinic_id")
        @Expose
        private Integer clinicId;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("patient_name")
        @Expose
        private String patientName;
        @SerializedName("patient_mobile")
        @Expose
        private String patientMobile;
        @SerializedName("patient_address")
        @Expose
        private String patientAddress;
        @SerializedName("booking_purpose")
        @Expose
        private String bookingPurpose;
        @SerializedName("service_status")
        @Expose
        private String serviceStatus;
        @SerializedName("confirmed_date")
        @Expose
        private Object confirmedDate;
        @SerializedName("cancelled_date")
        @Expose
        private Object cancelledDate;
        @SerializedName("cancelled_reason")
        @Expose
        private Object cancelledReason;
        @SerializedName("completed_date")
        @Expose
        private Object completedDate;
        @SerializedName("cost_amount")
        @Expose
        private Object costAmount;
        @SerializedName("vat_amount")
        @Expose
        private Object vatAmount;
        @SerializedName("service_charge")
        @Expose
        private Object serviceCharge;
        @SerializedName("misc_cost_amount")
        @Expose
        private Object miscCostAmount;
        @SerializedName("misc_cost_details")
        @Expose
        private Object miscCostDetails;
        @SerializedName("total_amount")
        @Expose
        private Object totalAmount;
        @SerializedName("rating")
        @Expose
        private Object rating;
        @SerializedName("review")
        @Expose
        private Object review;
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
        private Object updatedAt;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getClinicId() {
            return clinicId;
        }

        public void setClinicId(Integer clinicId) {
            this.clinicId = clinicId;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getPatientName() {
            return patientName;
        }

        public void setPatientName(String patientName) {
            this.patientName = patientName;
        }

        public String getPatientMobile() {
            return patientMobile;
        }

        public void setPatientMobile(String patientMobile) {
            this.patientMobile = patientMobile;
        }

        public String getPatientAddress() {
            return patientAddress;
        }

        public void setPatientAddress(String patientAddress) {
            this.patientAddress = patientAddress;
        }

        public String getBookingPurpose() {
            return bookingPurpose;
        }

        public void setBookingPurpose(String bookingPurpose) {
            this.bookingPurpose = bookingPurpose;
        }

        public String getServiceStatus() {
            return serviceStatus;
        }

        public void setServiceStatus(String serviceStatus) {
            this.serviceStatus = serviceStatus;
        }

        public Object getConfirmedDate() {
            return confirmedDate;
        }

        public void setConfirmedDate(Object confirmedDate) {
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

        public Object getCompletedDate() {
            return completedDate;
        }

        public void setCompletedDate(Object completedDate) {
            this.completedDate = completedDate;
        }

        public Object getCostAmount() {
            return costAmount;
        }

        public void setCostAmount(Object costAmount) {
            this.costAmount = costAmount;
        }

        public Object getVatAmount() {
            return vatAmount;
        }

        public void setVatAmount(Object vatAmount) {
            this.vatAmount = vatAmount;
        }

        public Object getServiceCharge() {
            return serviceCharge;
        }

        public void setServiceCharge(Object serviceCharge) {
            this.serviceCharge = serviceCharge;
        }

        public Object getMiscCostAmount() {
            return miscCostAmount;
        }

        public void setMiscCostAmount(Object miscCostAmount) {
            this.miscCostAmount = miscCostAmount;
        }

        public Object getMiscCostDetails() {
            return miscCostDetails;
        }

        public void setMiscCostDetails(Object miscCostDetails) {
            this.miscCostDetails = miscCostDetails;
        }

        public Object getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(Object totalAmount) {
            this.totalAmount = totalAmount;
        }

        public Object getRating() {
            return rating;
        }

        public void setRating(Object rating) {
            this.rating = rating;
        }

        public Object getReview() {
            return review;
        }

        public void setReview(Object review) {
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

        public Object getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Object updatedAt) {
            this.updatedAt = updatedAt;
        }

    }
}
