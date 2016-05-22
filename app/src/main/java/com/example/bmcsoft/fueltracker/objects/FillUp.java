package com.example.bmcsoft.fueltracker.objects;

/**
 * Created by bmCSoft on 2016-05-21.
 *
 * FillUp - FillUp information class
 */
public class FillUp {
    private Vehicle vehicle;
    private float amount; //amount in liters
    private float unit_price;
    private float total_price;
    private String fuel_type;
    private String time;

    public FillUp(Vehicle v,float amount,float unit_price,float total_price,String fuel_type,String time){
        this.vehicle =v;
        this.amount = amount;
        this.unit_price = unit_price;
        this.total_price = total_price;
        this.fuel_type = fuel_type;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public float getAmount() {
        return amount;
    }

    public float getUnit_price() {
        return unit_price;
    }

    public float getTotal_price() {
        return total_price;
    }

    public String getFuel_type() {
        return fuel_type;
    }


}
