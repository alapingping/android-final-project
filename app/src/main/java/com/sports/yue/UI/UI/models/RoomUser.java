package com.sports.yue.UI.UI.models;

import cn.bmob.v3.BmobObject;

/**
 *
 */
public class RoomUser extends BmobObject {
    private String RoomId;
    private String UserName;


    public RoomUser() {
    }
    public RoomUser(String RoomId, String UserName) {
        this.RoomId = RoomId;
        this.UserName = UserName;
    }

    public void setRoomId(String roomId) {
        RoomId = roomId;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserName() {
        return UserName;
    }

    public String getRoomId() {
        return RoomId;
    }
}