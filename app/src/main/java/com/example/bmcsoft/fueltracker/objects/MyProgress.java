package com.example.bmcsoft.fueltracker.objects;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by bmCSoft on 2016-05-31.
 */
public class MyProgress {
    HashMap<String,String> millage_log ;
    HashMap<String,String> fuel_cost_log ;
    ArrayList<HashMap<String,String>> longest_millage;
    ArrayList<HashMap<String,String>> favourite_vehicle;
    ArrayList<HashMap<String,String>> total_fuel_cost;

    public MyProgress(){
        millage_log = new HashMap<>();
       fuel_cost_log = new HashMap<>();
        longest_millage = new ArrayList<>();
        favourite_vehicle = new ArrayList<>();
        total_fuel_cost = new ArrayList<>();
    }

    public void addMillage(String key,String value){
        millage_log.put(key,value);                     //millage_log.get(key)
    }

    public void addFuelCost(String key,String value){
        fuel_cost_log.put(key, value);
    }

    public HashMap<String,String> getMillageData(){
        return this.millage_log;
    }

    public HashMap<String,String> getFuelCostData(){
        return this.fuel_cost_log;
    }

    public void setLongest_millage(HashMap<String,String> l_millage){
        longest_millage.add(l_millage);
    }

    public void setFavourite_vehicle(HashMap<String,String> vehicle){
        favourite_vehicle.add(vehicle);
    }

    public void setTotal_fuel_cost(HashMap<String,String> f_cost){
        total_fuel_cost.add(f_cost);
    }

    public ArrayList<HashMap<String ,String>> getLongest_millage(){
        return this.longest_millage;
    }

    public ArrayList<HashMap<String ,String>> getFavourite_vehicle(){
        return this.favourite_vehicle;
    }

    public ArrayList<HashMap<String ,String>> getTotal_fuel_cost(){
        return this.total_fuel_cost;
    }



}
