package com.example.bmcsoft.fueltracker.objects;

import android.content.Context;

import com.example.bmcsoft.fueltracker.dataaccess.LocalDBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bmCSoft on 2016-05-03.
 */
public class SharedObject {
    public static List<Vehicle>  VEHICLE_LIST = new ArrayList<>();
    public static Vehicle CUR_SELECTED_VEHICLE =null;
    public static Driver CUR_USER =null;
    public static Trip CUR_TRIP =null;
    public static boolean APPLICATION_TRIP_STATE = false;    // true - on a trip
    public static MyProgress MY_PROGRESS = null;

}
