package com.example.bmcsoft.fueltracker.dataaccess;

/**
 * Created by bmCSoft on 2016-05-02.
 */
public class DAConfig {
    /*
     * Remote server configurations
     */
    public static final String URL_GET_ALL_VEHICLE ="http://192.168.43.172/FTApplicationServer/RequestHandlerMobile.php?method=view_all_vehicle";
    public static final String URL_ADD_FILL_UP="http://192.168.43.172/FTApplicationServer/RequestHandlerMobile.php?method=add_fill_up";
    public static final String URL_LOG_IN="http://192.168.43.172/FTApplicationServer/RequestHandlerMobile.php?method=login";
    public static final String URL_ADD_TRIP = "http://192.168.43.172/FTApplicationServer/RequestHandlerMobile.php?method=new_trip";
    public static final String URL_GET_TRIP = "http://192.168.43.172/FTApplicationServer/RequestHandlerMobile.php?method=a_trip";
    public static final String URL_GET_VEHICLE = "http://192.168.43.172/FTApplicationServer/RequestHandlerMobile.php?method=a_vehicle";
    public static final String URL_CHECK_TRIP = "http://192.168.43.172/FTApplicationServer/RequestHandlerMobile.php?method=trip_check";
    public static final String URL_GET_PROGRESS_DATA = "http://192.168.43.172/FTApplicationServer/RequestHandlerMobile.php?method=my_stat";
    public static final String URL_GET_MY_TRIP_LIST = "http://192.168.43.172/FTApplicationServer/RequestHandlerMobile.php?method=my_trip_list";

    /*
     * Local database configurations
     */
    public static final String DATABASE_NAME = "FuelTracker.db";
    public static final String LOGIN_TABLE_NAME = "user";
    public static final String LOGIN_COLUMN_USERNAME = "user_name";
    public static final String LOGIN_COLUMN_PASSWORD = "password";

    public static final String TRIP_TABLE_NAME = "trip";
    public static final String TRIP_COLUMN_ONTRIP = "on_trip";
    public static final String TRIP_COLUMN_TRIPID = "trip_id";
    public static final String TRIP_COLUMN_TRIPDRIVERID = "driver_id";


}
