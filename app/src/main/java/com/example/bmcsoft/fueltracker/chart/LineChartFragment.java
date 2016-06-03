package com.example.bmcsoft.fueltracker.chart;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bmcsoft.fueltracker.R;
import com.example.bmcsoft.fueltracker.objects.SharedObject;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;

public class LineChartFragment extends Fragment {

    private View view ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.activity_line_chart_fragment,null);
        drawChart();
        return view;
    }

    private void drawChart(){
        HashMap<String,String> data_log = SharedObject.MY_PROGRESS.getMillageData();

        //defining Y axis
        ArrayList<Entry> entries = new ArrayList<Entry>();
        ArrayList<String> labels = new ArrayList<String>();


        int pos=0;
        for(String key : data_log.keySet()){
            entries.add(new Entry(Float.parseFloat(data_log.get(key)),pos));
            labels.add(key);
            pos++;
        }


        LineDataSet dataset = new LineDataSet(entries,"travelling distance");
        //colors
        //dataset.setDrawCubic(true);
        dataset.setDrawFilled(true);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);

        //defining data
        LineData data = new LineData(labels,dataset);

        LineChart chart = (LineChart)view.findViewById(R.id.lineChart);
        chart.setData(data);

        chart.setDescription("graph - millage vs month ");
        chart.animateY(2000);
    }
}
