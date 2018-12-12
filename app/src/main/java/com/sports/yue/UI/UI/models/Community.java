package com.sports.yue.UI.UI.models;

import java.util.Date;

import cn.bmob.v3.BmobObject;

public class Community extends BmobObject {
    private String UserName;//PK
    private Number Like;
    private String Video;
    private String RoomId;//PK
    private String Email;
//    private Date createdAT;//PK
//    private Date updatedAt;

    public Community(String Roonid,String Username){
        UserName = Username;
        RoomId = Roonid;
    }
    public Community(){}
    public Number getLike() {
        return Like;
    }
    public String getEmail() {
        return Email;
    }
    public String getRoomId() {
        return RoomId;
    }
    public String getUserName() {
        return UserName;
    }
    public void setEmail(String email) {
        Email = email;
    }
    public void setLike(Number like) {
        Like = like;
    }
    public void setVideo(String video) {
        Video = video;
    }
    public String getVideo() {
        return Video;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public void setRoomId(String roomId) {
        RoomId = roomId;
    }
}
