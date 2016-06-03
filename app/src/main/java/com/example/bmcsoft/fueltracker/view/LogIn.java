package com.example.bmcsoft.fueltracker.view;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bmcsoft.fueltracker.MainActivity;
import com.example.bmcsoft.fueltracker.R;
import com.example.bmcsoft.fueltracker.dataaccess.DAConfig;
import com.example.bmcsoft.fueltracker.dataaccess.LocalDBHelper;
import com.example.bmcsoft.fueltracker.dataaccess.RequestHandler;
import com.example.bmcsoft.fueltracker.objects.Driver;
import com.example.bmcsoft.fueltracker.objects.MyProgress;
import com.example.bmcsoft.fueltracker.objects.SharedObject;
import com.example.bmcsoft.fueltracker.objects.Trip;
import com.example.bmcsoft.fueltracker.objects.Vehicle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LogIn extends AppCompatActivity {

    private TextView u_name;
    private TextView pwd;
    private TextView info;
    private String JSON_STRING;
    private String username;
    private String password;
    private View view;
    private CheckBox rememberMe;

    private LocalDBHelper localDb = null;

    private boolean onTrip = false;
    private String tripVehicleId =null;
    private String tripDriverId = null;
    private String tripDriverDriverId = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        this.u_name = (TextView) findViewById(R.id.user_name_log_in);
        this.pwd = (TextView) findViewById(R.id.password_log_in);
        this.info = (TextView) findViewById(R.id.info_message_log_in);
        this.rememberMe = (CheckBox) findViewById(R.id.remeber_me);

        localDb = new LocalDBHelper(this);
        checkForSavedUser();
        //checkForSavedTrip();
    }

    private void checkForSavedUser(){
        int numOfRows = this.localDb.numberOfRows("user");
        if(numOfRows >0){
            Cursor res_user =this.localDb.getUser();
            res_user.moveToFirst();
            this.u_name.setText(res_user.getString(res_user.getColumnIndex(DAConfig.LOGIN_COLUMN_USERNAME)));
            this.pwd.setText(res_user.getString(res_user.getColumnIndex(DAConfig.LOGIN_COLUMN_PASSWORD)));
            this.rememberMe.setChecked(true);
        }
    }


    private void fetchTripData(){
        final Context context = this;

        class TripDataJson extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(context,"Fetching trip data","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                setCurrTrip(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> data = new HashMap<>();
                data.put("driver_id",Integer.toString(SharedObject.CUR_USER.getId()));

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(DAConfig.URL_CHECK_TRIP, data);
                return res;
            }
        }
        TripDataJson tdj = new TripDataJson();
        tdj.execute();

    }

    private void setCurrTrip(String json_string){
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json_string);
            Trip trip = new Trip();

            int error_code = jsonObject.getInt("error_code");

            if(error_code ==0){
                JSONArray result = jsonObject.getJSONArray("message");
                for(int i = 0; i<result.length(); i++){

                    JSONObject jo = result.getJSONObject(i);
                    trip.setId(jo.getInt("id"));
                    trip.setDate(jo.getString("date"));
                    trip.setDescription(jo.getString("description"));
                    trip.setStart_odometer(jo.getString("start_odometer"));
                    trip.setStart_time(jo.getString("start_time"));
                    trip.setDriver(SharedObject.CUR_USER);
                    this.tripVehicleId = jo.getString("vehicle_id");
                    //this.tripDriverId = jo.getString("driver_id");
                }

                SharedObject.CUR_TRIP = trip;

                fetchVehicleData();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                SharedObject.CUR_TRIP.setVehicle(SharedObject.CUR_SELECTED_VEHICLE);
                SharedObject.APPLICATION_TRIP_STATE = true;

            }
            else{
                this.info.setText(jsonObject.getString("message"));
            }
        }catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    private void fetchVehicleData(){
        final Context context = this;

        class TripDataJson extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(context,"Fetching vehicle data","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                setCurrVehicle(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> data = new HashMap<>();
                data.put("vehicle_id",tripVehicleId);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(DAConfig.URL_GET_VEHICLE, data);
                return res;
            }
        }
        TripDataJson tdj = new TripDataJson();
        tdj.execute();
    }

    private void setCurrVehicle(String json_string){
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(json_string);
            Vehicle vehicle = new Vehicle();

            int error_code = jsonObject.getInt("error_code");

            if(error_code ==0){
                JSONArray result = jsonObject.getJSONArray("message");
                for(int i = 0; i<result.length(); i++){

                    JSONObject jo = result.getJSONObject(i);
                    vehicle.setId(jo.getString("id"));
                    vehicle.setFuelType(jo.getString("fuel_type"));
                    vehicle.setLplate(jo.getString("licence_plate"));
                    vehicle.setMake(jo.getString("make"));
                    vehicle.setModel(jo.getString("model"));
                    vehicle.setYear(jo.getString("year"));
                    vehicle.setVclass(jo.getString("v_class"));
                }

                SharedObject.CUR_SELECTED_VEHICLE = vehicle;

            }
            else{
                this.info.setText(jsonObject.getString("message"));
            }


        }catch (JSONException ex){
            ex.printStackTrace();
        }
    }

    private void logInto(){
        username = u_name.getText().toString();
        password = pwd.getText().toString();

        if(username.matches("")|| password.matches("")){
            this.info.setText("All fields are required.");
        }else{
            fetchUserData();
        }
    }

    public void goMainMenu(View view){
        this.view = view;
        logInto();
    }

    /*
     * set vehicle list in Spinner
     */
    private void validateUser(){

        JSONObject jsonObject = null;
        Driver driver= new Driver();

        try {

            jsonObject = new JSONObject(JSON_STRING);
            int error_code = jsonObject.getInt("error_code");

            if(error_code ==0){
                JSONArray result = jsonObject.getJSONArray("message");
                for(int i = 0; i<result.length(); i++){

                    JSONObject jo = result.getJSONObject(i);
                    driver.setId(jo.getInt("id"));
                    driver.setF_name(jo.getString("f_name"));
                    driver.setL_name(jo.getString("l_name"));
                    driver.setAddress(jo.getString("address"));
                    driver.setContact_no(jo.getString("contact_no"));
                    driver.setGender(jo.getString("gender"));
                    driver.setNic(jo.getString("nic"));
                    driver.setDriver_id(jo.getString("driver_id"));
                    driver.setDriving_licence_no(jo.getString("driving_licence_no"));
                }

                SharedObject.CUR_USER = driver;

                if(this.rememberMe.isChecked()){
                    localDb.insertUserData(this.username,this.password);
                }
                else {
                    localDb.deleteEntry(DAConfig.LOGIN_TABLE_NAME);
                }

                fetchProgressData();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                fetchTripData();

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);

            }
            else{
                this.info.setText(jsonObject.getString("message"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /*
     * get json string of progress data
     */
    private void fetchProgressData(){
        final Context context = this;

        class ProgressDataJson extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(context,"Fetching progress data","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                System.out.println("@22222222222222222222222222222222");
                System.out.println(s);
                decodeProgressData(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> data = new HashMap<>();
                data.put("driver_id",Integer.toString(SharedObject.CUR_USER.getId()));

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(DAConfig.URL_GET_PROGRESS_DATA, data);
                return res;
            }
        }
        ProgressDataJson pdj = new ProgressDataJson();
        pdj.execute();
    }

    private void decodeProgressData(String json_string){
        JSONObject jsonObject = null;
        MyProgress progress = new MyProgress();

        try {

            jsonObject = new JSONObject(json_string);
            int error_code = jsonObject.getInt("error_code");

            if(error_code ==0){
                JSONArray millage = jsonObject.getJSONArray("millage");
                for(int i = 0; i<millage.length(); i++){

                    JSONObject jo = millage.getJSONObject(i);
                    String date  = jo.getString("year") + "/"+jo.getString("month");
                    progress.addMillage(date,jo.getString("distance"));
                }

                JSONArray cost = jsonObject.getJSONArray("cost_log");

                for(int i = 0; i<cost.length(); i++){

                    JSONObject jo = cost.getJSONObject(i);
                    String date  = jo.getString("year") + "/"+jo.getString("month");
                    progress.addFuelCost(date,jo.getString("cost"));
                }

                JSONArray longest_millage = jsonObject.getJSONArray("longest_millage");

                for(int i = 0; i<longest_millage.length(); i++){

                    JSONObject jo = longest_millage.getJSONObject(i);
                    HashMap<String ,String > ml = new HashMap<>();

                    ml.put("distance",jo.getString("distance"));
                    ml.put("vehicle",jo.getString("licence_plate"));
                    ml.put("trip",jo.getString("description"));
                    ml.put("date",jo.getString("date"));

                    progress.setLongest_millage(ml);
                }

                JSONArray fav_vehicle = jsonObject.getJSONArray("vehicles");

                for(int i = 0; i<fav_vehicle.length(); i++){

                    JSONObject jo = fav_vehicle.getJSONObject(i);
                    HashMap<String ,String > fv = new HashMap<>();

                    fv.put("used_times",jo.getString("u_times"));
                    fv.put("vehicle",jo.getString("licence_plate")+" "+jo.getString("v_class"));

                    progress.setFavourite_vehicle(fv);
                }

                JSONArray tot_cost = jsonObject.getJSONArray("fuel_cost");

                for(int i = 0; i<tot_cost.length(); i++){

                    JSONObject jo = tot_cost.getJSONObject(i);
                    HashMap<String ,String > tc = new HashMap<>();

                    tc.put("cost",jo.getString("cost"));

                    progress.setTotal_fuel_cost(tc);
                }




                SharedObject.MY_PROGRESS =progress;
            }
            else{
                this.info.setText(jsonObject.getString("message"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*
     * get json string of vehicles
     */
    private void fetchUserData(){

        final Context context = this;

        class UserDataJson extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(context,"Checking user","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                validateUser();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> data = new HashMap<>();
                data.put("user_name",username);
                data.put("password",password);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(DAConfig.URL_LOG_IN, data);
                return res;
            }
        }
        UserDataJson guj = new UserDataJson();
        guj.execute();
    }
}
