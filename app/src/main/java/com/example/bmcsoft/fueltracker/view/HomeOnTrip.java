package com.example.bmcsoft.fueltracker.view;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.bmcsoft.fueltracker.R;
import com.example.bmcsoft.fueltracker.dataaccess.LocalDBHelper;
import com.example.bmcsoft.fueltracker.objects.SharedObject;

public class HomeOnTrip extends Fragment implements View.OnClickListener{

    private View view;
    private TextView date;
    private TextView start_time;
    private TextView vehicle;
    private TextView description;
    private Button add_fill_up;
    private Button end_trip;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.activity_home_on_trip,null);

        this.date = (TextView)view.findViewById(R.id.date_home_on_trip);
        this.start_time =(TextView)view.findViewById(R.id.start_time_home_on_trip);
        this.vehicle =(TextView)view.findViewById(R.id.vehicle_home_on_trip);
        this.description =(TextView)view.findViewById(R.id.description_home_on_trip);
        this.add_fill_up = (Button)view.findViewById(R.id.add_fillup_home_on_trip);
        this.end_trip = (Button)view.findViewById(R.id.end_trip_home_on_trip);

        this.add_fill_up.setOnClickListener(this);
        this.end_trip.setOnClickListener(this);

        setHomeValues();
        return view;
    }


    private void setHomeValues(){
        this.date.setText(SharedObject.CUR_TRIP.getDate());
        this.start_time.setText(SharedObject.CUR_TRIP.getStart_time());
        this.vehicle.setText(SharedObject.CUR_SELECTED_VEHICLE.getVclass()+ " " + SharedObject.CUR_SELECTED_VEHICLE.getLplate());
        this.description.setText(SharedObject.CUR_TRIP.getDescription());
    }


    @Override
    public void onClick(View view) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if(view == add_fill_up){
            transaction.replace(R.id.container_view,new AddFillUp());
            transaction.commit();
        }
        else if(view == end_trip){

        }
    }

    private void uploadEndTripData(){

    }
}
