package com.example.findfun;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import org.parceler.Parcels;

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

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle;
        TextView tvDate;
        ImageView ivPoster;
//        int radius = 30;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDate = itemView.findViewById(R.id.tvDate);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            //itemView.setOnClickListener(this);
        }

        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Event event = events.get(position);
                Intent intent = new Intent(context, EventDetailsActivity.class);
                intent.putExtra(Event.class.getSimpleName(), Parcels.wrap(event));
                context.startActivity(intent);
            }
        }

        public void bind(Event event) {
            tvTitle.setText(event.getEventName());
            Glide.with(context).load(event.posterURL).into(ivPoster);
            tvDate.setText(event.getDate());
//            tvOverview.setText(movie.getOverview());
//            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
//                imageUrl = movie.getBackDropPath();
//                Glide.with(context).load(imageUrl).placeholder(R.drawable.flicks_backdrop_placeholder).centerCrop().transform(new RoundedCorners(radius)).transform(new CircleCrop()).into(ivPoster);
//            }
//            else{
//                imageUrl = movie.getPosterPath();
//                Glide.with(context).load(imageUrl).placeholder(R.drawable.flicks_movie_placeholder).centerCrop().transform(new RoundedCorners(radius)).transform(new CircleCrop()).into(ivPoster);
//            }
        }
    }
}
