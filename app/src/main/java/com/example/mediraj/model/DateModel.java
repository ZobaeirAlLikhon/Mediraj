package com.example.mediraj.model;

public class DateModel {
    private String dayName;
    private String date;
    private String month;
    private String year;
    private boolean isChecked ;

    public DateModel(String dayName, String date, String month, String year, boolean isChecked) {
        this.dayName = dayName;
        this.date = date;
        this.month = month;
        this.year = year;
        this.isChecked = isChecked;
    }

    public String getDayName() {
        return dayName;
    }

    public String getDate() {
        return date;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
