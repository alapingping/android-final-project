package com.sports.yue.UI.UI.models;
import cn.bmob.v3.BmobObject;

public class Room extends BmobObject {
    private String RoomId;
    private String RoomName;

    public Room(String id,String name){
        RoomId = id;
        RoomName = name;
    }
    public Room(){}

    public String getRoomId() {
        return RoomId;
    }

    public String getRoomName() {
        return RoomName;
    }

    public void setRoomId(String RoomId) {
        this.RoomId = RoomId;
    }

    public void setRoomName(String RoomName) {
        this.RoomName = RoomName;
    }

    @Override
    public String toString() {
        return "RoomId: " + RoomId + "\r\nRoomName: " + RoomName;
    }
}
