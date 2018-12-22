package com.sports.yue.UI.UI.models;

public class CurrentUser extends User {

    private static CurrentUser currentUser = null;

    private CurrentUser(){
    }

    public static CurrentUser getInstance(){
        if(currentUser == null)
            currentUser = new CurrentUser();
        return currentUser;
    }

}
