package com.sports.yue.UI.UI.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.sports.yue.R;
import com.sports.yue.UI.UI.Database_operation.Db_operation;
import com.sports.yue.UI.UI.local_db.DbManager;
import com.sports.yue.UI.UI.models.CurrentUser;
import com.sports.yue.UI.UI.models.Group;
import com.sports.yue.UI.UI.Adapter.GroupAdapter;
import com.sports.yue.UI.UI.models.Room;
import com.sports.yue.UI.UI.models.RoomUser;

import java.util.ArrayList;
import java.util.List;

public class My_groupFragment extends Fragment {

    ExpandableListView elv;
    private List<Group> list = new ArrayList<Group>();
    int[] img = new int[6];
    GroupAdapter adapter;
    public My_groupFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //获取当前View
        View view = inflater.inflate(R.layout.group_main,container,false);
        elv = (ExpandableListView) view.findViewById(R.id.elv);
        adapter = new GroupAdapter(getContext(), list);

        new Thread(new Runnable() {
            @Override
            public void run() {
                initData();
            }
        }).start();


        elv.setAdapter(adapter);
        elv.expandGroup(1);
        return view;
    }

    private void initData() {
//        for (int i = 0; i < img.length; i++) {
//            try {
//                img[i] = R.drawable.class.getField("img0"+(i+1)).getInt(null);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

        Group group1 = new Group("我创建的组");
        Room[] room = Db_operation.getDb_op().searchRoom(null);
        for (Room r : room){
            if (r.getRoomCreator().equalsIgnoreCase(CurrentUser.getInstance(getActivity().getApplicationContext()).getUserName())){
                group1.addRoom(r);
            }
        }

//        group1.addUser(new User(img[3], "杨幂", false, "其实我跟恺威已经离婚了，现在跟李易峰在一起，就酱~"));

        Group group2 = new Group("我加入的组");
        RoomUser[] rus = Db_operation.getDb_op().searchRoomUser(CurrentUser.getInstance(getActivity().getApplicationContext()).getUserName(),null);
        for (RoomUser ru : rus){
            for (Room r : room){
                if (r.getRoomId().equalsIgnoreCase(ru.getRoomId())){
                    group2.addRoom(r);
                }
            }
        }



        list.add(group1);
        list.add(group2);



    }

}
