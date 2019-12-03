package com.example.user.homework.ItemListActivity;

import com.example.user.homework.App;
import com.example.user.homework.GHAPI.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class Model implements Serializable {

    private ArrayList<User> users;
    private ArrayList<User> favouritesList = new ArrayList<>();
    private Disposable rxCall = null;

    public void loadUsers(final LoadUsersCallback callback) {
        if (users != null)
            users.clear();
        users = new ArrayList<>();

        Observable<List<User>> retrofitContributors;
        retrofitContributors = App.getApi().getContributors("retrofit", "e09656fa683222cba46c47bad508d0107cd97783");

        retrofitContributors
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<User>>() {
                               @Override
                               public void onSubscribe(Disposable d) {

                               }

                               @Override
                               public void onNext(List<User> users) {
                                   Model.this.users.addAll(users);
                                   callback.onLoad(users);
                               }

                               @Override
                               public void onError(Throwable e) {
                                   callback.onFail(e);
                               }

                               @Override
                               public void onComplete() {

                               }
                           }
                );
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<User> getFavouritesList() {
        return favouritesList;
    }

    public void searchUser(String query, SearchUsersCallback callback) {
        for (User user: users) {
            if (query.equals(user.login)) {
                callback.onFound(user);
                return;
            }
        }
        callback.onFail();
    }

    interface LoadUsersCallback {
        void onLoad(List<User> users);
        void onFail(Throwable t);
    }

    interface SearchUsersCallback {
        void onFound(User foundUser);
        void onFail();
    }
}
