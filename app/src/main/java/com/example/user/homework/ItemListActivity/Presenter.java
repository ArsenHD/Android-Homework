package com.example.user.homework.ItemListActivity;

import com.example.user.homework.GHAPI.User;

import java.util.ArrayList;
import java.util.List;

public class Presenter {
    private ItemListContractView view;
    private final Model model;

    public Presenter(Model model) {
        this.model = model;
    }

    public void attachView(ItemListContractView activity) {
        view = activity;
    }

    public void detachView() {
        view = null;
    }

    public void viewIsReady() {
        loadUsers();
    }

    public Model getModel() {
        return model;
    }

    public void loadUsers() {
        model.loadUsers(new Model.LoadUsersCallback() {
            @Override
            public void onLoad(List<User> users) {
                view.showUsers(users);
            }

            @Override
            public void onFail(Throwable t) {
                view.showFailToast();
            }
        });
    }

    public void showFavourites() {
        ArrayList<User> favouritesList = model.getFavouritesList();
        view.showFavourites(favouritesList);
    }

    public void searchUser() {
        String query = view.getSearchQuery();
        model.searchUser(query, new Model.SearchUsersCallback() {
            @Override
            public void onFound(User foundUser) {
                view.showSearchResult(foundUser);
            }

            @Override
            public void onFail() {
                view.showUserNotFoundToast();
            }
        });
    }
}
