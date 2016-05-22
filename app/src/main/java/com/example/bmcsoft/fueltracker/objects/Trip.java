package com.example.bmcsoft.fueltracker.objects;


import java.util.ArrayList;
import java.util.Date;
/**
 * Created by bmCSoft on 2016-05-21.
 */
public class Trip {
    private Date date;
    private String time;
    private Vehicle vehicle;
    private String description;
    private boolean isStarted = false;
    private ArrayList<FillUp> fill_up;

    public void startTrip(Vehicle vehicle,String description){
        this.fill_up = new ArrayList<>();
        this.isStarted = true;
        this.vehicle = vehicle;
        this.description = description;
        //get date from the system
    }

    public void endTrip(){
        if(isStarted){
            isStarted = false;
        }
    }

    public void addFillUp(float amount,float unit_price,float total_cost,String fuel_type,String time){
        FillUp fup = new FillUp(this.vehicle,amount,unit_price,total_cost,fuel_type,time);
        fill_up.add(fup);
    }
}
