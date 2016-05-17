package com.example.bmcsoft.fueltracker.chart;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bmcsoft.fueltracker.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class BarChartFragment extends Fragment {

    private View view ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.activity_bar_chart_fragment,null);
        drawChart();
        return view;
    }

    private void drawChart(){
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(4f, 0));
        entries.add(new BarEntry(8f, 1));
        entries.add(new BarEntry(6f, 2));
        entries.add(new BarEntry(12f, 3));
        entries.add(new BarEntry(18f, 4));
        entries.add(new BarEntry(9f, 5));

        BarDataSet dataset = new BarDataSet(entries, "# of Calls");
        /*
        *    ColorTemplate.LIBERTY_COLORS
        *    ColorTemplate.COLORFUL_COLORS
        *    ColorTemplate.JOYFUL_COLORS
        *    ColorTemplate.PASTEL_COLORS
        *    ColorTemplate.VORDIPLOM_COLORS
         */
        dataset.setColors(ColorTemplate.JOYFUL_COLORS);

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");

        BarChart chart = (BarChart)view.findViewById(R.id.barChart);

        BarData data = new BarData(labels,dataset);

        chart.setData(data);

        chart.setDescription("# of times Alice called Bob");

        chart.animateY(2000);
    }
}
