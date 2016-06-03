package com.example.bmcsoft.fueltracker.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.bmcsoft.fueltracker.R;
import com.example.bmcsoft.fueltracker.dataaccess.DAConfig;
import com.example.bmcsoft.fueltracker.dataaccess.RequestHandler;
import com.example.bmcsoft.fueltracker.objects.SharedObject;

import java.util.HashMap;

public class MyTrips extends Fragment {

    private  View view;
    private String JSON_STRING;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.activity_my_trips,null);
        setTrips();
        return view;
    }

    private void setTrips(){

        LinearLayout lut = (LinearLayout) view.findViewById(R.id.view_my_trip_list);
        Button trip = new Button(getActivity());
        LinearLayout.LayoutParams l_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        l_params.setMargins(0,5,0,0);
        trip.setLayoutParams(l_params);
        trip.setBackgroundColor(trip.getContext().getResources().getColor(R.color.trip_back));
        trip.setTextSize(13);
        trip.setText("sssssssssssss");

        Button trip1 = new Button(getActivity());
        LinearLayout.LayoutParams l_params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        l_params1.setMargins(0,5,0,0);
        trip1.setLayoutParams(l_params1);
        trip1.setBackgroundColor(trip1.getContext().getResources().getColor(R.color.trip_back));
        trip1.setTextSize(13);
        trip1.setText("BUHUHAUHAHAHAA");

        lut.addView(trip);
        lut.addView(trip1);

    }

    private void getDataJson(){
        final Context context = getActivity();

        class TripDataJson extends AsyncTask<Void,Void,String> {

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
                decodeJson(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> data = new HashMap<>();
                data.put("driver_id", Integer.toString(SharedObject.CUR_USER.getId()));

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(DAConfig.URL_GET_MY_TRIP_LIST, data);
                return res;
            }
        }
        TripDataJson tdj = new TripDataJson();
        tdj.execute();
    }

    private void decodeJson(String json_string){

    }
}
