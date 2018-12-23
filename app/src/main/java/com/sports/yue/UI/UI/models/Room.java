package com.sports.yue.UI.UI.models;
import cn.bmob.v3.BmobObject;
import java.util.Date;

/**
 * 房间
 */
public class Room extends BmobObject {
    private String ActivityTime;
    private String ActivityPosition;
    private Number RoomMaxPeople;
    private String RoomType;
    private String RoomDescription;
    private String RoomCreator;
    private String RoomName;
    private String RoomId;//PK


    public Room(String id,String name){
        RoomId = id;
        RoomName = name;
    }
    public Room(){}

    public String getActivityTime() {
        return ActivityTime;
    }

    public Number getRoomMaxPeople() {
        return RoomMaxPeople;
    }

    public String getActivityPosition() {
        return ActivityPosition;
    }

    public String getRoomCreator() {
        return RoomCreator;
    }

    public String getRoomDescription() {
        return RoomDescription;
    }

    public String getRoomType() {
        return RoomType;
    }

    public void setActivityPosition(String activityPosition) {
        ActivityPosition = activityPosition;
    }

    public void setActivityTime(String activityTime) {
        ActivityTime = activityTime;
    }

    public void setRoomCreator(String roomCreator) {
        RoomCreator = roomCreator;
    }

    public void setRoomDescription(String roomDescription) {
        RoomDescription = roomDescription;
    }

    public void setRoomMaxPeople(Number roomMaxPeople) {
        RoomMaxPeople = roomMaxPeople;
    }

    public void setRoomType(String roomType) {
        RoomType = roomType;
    }

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
