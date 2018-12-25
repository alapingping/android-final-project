package com.sports.yue.UI.UI.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.sports.yue.R;
import com.sports.yue.UI.UI.Database_operation.Db_operation;
import com.sports.yue.UI.UI.activity.MainActivity;
import com.sports.yue.UI.UI.activity.mapActivity;
import com.sports.yue.UI.UI.api.Share;
import com.sports.yue.UI.UI.local_db.DbManager;
import com.sports.yue.UI.UI.models.CurrentUser;
import com.sports.yue.UI.UI.models.Message;
import com.sports.yue.UI.UI.models.Room;
import com.sports.yue.UI.UI.models.RoomUser;

import java.util.List;

public class RoomFragment extends Fragment {

    ImageView photo;
    TextView roomlocation;
    ImageView toActivityLocation;
    TextView descriptionbox;
    TextView messagebox;
    TextView Room_MessageSend;
    Button Send;
    Button like;
    Button quit;
    Button join;
    TextView Room_Activity_Location;
    TextView Room_MaxPeople;
    TextView Room_Creator;
    TextView Room_Activity_Time;
    TextView Room_type;
    String roomid;


    public RoomFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_room, container, false);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Room");


        photo = view.findViewById(R.id.room_photo_image);
        descriptionbox = view.findViewById(R.id.decription_box);
        messagebox = (TextView) view.findViewById(R.id.message_box);
        messagebox.setMovementMethod(ScrollingMovementMethod.getInstance());
        messagebox.setScrollbarFadingEnabled(false);
        messagebox.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    //通知父控件不要干扰
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                }
                if(event.getAction()==MotionEvent.ACTION_MOVE){
                    //通知父控件不要干扰
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                }
                if(event.getAction()==MotionEvent.ACTION_UP){
                    v.getParent().requestDisallowInterceptTouchEvent(false);
                }
                return false;
            }
        });
        Room_MessageSend = view.findViewById(R.id.Room_MessageSend_Input);

        Room_Activity_Location = view.findViewById(R.id.Room_Activity_Location);
        Room_MaxPeople = view.findViewById(R.id.Room_MaxPeople);
        Room_Creator = view.findViewById(R.id.Room_Creator);
        Room_Activity_Time = view.findViewById(R.id.Room_Activity_Time);
        Room_type = view.findViewById(R.id.Room_type);

        Send = view.findViewById(R.id.message_send);
        like = view.findViewById(R.id.like);
        quit = view.findViewById(R.id.quit);
        join = view.findViewById(R.id.join);

        roomlocation = view.findViewById(R.id.Room_Activity_Location);
        toActivityLocation = view.findViewById(R.id.Room_get_Activity_Location);
        toActivityLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),mapActivity.class);
                intent.putExtra("location",roomlocation.getText().toString());
                startActivity(intent);
            }
        });

        Bundle bundle = new Bundle();
        bundle = getArguments();
        if( bundle != null)
            roomid = bundle.getString("roomid");

        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发送留言
                boolean flag = false;
                RoomUser[] ru = DbManager.getDbManager().selectRoomUser(roomid,null);
                for (RoomUser roomUser : ru){
                    if (roomUser.getUserName().equalsIgnoreCase(CurrentUser.getInstance(getActivity().getApplicationContext()).getUserName())){
                        flag = true;
                        break;
                    }
                }

                if (!flag){
                    Toast.makeText(getActivity(),"你不是此房间成员，请加入后在发送留言!",Toast.LENGTH_LONG).show();
                    return;
                }

                Message message = new Message();
                message.setMessageContent(Room_MessageSend.getText().toString());
                message.setRoomId(roomid);
                message.setUserName(CurrentUser.getInstance(getActivity().getApplicationContext()).getUserName());

                Db_operation.getDb_op().add(message);
                DbManager.getDbManager().insert(message,null);

                Toast.makeText(getActivity(),"发送成功!",Toast.LENGTH_LONG).show();


                Room_MessageSend.setText("");
                setMessagebox(roomid);
            }
        });

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //分享
                Share.onClickShare(getActivity());
            }
        });
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回
                getFragmentManager().popBackStack();
            }
        });
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //加入


                RoomUser[] ru = DbManager.getDbManager().selectRoomUser(roomid,null);
                Room[] rooms = DbManager.getDbManager().selectRoom(roomid);

                if (ru.length >= rooms[0].getRoomMaxPeople().intValue()){
                    Toast.makeText(getActivity(),"此房间已满，你不能加入!",Toast.LENGTH_LONG).show();
                    return;
                }
                for (RoomUser roomUser : ru){
                    if (roomUser.getUserName().equalsIgnoreCase(CurrentUser.getInstance(getActivity().getApplicationContext()).getUserName())){
                        Toast.makeText(getActivity(),"你已加入此房间!",Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                rooms[0].setCurrentUserNum((Integer.valueOf(rooms[0].getCurrentUserNum()) + 1)+"");
                Db_operation.getDb_op().updata(rooms[0]);
                DbManager.getDbManager().update(rooms[0]);
                Db_operation.getDb_op().add(new RoomUser(roomid,CurrentUser.getInstance(getActivity().getApplicationContext()).getUserName()));
                DbManager.getDbManager().insert(new RoomUser(roomid,CurrentUser.getInstance(getActivity().getApplicationContext()).getUserName()),null);

                Toast.makeText(getActivity(),"加入成功!",Toast.LENGTH_LONG).show();


            }
        });

        setMessage(roomid);

        return view;
    }

    private void setMessage(String roomid){
        //从数据库获取

        messagebox.setText("");
        List<String[]> list =  DbManager.getDb_M(getActivity().getApplicationContext()).select(
                new String[]{"RoomDescription","ActivityPosition","RoomMaxPeople","RoomCreator","ActivityTime","RoomType"}
                ,new String[]{"ROOM"},new String[]{"RoomId"},new String[]{roomid});
        List<String[]> roomusernum =  DbManager.getDb_M(getActivity().getApplicationContext()).select(
                null
                ,new String[]{"ROOMUSER"},new String[]{"RoomId"},new String[]{roomid});
        descriptionbox.setText(list.get(0)[0]);
        Room_Activity_Location.setText(list.get(0)[1]);
        Room_MaxPeople.setText(roomusernum.size() + "/" + list.get(0)[2]);
        Room_Creator.setText(list.get(0)[3]);
        Room_Activity_Time.setText(list.get(0)[4]);
        Room_type.setText(list.get(0)[5]);
        list =  DbManager.getDb_M(getActivity().getApplicationContext()).select(
                new String[]{"MessageContent","UserName","createdAt"},new String[]{"MESSAGE"},new String[]{"RoomId"},new String[]{roomid},"createdAt");
        for (String[] str : list){
            messagebox.append(str[1] + "(" + str[2] + "):" + "\r\n\r\r\r\r\r\r" + str[0] + "\r\n");
        }

    }

    private void setMessagebox(String roomid){

        messagebox.setText("");
        List<String[]> list =  DbManager.getDb_M(getActivity().getApplicationContext()).select(
                new String[]{"MessageContent","UserName","createdAt"},new String[]{"MESSAGE"},new String[]{"RoomId"},new String[]{roomid},"createdAt");
        for (String[] str : list){
            messagebox.append(str[1] + "(" + str[2] + "):" + "\r\n\r\r\r\r\r" + str[0] + "\r\n");
        }
    }



}
