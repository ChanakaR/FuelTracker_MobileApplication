package com.example.bmcsoft.fueltracker.view;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bmcsoft.fueltracker.R;
import com.example.bmcsoft.fueltracker.dataaccess.DAConfig;
import com.example.bmcsoft.fueltracker.dataaccess.RequestHandler;
import com.example.bmcsoft.fueltracker.objects.SharedObject;
import com.example.bmcsoft.fueltracker.objects.Vehicle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment implements View.OnClickListener{

    private View view;
    private Button btnAddFillUp;
    private String JSON_STRING;
    private LinearLayout btnStartTrip;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.activity_home,null);

        getVehicleJSON();

        btnAddFillUp = (Button) view.findViewById(R.id.addFillUp);
        btnAddFillUp.setOnClickListener(this);
        btnStartTrip = (LinearLayout) view.findViewById(R.id.home_start_trip);
        btnStartTrip.setOnClickListener(this);
        return view;
    }



    /*
     * set vehicle list in Spinner
     */
    private void loadVehicleList(){
        Spinner vehicle_spinner = (Spinner) view.findViewById(R.id.vehicle_list);
        List<String> vehicle_spinner_list = new ArrayList<>();

        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(JSON_STRING);
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
                JSON_STRING = s;
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

    @Override
    public void onClick(View view) {
        if (view ==  btnAddFillUp){
            showFillUpForm();
        }
        else if(view == btnStartTrip){
            showStartTrip();
        }
    }

    private void showFillUpForm(){
        FragmentManager mFragmentManager;
        FragmentTransaction mFragmentTransaction;
        mFragmentManager = getFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.container_view,new AddFillUp());
        mFragmentTransaction.commit();
    }

    public void showStartTrip(){
        FragmentManager mFragmentManager;
        FragmentTransaction mFragmentTransaction;
        mFragmentManager = getFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.container_view,new StartTrip());
        mFragmentTransaction.commit();
    }

    public void showMyProgress(){

    }

    public void showAvailableVehicles(){

    }

    public void showMyProfile(){

    }


}
