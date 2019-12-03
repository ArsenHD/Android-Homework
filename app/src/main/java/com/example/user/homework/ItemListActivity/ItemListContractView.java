package com.example.user.homework.ItemListActivity;

import com.example.user.homework.GHAPI.User;

import java.util.ArrayList;
import java.util.List;

public interface ItemListContractView {
    String getSearchQuery();
    void showUsers(List<User> users);
    void showFailToast();
    void showFavourites(ArrayList<User> favouritesList);
    void showSearchResult(User foundUser);
    void showUserNotFoundToast();
}
