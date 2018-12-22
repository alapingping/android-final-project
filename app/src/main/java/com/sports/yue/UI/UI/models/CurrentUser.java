package com.sports.yue.UI.UI.models;

import android.content.Context;

import com.sports.yue.UI.UI.local_db.DbManager;

import java.util.List;

public class CurrentUser extends User {

    private static CurrentUser currentUser = null;

    private CurrentUser(Context c){
        setUserName(getCurrentUser(c).get(0)[0]);
        setUserPass(getCurrentUser(c).get(0)[1]);
    }

    public static CurrentUser getInstance(Context c){
        if(currentUser == null)
            currentUser = new CurrentUser(c);
        return currentUser;
    }

    private List<String[]> getCurrentUser(Context c){
        return DbManager.getDb_M(c).select(new String[]{"UserName","Password"},new String[]{"CURRENTUSER"},null,null);
    }

    public void setCurrentUser(Context c,String username,String password){
        DbManager.getDb_M(c).update("CURRENTUSER",new String[]{"UserName","Password"},new String[]{username,password},null,null);
        setUserName(username);
        setUserPass(password);
    }

}
