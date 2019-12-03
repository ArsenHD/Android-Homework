package com.example.user.homework.ItemListActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.example.user.homework.FavouritesActivity.FavouritesActivity;
import com.example.user.homework.ItemDetailActivity.ItemDetailActivity;
import com.example.user.homework.ItemDetailActivity.ItemDetailFragment;
import com.example.user.homework.R;
import com.example.user.homework.GHAPI.User;

import java.util.ArrayList;
import java.util.List;

public class ItemListActivity extends AppCompatActivity implements ItemListContractView {

    private UserAdapter userAdapter;

    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        init(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable("model", presenter.getModel());
        super.onSaveInstanceState(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        findViewById(R.id.favourites_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.showFavourites();
            }
        });

        findViewById(R.id.search_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.searchUser();
            }
        });

        //if (findViewById(R.id.item_detail_container) != null) {
        //    mTwoPane = true;
        //}

        Model usersModel = new Model();
        if (savedInstanceState != null) {
            usersModel = (Model) savedInstanceState.getSerializable("model");
        }

        userAdapter = new UserAdapter(this, usersModel);

        presenter = new Presenter(usersModel);
        presenter.attachView(this);

        RecyclerView recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        recyclerView.setAdapter(userAdapter);

        presenter.viewIsReady();
    }

    public void showSearchResult(User foundUser) {
        Context context = this;
        Intent intent = new Intent(context, ItemDetailActivity.class);
        intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, foundUser.login);
        intent.putExtra(ItemDetailFragment.ARG_ITEM_URL, foundUser.userpic);
        context.startActivity(intent);
    }

    public void showUserNotFoundToast() {
        Toast.makeText(ItemListActivity.this, "User not found", Toast.LENGTH_SHORT).show();
    }


    public void showFailToast() {
        Toast.makeText(ItemListActivity.this, "An error occured during networking", Toast.LENGTH_SHORT).show();
    }

    public void showFavourites(ArrayList<User> favouritesList) {
        Context context = this;
        Intent intent = new Intent(context, FavouritesActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("favouritesList", favouritesList);
        intent.putExtra("bundle", bundle);
        context.startActivity(intent);
    }

    public String getSearchQuery() {
        TextView query = findViewById(R.id.search_input);
        return query.getText().toString();
    }

    public void showUsers(List<User> users) {
        userAdapter.setData(users);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
