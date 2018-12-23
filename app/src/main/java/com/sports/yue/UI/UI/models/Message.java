package com.sports.yue.UI.UI.models;

import java.util.Date;

import cn.bmob.v3.BmobObject;

/**
 * 留言
 */
public class Message extends BmobObject {
    private String UserName;//PK
    private String RoomId;//PK
    private String MessageContent;
    private Date createAt;


    public Message(String user,String ro){
        UserName = user;
        RoomId = ro;
    }
    public Message(){}

    public String getUserName() {
        return UserName;
    }

    public String getRoomId() {
        return RoomId;
    }

    public String getMessageContent() {
        return MessageContent;
    }

    public void setMessageContent(String messageContent) {
        MessageContent = messageContent;
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
