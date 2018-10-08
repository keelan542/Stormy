package com.keelanb.stormy.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.keelanb.stormy.databinding.HourlyListItemBinding;

public class HourlyAdapter extends RecyclerView.Adapter<HourlyAdapter.ViewHolder> {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
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
