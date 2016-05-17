package com.example.bmcsoft.fueltracker.chart;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bmcsoft.fueltracker.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

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
        //defining Y axis
        ArrayList<Entry> entries = new ArrayList<Entry>();
        entries.add(new Entry(4f, 0));
        entries.add(new Entry(8f, 1));
        entries.add(new Entry(6f, 2));
        entries.add(new Entry(2f, 3));
        entries.add(new Entry(18f, 4));
        entries.add(new Entry(9f, 5));

        LineDataSet dataset = new LineDataSet(entries,"# of calls");
        //colors
        //dataset.setDrawCubic(true);
        dataset.setDrawFilled(true);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);



        //defining X axis
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");

        //defining data
        LineData data = new LineData(labels,dataset);

        LineChart chart = (LineChart)view.findViewById(R.id.lineChart);
        chart.setData(data);

        chart.setDescription("Description");
        chart.animateY(2000);
    }
}
