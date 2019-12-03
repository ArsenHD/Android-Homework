package com.example.user.homework.ItemListActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.user.homework.ItemDetailActivity.ItemDetailActivity;
import com.example.user.homework.ItemDetailActivity.ItemDetailFragment;
import com.example.user.homework.R;
import com.example.user.homework.GHAPI.User;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
//import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {
    private ItemListActivity mParentActivity;
    List<User> data = new ArrayList<>();
    private Model model;

    public UserAdapter(ItemListActivity parentActivity, Model model) {
        mParentActivity = parentActivity;
        this.model = model;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_list_content, viewGroup, false);
        return new UserAdapter.UserHolder(view);
    }

    private void setImage(UserHolder userHolder, Bitmap bitmap, String imageName, int i) {
        userHolder.progressBar.setVisibility(View.GONE);
        userHolder.circleImageView.setImageBitmap(bitmap);
        userHolder.circleImageViewName.setText(imageName);
        userHolder.itemView.setTag(model.getUsers().get(i));
        userHolder.itemView.setOnClickListener(mOnClickListener);
    }

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            User user = (User) view.getTag();
            Context context = view.getContext();
            Intent intent = new Intent(context, ItemDetailActivity.class);
            intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, user.login);
            intent.putExtra(ItemDetailFragment.ARG_ITEM_URL, user.userpic);
            context.startActivity(intent);
        }
    };


    @Override
    public void onBindViewHolder(@NonNull final UserHolder userHolder, final int i) {
        User user = data.get(i);

        final String imageUrl = user.userpic;
        final String login = user.login;
        userHolder.circleImageViewName.setText(login);

        final String pathToImage = Environment.getExternalStorageDirectory().toString()
                + "/downloaded_images/" + login + ".png";

        Bitmap bitmap = BitmapFactory.decodeFile(pathToImage);
        if (bitmap != null) {
            setImage(userHolder, bitmap, login, i);
            return;
        }

        Glide.with(mParentActivity)
                .asBitmap()
                .load(imageUrl)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        setImage(userHolder, resource, login, i);

                        FileOutputStream out = null;
                        try {
                            out = new FileOutputStream(new File(pathToImage));
                            resource.compress(Bitmap.CompressFormat.PNG, 100, out);
                            out.flush();
                            out.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public int getItemCount() {
        if (model.getUsers() == null)
            return 0;
        return model.getUsers().size();
    }

    public void setData(List<User> users) {
        data.clear();
        data.addAll(users);
        notifyDataSetChanged();
    }

    class UserHolder extends RecyclerView.ViewHolder {
        final CircleImageView circleImageView;
        final TextView circleImageViewName;
        final ProgressBar progressBar;
        final Button likeButton;
        final ConstraintLayout parentLayout;

        public UserHolder(View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.circle_image);
            circleImageViewName = itemView.findViewById(R.id.image_name);
            progressBar = itemView.findViewById(R.id.download_circle_image_progress_bar);
            likeButton = itemView.findViewById(R.id.add_to_favourites);
            likeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String login = circleImageViewName.getText().toString();
                    User currentUser = null;
                    for (User user : model.getUsers()) {
                        if (user.login.equals(login)) {
                            currentUser = user;
                        }
                    }

                    ArrayList<String> logins = new ArrayList<>();
                    for (User user : model.getFavouritesList()) {
                        logins.add(user.login);
                    }

                    if (!logins.contains(login)) {
                        model.getFavouritesList().add(currentUser);
                    }
                }
            });

            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
