package com.example.mediraj.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoctorBookingModel {

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
        @SerializedName("doctor_id")
        @Expose
        public Integer doctorId;
        @SerializedName("user_id")
        @Expose
        public Integer userId;
        @SerializedName("patient_name")
        @Expose
        public String patientName;
        @SerializedName("patient_email")
        @Expose
        public String patientEmail;
        @SerializedName("patient_mobile")
        @Expose
        public String patientMobile;
        @SerializedName("patient_address")
        @Expose
        public String patientAddress;
        @SerializedName("appointment_status")
        @Expose
        public String appointmentStatus;
        @SerializedName("requested_schedule")
        @Expose
        public String requestedSchedule;
        @SerializedName("appointment_schedule")
        @Expose
        public Object appointmentSchedule;
        @SerializedName("is_re_appointment")
        @Expose
        public String isReAppointment;
        @SerializedName("re_appointment_date")
        @Expose
        public Object reAppointmentDate;
        @SerializedName("visiting_fees")
        @Expose
        public Object visitingFees;
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
