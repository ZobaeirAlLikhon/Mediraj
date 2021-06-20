package com.example.mediraj.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllDoctorList {
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
        @SerializedName("department_id")
        @Expose
        private Integer departmentId;
        @SerializedName("full_name")
        @Expose
        private String fullName;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("avatar")
        @Expose
        private String avatar;
        @SerializedName("specialty")
        @Expose
        private String specialty;
        @SerializedName("degree")
        @Expose
        private String degree;
        @SerializedName("designation")
        @Expose
        private String designation;
        @SerializedName("organization")
        @Expose
        private String organization;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("chember")
        @Expose
        private String chember;
        @SerializedName("location")
        @Expose
        private String location;
        @SerializedName("visiting_fees")
        @Expose
        private Integer visitingFees;
        @SerializedName("schedule_days")
        @Expose
        private String scheduleDays;
        @SerializedName("schedule_start")
        @Expose
        private String scheduleStart;
        @SerializedName("schedule_end")
        @Expose
        private String scheduleEnd;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("experience")
        @Expose
        private Integer experience;
        @SerializedName("about_doctor")
        @Expose
        private String aboutDoctor;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("isWishListed")
        @Expose
        private String isWishListed;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getDepartmentId() {
            return departmentId;
        }

        public void setDepartmentId(Integer departmentId) {
            this.departmentId = departmentId;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getSpecialty() {
            return specialty;
        }

        public void setSpecialty(String specialty) {
            this.specialty = specialty;
        }

        public String getDegree() {
            return degree;
        }

        public void setDegree(String degree) {
            this.degree = degree;
        }

        public String getDesignation() {
            return designation;
        }

        public void setDesignation(String designation) {
            this.designation = designation;
        }

        public String getOrganization() {
            return organization;
        }

        public void setOrganization(String organization) {
            this.organization = organization;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getChember() {
            return chember;
        }

        public void setChember(String chember) {
            this.chember = chember;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public Integer getVisitingFees() {
            return visitingFees;
        }

        public void setVisitingFees(Integer visitingFees) {
            this.visitingFees = visitingFees;
        }

        public String getScheduleDays() {
            return scheduleDays;
        }

        public void setScheduleDays(String scheduleDays) {
            this.scheduleDays = scheduleDays;
        }

        public String getScheduleStart() {
            return scheduleStart;
        }

        public void setScheduleStart(String scheduleStart) {
            this.scheduleStart = scheduleStart;
        }

        public String getScheduleEnd() {
            return scheduleEnd;
        }

        public void setScheduleEnd(String scheduleEnd) {
            this.scheduleEnd = scheduleEnd;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Integer getExperience() {
            return experience;
        }

        public void setExperience(Integer experience) {
            this.experience = experience;
        }

        public String getAboutDoctor() {
            return aboutDoctor;
        }

        public void setAboutDoctor(String aboutDoctor) {
            this.aboutDoctor = aboutDoctor;
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

        public String getIsWishListed() {
            return isWishListed;
        }

        public void setIsWishListed(String isWishListed) {
            this.isWishListed = isWishListed;
        }


    }
}
