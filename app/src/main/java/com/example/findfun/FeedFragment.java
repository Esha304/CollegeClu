package com.example.findfun;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findfun.Post;
import com.example.findfun.PostsAdapter;
import com.example.findfun.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.Parse;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class FeedFragment extends Fragment {

    public static final String TAG = "FeedFragment";
    protected RecyclerView rvPosts;
    protected PostsAdapter adapter;
    protected List<Post> allPosts;
    SwipeRefreshLayout swipeContainer;

    public FeedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onViewCreated(view, savedInstanceState);
        rvPosts = view.findViewById(R.id.rvPosts);
        allPosts = new ArrayList<>();
        adapter = new PostsAdapter(getContext(), allPosts);
        rvPosts.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvPosts.setLayoutManager(linearLayoutManager);

        queryPosts();

        //SwipeRefresh
        swipeContainer = view.findViewById(R.id.swipeContainer2);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i(TAG,"fetching new data");
                adapter.clear();
                allPosts.clear();
                queryPosts();
                swipeContainer.setRefreshing(false);
            }
        });
    }

    protected void queryPosts () {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.setLimit(20);
        //query.setSkip(skipAmount);
        query.addDescendingOrder(Post.KEY_CREATED_KEY);
        query.findInBackground(new FindCallback<Post>() {
            //@SuppressLint("NotifyDataSetChanged")
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Unable to retrieve posts");
                    return;
                }
                for (Post post : posts) {
                    Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());
                }
                allPosts.addAll(posts);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        inflater.inflate(R.menu.menu_sort, menu);
        menu.findItem(R.id.DateRO).setVisible(false);
        menu.findItem(R.id.DateOR).setVisible(false);
//        menu.findItem(R.id.Ascen).setVisible(false);
//        menu.findItem(R.id.Descen).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menulogoutbtn) {
            ParseUser.logOut();
            ParseUser currentUser = ParseUser.getCurrentUser();
            Intent i = new Intent(getContext(), LoginActivity.class);
            startActivity(i);
            return true;
        }
        if (item.getItemId() == R.id.menubackbtn) {
            Intent i = new Intent(getContext(), CityTypeActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}