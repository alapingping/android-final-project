package com.sports.yue.UI.UI.models;

import java.util.ArrayList;
import java.util.List;

public class Group {
    //分组名
    public String groupName;
    //有很多User
    public List<User> list;

    public Group(String groupName){
        this.groupName = groupName;
        list = new ArrayList<User>();
    }
    //添加User
    public void addUser(User user){
        list.add(user);
    }

    //获取某个分组中User的数量
    public int getChildCount() {
        return list.size();
    }
    //获取某个分组中User在线的数量
    public int getOnlineCount(){
        return list.size();
    }
    //获取分组中某个孩子
    public User getChild(int childPosition) {
        return list.get(childPosition);
    }

}