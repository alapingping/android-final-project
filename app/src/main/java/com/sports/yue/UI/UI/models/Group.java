package com.sports.yue.UI.UI.models;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的组
 */
public class Group {
    //分组名
    public String groupName;
    //有很多User
    public List<Room> list;

    public Group(String groupName){
        this.groupName = groupName;
        list = new ArrayList<Room>();
    }
    //添加User
    public void addRoom(Room user){
        list.add(user);
    }

    //获取某个分组中User的数量
    public int getChildCount() {
        return list.size();
    }
    //获取某个分组中User在线的数量
//    public int getOnlineCount(){
//        return list.size();
//    }
    //获取分组中某个孩子
    public Room getChild(int childPosition) {
        return list.get(childPosition);
    }

}