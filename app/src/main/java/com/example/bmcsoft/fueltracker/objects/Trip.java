package com.example.bmcsoft.fueltracker.objects;


import java.util.ArrayList;

/**
 * Created by bmCSoft on 2016-05-21.
 */
public class Trip {

    private int id;
    private String date;
    private String start_time;
    private Vehicle vehicle;
    private String start_odometer;
    private String description;
    private ArrayList<FillUp> fill_up;
    private Driver driver;

    public Trip(){
        this.fill_up = new ArrayList<>();
        this.driver = SharedObject.CUR_USER;
        this.vehicle = SharedObject.CUR_SELECTED_VEHICLE;
    }


    public void addFillUp(float amount,float unit_price,float total_cost,String fuel_type,String time){
        FillUp fup = new FillUp(this.vehicle,amount,unit_price,total_cost,fuel_type,time);
        fill_up.add(fup);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public ArrayList<FillUp> getFill_up() {
        return fill_up;
    }

    public void setFill_up(ArrayList<FillUp> fill_up) {
        this.fill_up = fill_up;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public String getStart_odometer() {
        return start_odometer;
    }

    public void setStart_odometer(String start_odometer) {
        this.start_odometer = start_odometer;
    }


}
