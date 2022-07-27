package com.example.findfun;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

    //List<Event> events;
    MyDatabase eventDB;
    private Context context;
    private ArrayList event_name, event_image, event_date, event_venue, event_city, event_state;

    public EventAdapter(Context context, ArrayList event_name, ArrayList event_image, ArrayList event_date, ArrayList event_venue, ArrayList event_city, ArrayList event_state) {
        this.context = context;
        this.event_name = event_name;
        this.event_image = event_image;
        this.event_date = event_date;
        this.event_venue = event_venue;
        this.event_city = event_city;
        this.event_state = event_state;
        //this.events = events;
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

        holder.tvTitle.setText(String.valueOf(event_name.get(position)));
        Glide.with(context).load(event_image.get(position).toString()).into(holder.ivPoster);
        holder.tvDate.setText(String.valueOf(event_date.get(position)));
        holder.tvLocation.setText(String.valueOf(event_venue.get(position)));
        holder.tvCity.setText(String.valueOf(event_city.get(position)));
        holder.tvState.setText(String.valueOf(event_state.get(position)));
        //Log.d("EventAdpapter", "OnBindViewHolder"+ position);
        //Event event = events.get(position);
        //holder.bind(event);
    }




    @Override
    public int getItemCount() {
        return event_name.size();
        //return events.size();
    }

//    @SuppressLint("NotifyDataSetChanged")
//    public void clear() {
//        events.clear();
//        notifyDataSetChanged();
//    }
//
//    public void filterList(ArrayList<Event> filteredList) {
//        events = filteredList;
//        notifyDataSetChanged();
//    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle;
        TextView tvDate;
        ImageView ivPoster;
        TextView tvLocation;
        TextView tvCity;
        TextView tvState;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eventDB = new MyDatabase(context);
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
