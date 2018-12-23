package com.sports.yue.UI.UI.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    TextView textview;
    LinearLayout room_item;
    private List<Group> list = new ArrayList<Group>();
    public My_groupFragment(){

    }
    Room[] room;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //获取当前View
        View view = inflater.inflate(R.layout.group_main,container,false);

        initData();

        elv = (ExpandableListView) view.findViewById(R.id.elv);
        GroupAdapter adapter = new GroupAdapter(getContext(), list);
        elv.setAdapter(adapter);
        elv.expandGroup(1);


        //设置房间item点击监听
//        room_item = view.findViewById(R.id.Room_LinearLayout);
//        textview = view.findViewById(R.id.roomid);
//        room_item.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Fragment targetfragment = new RoomFragment();
//                Bundle bundle = new Bundle();
//                bundle.putString("roomid",textview.getText().toString());
//                targetfragment.setArguments(bundle);
//                FragmentTransaction ft = getFragmentManager().beginTransaction();
//                ft.replace(R.id.frame_content,targetfragment);
//                ft.addToBackStack(null);
//                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//                ft.commit();
//            }
//        });
        return view;
    }

    private void initData() {

        Group group1 = new Group("我创建的组");
        room = DbManager.getDbManager().selectRoom(null);
        for (Room r : room){
            if (r.getRoomCreator().equalsIgnoreCase(CurrentUser.getInstance(getActivity().getApplicationContext()).getUserName())){
                group1.addRoom(r);
            }
        }


        Group group2 = new Group("我加入的组");
        RoomUser[] rus = DbManager.getDbManager().selectRoomUser(null,
                CurrentUser.getInstance(getActivity().getApplicationContext()).getUserName());
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
