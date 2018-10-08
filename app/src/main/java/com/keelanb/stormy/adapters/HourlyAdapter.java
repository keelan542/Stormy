package com.keelanb.stormy.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.keelanb.stormy.R;
import com.keelanb.stormy.Weather.Hour;
import com.keelanb.stormy.databinding.HourlyListItemBinding;

import java.util.List;

public class HourlyAdapter extends RecyclerView.Adapter<HourlyAdapter.ViewHolder> {

    private List<Hour> hours;
    private Context context;

    public HourlyAdapter(List<Hour> hours, Context context) {
        this.hours = hours;
        this.context = context;
    }

    @Override
    public HourlyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        HourlyListItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                R.layout.hourly_list_item,
                        parent,
                        false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Hour hour = hours.get(position);
        holder.hourListItemBinding.setHour(hour);
    }

    @Override
    public int getItemCount() {
        return hours.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //Binding variables
        public HourlyListItemBinding hourListItemBinding;

        //Constructor to do view lookups for each subview
        public ViewHolder(HourlyListItemBinding hourlyLayoutBinding) {
            super(hourlyLayoutBinding.getRoot());

            hourListItemBinding = hourlyLayoutBinding;
        }
    }
}
