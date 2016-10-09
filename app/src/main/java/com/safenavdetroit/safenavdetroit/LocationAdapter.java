package com.safenavdetroit.safenavdetroit;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.esri.core.tasks.geocode.LocatorGeocodeResult;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by ryancasler on 10/8/16
 * SafeNavDetroit
 */

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {

    private List<LocatorGeocodeResult> data;

    public LocationAdapter(List<LocatorGeocodeResult> data) {
        this.data = data;
    }

    public void updateList(List<LocatorGeocodeResult> results){
        data = results;
        notifyDataSetChanged();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_location, parent, false);

        ViewHolder vh = new ViewHolder(v);

        vh.address = (TextView) v.findViewById(R.id.address);

        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        LocatorGeocodeResult result = data.get(position);
        holder.address.setText(result.getAddress());

        holder.data = data;
    }

    @Override
    public int getItemCount() {
        if (data!=null)
            return data.size();
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView address;
        List<LocatorGeocodeResult> data;

        ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
        }

        @Override public void onClick(View view) {
            EventBus.getDefault().post(data.get(getLayoutPosition()));
        }
    }
}

