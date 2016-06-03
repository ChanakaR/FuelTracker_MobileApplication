package com.example.bmcsoft.fueltracker.objects;

/**
 * Created by bmCSoft on 2016-06-03.
 */
public class TripSummary {
    private int id;
    private String start_time;
    private String end_time;
    private String date;
    private String description;
    private String vehicle;
    private String start_odometer;
    private String end_odometer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getStart_odometer() {
        return start_odometer;
    }

    public void setStart_odometer(String start_odometer) {
        this.start_odometer = start_odometer;
    }

    public String getEnd_odometer() {
        return end_odometer;
    }

    public void setEnd_odometer(String end_odometer) {
        this.end_odometer = end_odometer;
    }

}
