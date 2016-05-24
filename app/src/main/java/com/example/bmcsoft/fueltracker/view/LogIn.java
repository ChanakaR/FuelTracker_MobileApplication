package com.example.bmcsoft.fueltracker.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bmcsoft.fueltracker.MainActivity;
import com.example.bmcsoft.fueltracker.R;
import com.example.bmcsoft.fueltracker.dataaccess.DAConfig;
import com.example.bmcsoft.fueltracker.dataaccess.RequestHandler;
import com.example.bmcsoft.fueltracker.objects.Driver;
import com.example.bmcsoft.fueltracker.objects.SharedObject;
import com.example.bmcsoft.fueltracker.objects.Vehicle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LogIn extends AppCompatActivity {

    private TextView u_name;
    private TextView pwd;
    private TextView info;
    private String JSON_STRING;
    private String username;
    private String password;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        this.u_name = (TextView) findViewById(R.id.user_name_log_in);
        this.pwd = (TextView) findViewById(R.id.password_log_in);
        this.info = (TextView) findViewById(R.id.info_message_log_in);

    }

    private void logInto(){
        username = u_name.getText().toString();
        password = pwd.getText().toString();

        this.info.setText("All fields are required.");
        getUserJSON();

    }

    public void goMainMenu(View view){
        this.view = view;
        logInto();
    }

    /*
     * set vehicle list in Spinner
     */
    private void checkUser(){

        System.out.println("dsdsdsds");

        JSONObject jsonObject = null;
        Driver driver= new Driver();

        try {
            System.out.println("onna awaaaaaaaaaa1111");
            System.out.println(JSON_STRING);
            jsonObject = new JSONObject(JSON_STRING);
            //JSONArray er_code = jsonObject.getJSONArray("error_code");

            System.out.println("onna awaaaaaaaaaa");
            System.out.println("error code: "+jsonObject.getInt("error_code"));

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

            SharedObject.cur_user = driver;

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    /*
     * get json string of vehicles
     */
    private void getUserJSON(){

        Context context = this;

        class UserDataJson extends AsyncTask<Void,Void,String> {

            //ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //loading = ProgressDialog.show((Activity)getApplicationContext(),"Checking user","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //loading.dismiss();
                JSON_STRING = s;
                checkUser();
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
