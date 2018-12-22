package com.sports.yue.UI.UI.models;

import java.io.File;
import java.util.Date;

import cn.bmob.v3.BmobObject;

/**
 * 用户信息
 */
public class User extends BmobObject {
    private String UserSex;
    private String UserEmail;
    private String UserPhone;
    private String UserPass;
    private String UserName;//PK
    private String UserInfo;


    public User(String name,String pass){
        UserName = name;
        UserPass = pass;
    }

    public User(){}

    public String getUserName() {
        return UserName;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public String getUserInfo() {
        return UserInfo;
    }

    public String getUserPass() {
        return UserPass;
    }

    public String getUserPhone() {
        return UserPhone;
    }

    public String getUserSex() {
        return UserSex;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public void setUserInfo(String userInfo) {
        UserInfo = userInfo;
    }

    public void setUserPass(String userPass) {
        UserPass = userPass;
    }

    public void setUserPhone(String userPhone) {
        UserPhone = userPhone;
    }

    public void setUserSex(String userSex) {
        UserSex = userSex;
    }
}
