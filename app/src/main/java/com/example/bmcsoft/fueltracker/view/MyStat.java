package com.example.bmcsoft.fueltracker.view;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bmcsoft.fueltracker.R;
import com.example.bmcsoft.fueltracker.objects.SharedObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MyStat extends Fragment {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.activity_my_stat,null);

        setValues();

        return view;
    }

    private void setValues(){
        setTotalCost();
        setLongestDistance();
        setFavVehicle();
    }

    private void setLongestDistance(){

        ArrayList<HashMap<String,String>> millage = SharedObject.MY_PROGRESS.getLongest_millage();

        TextView distance_1 = (TextView) view.findViewById(R.id.distance_1_my_stat);
        TextView vehicle_1 = (TextView) view.findViewById(R.id.vehicle_1_my_stat);
        TextView trip_1 = (TextView) view.findViewById(R.id.trip_1_my_stat);
        TextView date_1 = (TextView) view.findViewById(R.id.date_1_my_stat);

        HashMap<String,String>  ml1 = millage.get(0);
        distance_1.setText(ml1.get("distance")+" km");
        vehicle_1.setText(ml1.get("vehicle"));
        trip_1.setText(ml1.get("trip"));
        date_1.setText(ml1.get("date"));

        TextView distance_2 = (TextView) view.findViewById(R.id.distance_2_my_stat);
        TextView vehicle_2 = (TextView) view.findViewById(R.id.vehicle_2_my_stat);
        TextView trip_2 = (TextView) view.findViewById(R.id.trip_2_my_stat);
        TextView date_2 = (TextView) view.findViewById(R.id.date_2_my_stat);

        HashMap<String,String>  ml2 = millage.get(1);
        distance_2.setText(ml2.get("distance")+" km");
        vehicle_2.setText(ml2.get("vehicle"));
        trip_2.setText(ml2.get("trip"));
        date_2.setText(ml2.get("date"));

     }

    private void setTotalCost(){
        TextView cost = (TextView) view.findViewById(R.id.total_cost_my_stat);
        cost.setText("Rs. " +SharedObject.MY_PROGRESS.getTotal_fuel_cost().get(0).get("cost") +"/=");
    }

    private void setFavVehicle(){
        ArrayList<HashMap<String,String>> fv = SharedObject.MY_PROGRESS.getFavourite_vehicle();

        TextView vh_1 = (TextView) view.findViewById(R.id.fv_vehicle_1_my_stat);
        TextView ut_1 = (TextView) view.findViewById(R.id.fv_ut_1_my_stat);

        HashMap<String,String>  v1 = fv.get(0);
        vh_1.setText(v1.get("vehicle"));
        ut_1.setText(v1.get("used_times"));

        TextView vh_2 = (TextView) view.findViewById(R.id.fv_vehicle_2_my_stat);
        TextView ut_2 = (TextView) view.findViewById(R.id.fv_ut_2_my_stat);

        HashMap<String,String>  v2 = fv.get(1);
        vh_2.setText(v2.get("vehicle"));
        ut_2.setText(v2.get("used_times"));

        TextView vh_3 = (TextView) view.findViewById(R.id.fv_vehicle_3_my_stat);
        TextView ut_3 = (TextView) view.findViewById(R.id.fv_ut_3_my_stat);

        HashMap<String,String>  v3 = fv.get(2);
        vh_3.setText(v3.get("vehicle"));
        ut_3.setText(v3.get("used_times"));

    }
}
