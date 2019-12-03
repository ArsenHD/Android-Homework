package com.example.user.homework.GHAPI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class User implements Serializable {
    public String login;
    @SerializedName("avatar_url")
    @Expose
    public String userpic;
}
