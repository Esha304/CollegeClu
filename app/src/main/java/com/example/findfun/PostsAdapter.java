package com.example.findfun;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder>{
    private static final String TAG = "PostsAdapter";
    private Context context;
    private List<Post> posts;

    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvUsername;
        private ImageView tvImage;
        private ImageButton ibLikes;
        private TextView tvLikes;
        private TextView tvDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvImage = itemView.findViewById(R.id.tvImage);
            ibLikes = itemView.findViewById(R.id.ibPostLikes);
            tvLikes = itemView.findViewById(R.id.tvPostLikes);
            tvDescription = itemView.findViewById(R.id.tvDescription);
        }

        public void bind(Post post) {
            tvDescription.setText(post.getDescription());
            tvUsername.setText(post.getUser().getUsername());
            ParseFile image = post.getImage();
            if (image != null) {
                Glide.with(context).load(image.getUrl()).into(tvImage);
            }
            tvLikes.setText(post.likeCountDisplayText());

            if (post.getLikedBy().contains(ParseUser.getCurrentUser().getObjectId())) {
                ibLikes.setColorFilter(Color.RED);
            } else { ibLikes.setColorFilter(Color.DKGRAY); }

            ibLikes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    List<String> likedBy = post.getLikedBy();
                    if (!likedBy.contains(ParseUser.getCurrentUser().getObjectId())) {
                        likedBy.add(ParseUser.getCurrentUser().getObjectId());
                        post.setLikedBy(likedBy);
                        ibLikes.setColorFilter(Color.RED);
                    }
                    else {
                        likedBy.remove(ParseUser.getCurrentUser().getObjectId());
                        post.setLikedBy(likedBy);
                        ibLikes.setColorFilter(Color.DKGRAY);
                    }
                    post.saveInBackground();
                    tvLikes.setText(post.likeCountDisplayText());
                }
            });
        }
    }
}
