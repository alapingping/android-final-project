package com.sports.yue.UI.UI.models;

public class User {
    private int imgId;
    private String RoomName;
    private String Type;
    private String Member;
    private String Location;


    public User() {
        super();
    }
    public User(int imgId, String RoomName, String Type , String Member, String Location) {
        super();
        this.imgId = imgId;
        this.RoomName = RoomName;
        this.Type = Type;
        this.Member = Member;
        this.Location = Location;
    }

    public int getImgId() {
        return imgId;
    }
    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getLocation() {
        return Location;
    }

    public String getMember() {
        return Member;
    }

    public String getRoomName() {
        return RoomName;
    }

    public String getType() {
        return Type;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public void setMember(String member) {
        Member = member;
    }

    public void setRoomName(String roomName) {
        RoomName = roomName;
    }

    public void setType(String type) {
        Type = type;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}