package com.example.user.homework.ItemDetailActivity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.user.homework.R;

public class ItemDetailFragment extends Fragment {
    public static final String ARG_ITEM_ID = "item_id";
    public static final String ARG_ITEM_URL = "item_url";

    private ImageView imageView;
    private ProgressBar progressBar;

    private String imageName;
    private String imageUrl;

    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            imageName = getArguments().getString(ARG_ITEM_ID);
            imageUrl = getArguments().getString(ARG_ITEM_URL);
            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(imageName);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);

        if (imageName != null) {
            imageView = rootView.findViewById(R.id.high_res_img);
            progressBar = rootView.findViewById(R.id.download_image_progress_bar);

            String imageFile = Environment.getExternalStorageDirectory().toString() + "/downloaded_images/" + imageName + ".png";
            Bitmap bitmap = BitmapFactory.decodeFile(imageFile);
            if (bitmap != null) {
                progressBar.setVisibility(View.GONE);
                imageView.setImageBitmap(bitmap);
                ((TextView) rootView.findViewById(R.id.high_res_image_details)).setText("Some details");
            }
        }

        return rootView;
    }
}
