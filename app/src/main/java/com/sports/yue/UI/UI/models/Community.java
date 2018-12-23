package com.sports.yue.UI.UI.models;

import java.util.Date;

import cn.bmob.v3.BmobObject;

/**
 * 社区
 */
public class Community extends BmobObject {
    private String UserName;//PK
    private Number Likes;
    private String Video;
    private String RoomId;//PK
    private String Email;
    private Date createAt;


    public Community(String Roonid,String Username){
        UserName = Username;
        RoomId = Roonid;
    }
    public Community(){}
    public Number getLikes() {
        return Likes;
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
    public void setLikes(Number likes) {
        Likes = likes;
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

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }


}
