package com.example.findfun;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder>{

    Context context;
    List<Event> events;

    public EventAdapter(Context context, List<Event> movies) {
        this.context = context;
        this.events = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("EventAdapter", "OnCreatViewHolder");
        View eventView = LayoutInflater.from(context).inflate(R.layout.item_event, parent, false);
        return new ViewHolder(eventView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("EventAdpapter", "OnBindViewHolder"+ position);
        Event event = events.get(position);
        holder.bind(event);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void clear() {
        events.clear();
        notifyDataSetChanged();
    }

    public void filterList(ArrayList<Event> filteredList) {
        events = filteredList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle;
        TextView tvDate;
        ImageView ivPoster;
        TextView tvLocation;
        TextView tvCity;
        TextView tvState;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDate = itemView.findViewById(R.id.tvDate);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvState = itemView.findViewById(R.id.tvState);
            tvCity = itemView.findViewById(R.id.tvCity);
        }

        public void bind(Event event) {
            tvTitle.setText(event.getEventName());
            Glide.with(context).load(event.posterURL).into(ivPoster);
            tvDate.setText(event.getDate());
            tvLocation.setText(event.getLocation());
            tvState.setText(event.getState());
            tvCity.setText(event.getCity());
            tvLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String location = event.getLocation().toString();
                    String city = event.getCity().toString();
                    DisplayTrack(location, city);
                }
            });
        }
        private void DisplayTrack(String location, String city) {
            try {
                Uri uri = Uri.parse("https://www.google.co.in/maps/dir//"+ location + "," + city);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setPackage("com.google.android.apps.maps");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        }
    }
}
