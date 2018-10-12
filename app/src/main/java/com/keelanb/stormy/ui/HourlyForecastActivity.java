package com.keelanb.stormy.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.keelanb.stormy.R;
import com.keelanb.stormy.Weather.Hour;
import com.keelanb.stormy.adapters.HourlyAdapter;
import com.keelanb.stormy.databinding.ActivityHourlyForecastBinding;

import java.util.ArrayList;
import java.util.List;

public class HourlyForecastActivity extends AppCompatActivity {

    private HourlyAdapter adapter;
    private ActivityHourlyForecastBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,
                R.layout.activity_hourly_forecast);

        adapter = new HourlyAdapter(getHourData(), this);

        binding.hourlyListItems.setAdapter(adapter);
        binding.hourlyListItems.setLayoutManager(new LinearLayoutManager(this));
    }
}
