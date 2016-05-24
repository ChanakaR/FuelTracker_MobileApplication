package com.example.bmcsoft.fueltracker.view;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bmcsoft.fueltracker.R;
import com.example.bmcsoft.fueltracker.dataaccess.DAConfig;
import com.example.bmcsoft.fueltracker.dataaccess.RequestHandler;
import com.example.bmcsoft.fueltracker.objects.SharedObject;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class AddFillUp extends Fragment implements View.OnClickListener{
    private View view;
    private Button btnAdd;
    private EditText unit_price;
    private EditText amount;
    private String date;
    private EditText total_price;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.activity_add_fill_up,null);

        TextView txt_vehicle_name = (TextView)view.findViewById(R.id.text_vehicle_name);
        TextView txt_vehicle_lplate =(TextView)view.findViewById(R.id.text_vehicle_lplate);
        TextView txt_vehicle_ftype = (TextView)view.findViewById(R.id.text_vehicle_ftype);
        //TextView txt_date = (TextView)view.findViewById(R.id.text_date);

        String v_name =SharedObject.cur_selected_vehicle.getVclass()+"-"+SharedObject.cur_selected_vehicle.getMake()+"-"+SharedObject.cur_selected_vehicle.getModel();
        txt_vehicle_name.setText(v_name);
        txt_vehicle_lplate.setText(SharedObject.cur_selected_vehicle.getLplate());
        txt_vehicle_ftype.setText(SharedObject.cur_selected_vehicle.getFuelType());

        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        //txt_date.setText(date);

        btnAdd = (Button)view.findViewById(R.id.btn_add_add_fillup);
        btnAdd.setOnClickListener(this);
        return view;
    }

    private void addFillUpDetail(){

        final String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());;
        final String unit_price= ((EditText)view.findViewById(R.id.input_unit_price)).getText().toString();
        final String fuel_type = ((TextView)view.findViewById(R.id.text_vehicle_ftype)).getText().toString();
        final String amount = ((EditText)view.findViewById(R.id.input_fuel_amount)).getText().toString();
        final String total_price = ((EditText)view.findViewById(R.id.input_total_price)).getText().toString();
        final String odo_meter = ((EditText)view.findViewById(R.id.input_odo_meter)).getText().toString();
        final String vehicle_id =  (SharedObject.cur_selected_vehicle.getId());
        final String driver_id = "4";

        class AddFUp extends AsyncTask<Void,Void,String> {


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
                Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put("fuel_type",fuel_type);
                params.put("amount",amount);
                params.put("unit_price",unit_price);
                params.put("total_price",total_price);
                params.put("date",date);
                params.put("vehicle_id",vehicle_id);
                params.put("driver_id",driver_id);
                params.put("odo_meter",odo_meter);


                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(DAConfig.URL_ADD_FILL_UP, params);
                return res;
            }
        }

        AddFUp afu = new AddFUp();
        afu.execute();
    }

    @Override
    public void onClick(View view) {
        if(view == btnAdd){
           // Toast.makeText(getActivity(),"awaaaa",Toast.LENGTH_LONG).show();
            addFillUpDetail();
        }
    }
}
