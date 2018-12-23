package com.sports.yue.UI.UI.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sports.yue.R;
import com.sports.yue.UI.UI.Database_operation.Db_operation;
import com.sports.yue.UI.UI.local_db.DbManager;
import com.sports.yue.UI.UI.models.CurrentUser;
import com.sports.yue.UI.UI.models.Room;
import com.sports.yue.UI.UI.models.RoomUser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateRoomFragment extends Fragment {
    private ViewPager viewPager;
    TextView roomnames;
    TextView description;
    TextView activity_type;
    TextView maxpeople;
    TextView location;
    TextView activity_time;
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public CreateRoomFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_create_room, container, false);
        Button bu = (Button) view.findViewById(R.id.Room_Submit);
        Button qu = view.findViewById(R.id.Room_Quit);

        roomnames  = (TextView) view.findViewById(R.id.Room_Name_Input);
        description = (TextView) view.findViewById(R.id.Room_Description_Input);
        activity_type = (TextView) view.findViewById(R.id.Room_Type_Input);
        maxpeople = (TextView) view.findViewById(R.id.Room_MaxPeople_Input);
        location = (TextView) view.findViewById(R.id.Room_ActivityLocation_Input);
        activity_time = (TextView) view.findViewById(R.id.Room_ActivityTime_Input);



        bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreateSubmit(v);
            }
        });

        qu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frame_content,new HomeFragment());
                ft.addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        });

        return view;
    }
    public void onCreateSubmit(View view){
        if (roomnames.getText().toString().equalsIgnoreCase("") ||
        description.getText().toString().equalsIgnoreCase("") ||
        activity_type.getText().toString().equalsIgnoreCase("") ||
        maxpeople.getText().toString().equalsIgnoreCase("") ||
        location.getText().toString().equalsIgnoreCase("") ||
        activity_time.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(getActivity(), "请正确输入信息，信息不能为空", Toast.LENGTH_LONG).show();
            return;
        }
//        TextView roomnames = (TextView) view.findViewById(R.id.Room_Name_Input);
//        TextView description = (TextView) view.findViewById(R.id.Room_Description_Input);
//        TextView activity_type = (TextView) view.findViewById(R.id.Room_Type_Input);
//        TextView maxpeople = (TextView) view.findViewById(R.id.Room_MaxPeople_Input);
//        TextView location = (TextView) view.findViewById(R.id.Room_ActivityLocation_Input);
//        TextView activity_time = (TextView) view.findViewById(R.id.Room_ActivityTime_Input);

        Room[] all = DbManager.getDbManager().selectRoom(null);
        String id = "";
        if (all.length>0) {
            id = all[all.length - 1].getRoomId();
        }else {
            id = "0";
        }
        int roid = Integer.valueOf(id);
        roid = roid + 1;
        Room room = new Room();
        room.setActivityPosition(location.getText().toString());
        room.setRoomCreator(CurrentUser.getInstance(getActivity().getApplicationContext()).getUserName());
        room.setActivityTime(activity_time.getText().toString());
        room.setRoomDescription(description.getText().toString());
        room.setRoomId(roid+"");

        room.setRoomMaxPeople(Integer.valueOf(maxpeople.getText().toString()));
        room.setRoomName(roomnames.getText().toString());
        room.setRoomType(activity_type.getText().toString());

        Db_operation.getDb_op().add(room);
        DbManager.getDbManager().insert(room,null);
        Db_operation.getDb_op().add(new RoomUser(roid+"",CurrentUser.getInstance(getActivity().getApplicationContext()).getUserName()));
        DbManager.getDbManager().insert(new RoomUser(roid+"",CurrentUser.getInstance(getActivity().getApplicationContext()).getUserName()),null);

        Toast.makeText(getActivity(), "创建成功", Toast.LENGTH_LONG).show();

//        changeFragment(R.id.frame_content,new HomeFragment());
//        setActionBarTitle(-1,1);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.frame_content,new HomeFragment());
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }
}
