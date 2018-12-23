package com.sports.yue.UI.UI.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sports.yue.R;
import com.sports.yue.UI.UI.local_db.DbManager;

import java.util.List;

public class RoomFragment extends Fragment {

    private ViewPager viewPager;
    ImageView photo;
    TextView descriptionbox;
    TextView messagebox;
    TextView Room_MessageSend;
    Button Send;
    Button like;
    Button quit;
    Button join;


    public RoomFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_room, container, false);

        photo = view.findViewById(R.id.room_photo_image);
        descriptionbox = view.findViewById(R.id.decription_box);
        messagebox = view.findViewById(R.id.message_box);
        Room_MessageSend = view.findViewById(R.id.Room_MessageSend_Input);
        Send = view.findViewById(R.id.message_send);
        like = view.findViewById(R.id.like);
        quit = view.findViewById(R.id.quit);
        join = view.findViewById(R.id.join);

        String roomid = savedInstanceState.getString("roomid");

        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发送留言

            }
        });
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //收藏

            }
        });
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回

            }
        });
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //加入

            }
        });

        setMessage(roomid);

        return view;
    }

    private void setMessage(String roomid){
        //从数据库获取

        List<String[]> list =  DbManager.getDb_M(getActivity().getApplicationContext()).select(
                new String[]{"RoomDescription"},new String[]{"ROOM"},new String[]{"RoomId"},new String[]{roomid});
        descriptionbox.setText(list.get(0)[0]);
        list =  DbManager.getDb_M(getActivity().getApplicationContext()).select(
                new String[]{"MessageContent","UserName","createdAt"},new String[]{"MESSAGE"},new String[]{"RoomId"},new String[]{roomid},"createdAt");
        for (String[] str : list){
            messagebox.append(str[1] + "(" + str[2] + "):" + "\r\n" + str[0] + "\r\n");
        }

    }
}
