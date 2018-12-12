package com.sports.yue.UI.UI.models;

import java.util.ArrayList;
import java.util.List;

public class Group {
    //分组名
    public String groupName;
    //有很多User
    public List<Users> list;

    public Group(String groupName){
        this.groupName = groupName;
        list = new ArrayList<Users>();
    }
    //添加User
    public void addUser(Users user){
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
    public Users getChild(int childPosition) {
        return list.get(childPosition);
    }

}