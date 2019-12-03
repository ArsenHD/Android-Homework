package com.example.user.homework.FavouritesActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.user.homework.R;
import com.example.user.homework.GHAPI.User;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FavouritesActivity extends AppCompatActivity {

    private final String dir = Environment.getExternalStorageDirectory().toString() + "/downloaded_images/";
    private ArrayList<User> favouritesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourites_list);

        Intent intent = getIntent();
        favouritesList = (ArrayList<User>)intent.getBundleExtra("bundle").getSerializable("favouritesList");


        View recyclerView = findViewById(R.id.favourites_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        TextView emptyFavouritesList = findViewById(R.id.empty_favourites_list);

        if (((RecyclerView) recyclerView).getAdapter().getItemCount() != 0) {
            emptyFavouritesList.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable("favouritesList", favouritesList);

        super.onSaveInstanceState(savedInstanceState);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new FavouritesRecyclerViewAdapter(this));
    }

    public class FavouritesRecyclerViewAdapter
            extends RecyclerView.Adapter<FavouritesRecyclerViewAdapter.ViewHolder> {

        private final FavouritesActivity mParentActivity;

        FavouritesRecyclerViewAdapter(FavouritesActivity parent) {
            mParentActivity = parent;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.favourites_item, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            viewHolder.favouriteImageName.setText(favouritesList.get(i).login);
            String imageFile = dir + favouritesList.get(i).login + ".png";
            Log.e("TAG1234", imageFile);
            Bitmap bitmap = BitmapFactory.decodeFile(imageFile);
            if (bitmap != null) {
                viewHolder.progressBar.setVisibility(View.GONE);
                viewHolder.favouriteImageView.setImageBitmap(bitmap);
            }
        }

        @Override
        public int getItemCount() {
            if (favouritesList == null)
                return 0;
            return favouritesList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final CircleImageView favouriteImageView;
            final TextView favouriteImageName;
            final ProgressBar progressBar;
            final RelativeLayout parentLayout;


            ViewHolder(View view) {
                super(view);
                favouriteImageView = view.findViewById(R.id.favourites_circle_image);
                favouriteImageName = view.findViewById(R.id.favourites_image_name);
                progressBar = view.findViewById(R.id.favourites_download_circle_image_progress_bar);
                parentLayout = view.findViewById(R.id.favourites_parent_layout);
            }
        }
    }
}
