package com.example.bmcsoft.fueltracker.view;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bmcsoft.fueltracker.R;
import com.example.bmcsoft.fueltracker.dataaccess.DAConfig;
import com.example.bmcsoft.fueltracker.dataaccess.LocalDBHelper;
import com.example.bmcsoft.fueltracker.dataaccess.RequestHandler;
import com.example.bmcsoft.fueltracker.objects.SharedObject;
import com.example.bmcsoft.fueltracker.objects.Trip;
import com.example.bmcsoft.fueltracker.objects.Vehicle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StartTrip extends Fragment implements View.OnClickListener {

    private View view;
    private String JSON_STRING_VEHICLE = null;
    private String JSON_STRING_TRIP = null;
    private Button btn_start;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.activity_start_trip,null);
        btn_start = (Button) view.findViewById(R.id.start_trip_s_trip);
        btn_start.setOnClickListener(this);
        getVehicleJSON();

        return view;
    }



    /*
     * set vehicle list in Spinner
     */
    private void loadVehicleList(){
        Spinner vehicle_spinner = (Spinner) view.findViewById(R.id.vehicle_list_s_trip);
        List<String> vehicle_spinner_list = new ArrayList<>();

        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(JSON_STRING_VEHICLE);
            JSONArray result = jsonObject.getJSONArray("message");

            for(int i = 0; i<result.length(); i++){

                JSONObject jo = result.getJSONObject(i);
                Vehicle v= new Vehicle(jo.getString("id"),jo.getString("licence_plate"),jo.getString("v_class"),jo.getString("make"),jo.getString("model"),jo.getString("year"),jo.getString("fuel_type"));
                String l_plate = jo.getString("licence_plate");
                vehicle_spinner_list.add(l_plate);
                SharedObject.VEHICLE_LIST.add(v);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item, vehicle_spinner_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        vehicle_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos,
                                       long id) {
                for(Vehicle v : SharedObject.VEHICLE_LIST){
                    if(v.getLplate()==parent.getItemAtPosition(pos).toString()){
                        SharedObject.CUR_SELECTED_VEHICLE = v;
                    }
                }

                Toast.makeText(parent.getContext(),
                        "On Item Select : \n" + SharedObject.CUR_SELECTED_VEHICLE.getVclass(),
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
        vehicle_spinner.setAdapter(adapter);
    }


    /*
     * get json string of vehicles
     */
    private void getVehicleJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(),"Fetching Data","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING_VEHICLE = s;
                loadVehicleList();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(DAConfig.URL_GET_ALL_VEHICLE);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void uploadData(){
        final String driver_id = Integer.toString(SharedObject.CUR_USER.getId());
        final String vehicle_id = SharedObject.CUR_SELECTED_VEHICLE.getId();
        final String odometer = ((EditText)view.findViewById(R.id.om_count_s_trip)).getText().toString();
        final String description = ((EditText)view.findViewById(R.id.description_s_trip)).getText().toString();

        class UploadTripData extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(),"Adding...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING_TRIP =s;
                Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
                checkSuccessful();
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> params = new HashMap<>();
                params.put("vehicle_id",vehicle_id);
                params.put("driver_id",driver_id);
                params.put("start_odometer",odometer);
                params.put("description",description);
                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(DAConfig.URL_ADD_TRIP, params);
                return res;
            }
        }

        UploadTripData utd = new UploadTripData();
        utd.execute();
        //this.checkSuccessful();
    }

    @Override
    public void onClick(View view) {
        if(view == btn_start){
            this.uploadData();
        }
    }

    public void checkSuccessful(){

        if(JSON_STRING_TRIP != null){
            JSONObject jsonObject = null;
            Trip trip = new Trip();

            try {
                jsonObject = new JSONObject(JSON_STRING_TRIP);
                int error_code = jsonObject.getInt("error_code");

                if(error_code ==0){
                    JSONObject jo = jsonObject.getJSONObject("message");

                    trip.setId(jo.getInt("id"));
                    trip.setStart_time(jo.getString("start_time"));
                    trip.setDate(jo.getString("date"));
                    trip.setDescription(jo.getString("description"));
                    trip.setStart_odometer(jo.getString("start_odometer"));

                    SharedObject.CUR_TRIP = trip;

                    FragmentManager mFragmentManager = getFragmentManager();
                    FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
                    mFragmentTransaction.replace(R.id.container_view,new HomeOnTrip());
                    mFragmentTransaction.commit();

                }
                else{
                    Toast.makeText(getActivity(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }



}
