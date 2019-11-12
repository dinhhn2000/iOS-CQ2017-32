package com.example.travelsupporter.API;

public class CreateTourRequest {
    private String tourName;
    private String startTime;
    private String endTime;
    public CreateTourRequest (String tourName, String startTime, String endTime){
        this.tourName = tourName;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    public void setTourName(String tourName) {
        this.tourName = tourName;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTourName() {
        return tourName;
    }
    public String getStartTime() {
        return startTime;
    }
    public String getEndTimeTime() {
        return endTime;
    }

}
