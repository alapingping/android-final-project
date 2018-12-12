package com.sports.yue.UI.UI.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.sports.yue.R;
import com.sports.yue.UI.UI.models.Group;
import com.sports.yue.UI.UI.Adapter.GroupAdapter;
import com.sports.yue.UI.UI.models.Users;

import java.util.ArrayList;
import java.util.List;

public class My_groupFragment extends Fragment {

    ExpandableListView elv;
    private List<Group> list = new ArrayList<Group>();
    int[] img = new int[6];

    public My_groupFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //获取当前View
        View view = inflater.inflate(R.layout.main,container,false);
        initData();

        elv = (ExpandableListView) view.findViewById(R.id.elv);
        GroupAdapter adapter = new GroupAdapter(getContext(), list);
        elv.setAdapter(adapter);

        elv.expandGroup(1);



        return view;
    }

    private void initData() {
        for (int i = 0; i < img.length; i++) {
            try {
                img[i] = R.drawable.class.getField("img0"+(i+1)).getInt(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Group group1 = new Group("我创建的组");
        group1.addUser(new Users(img[0], "319" ,"学习", "3/9", "我爱娜扎！"));
        group1.addUser(new Users(img[0], "319" ,"学习", "3/9", "我爱娜扎！"));
        group1.addUser(new Users(img[0], "319" ,"学习", "3/9", "我爱娜扎！"));
        group1.addUser(new Users(img[0], "319" ,"学习", "3/9", "我爱娜扎！"));

//        group1.addUser(new User(img[3], "杨幂", false, "其实我跟恺威已经离婚了，现在跟李易峰在一起，就酱~"));

        Group group2 = new Group("我加入的组");
        group2.addUser(new Users(img[0], "319" ,"学习", "3/9", "我爱娜扎！"));
        group2.addUser(new Users(img[0], "319" ,"学习", "3/9", "我爱娜扎！"));
        group2.addUser(new Users(img[0], "319" ,"学习", "3/9", "我爱娜扎！"));



        list.add(group1);
        list.add(group2);

    }
}
