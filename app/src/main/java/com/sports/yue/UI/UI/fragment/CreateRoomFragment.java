package com.sports.yue.UI.UI.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.baidu.mapapi.utils.DistanceUtil;
import com.sports.yue.R;
import com.sports.yue.UI.UI.Database_operation.Db_operation;
import com.sports.yue.UI.UI.local_db.DbManager;
import com.sports.yue.UI.UI.models.CurrentUser;
import com.sports.yue.UI.UI.models.Room;
import com.sports.yue.UI.UI.models.RoomUser;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreateRoomFragment extends Fragment {
    private ViewPager viewPager;
    TextView roomnames;
    TextView description;
    TextView activity_type;
    TextView maxpeople;
    TextView location;
    TextView activity_time;
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //sug检索
    private SuggestionSearch mSuggestionSearch;
    //补全输入
    private AutoCompleteTextView autoCompleteTextView;
    //补全适配器
    private ArrayAdapter arrayAdapter;
    //地点列表
    List<String> list = new ArrayList<>();
    //地点详细信息
    List<SuggestionResult.SuggestionInfo> listinfo = new ArrayList<>();
    //
    List<String> lists = new ArrayList<>();
    //地点距离
    List<String> listjl = new ArrayList<>();

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
//        location = (TextView) view.findViewById(R.id.Room_ActivityLocation_Input);
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

        //获取输入框
        autoCompleteTextView = view.findViewById(R.id.Room_ActivityLocation_Input);
        //初始化适配器
        arrayAdapter = new ArrayAdapter(
                getActivity(),android.R.layout.simple_list_item_1);
        //对输入框增加监听
        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //输入前要做的事
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //变化时触发
                String endPoint = autoCompleteTextView.getText().toString();
                mSuggestionSearch = SuggestionSearch.newInstance();
                mSuggestionSearch.setOnGetSuggestionResultListener(suglistener);
                mSuggestionSearch.requestSuggestion((new SuggestionSearchOption())
                        .keyword(endPoint)
                        .city(endPoint));
            }

            @Override
            public void afterTextChanged(Editable s) {
                //变化后触发
            }
        });
        //配置监听器
        autoCompleteTextView.setAdapter(arrayAdapter);

        return view;
    }


    //提交创建房间请求
    public void onCreateSubmit(View view){
        if (roomnames.getText().toString().equalsIgnoreCase("") ||
        description.getText().toString().equalsIgnoreCase("") ||
        activity_type.getText().toString().equalsIgnoreCase("") ||
        maxpeople.getText().toString().equalsIgnoreCase("") ||
        autoCompleteTextView.getText().toString().equalsIgnoreCase("") ||
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
        room.setActivityPosition(autoCompleteTextView.getText().toString());
        room.setRoomCreator(CurrentUser.getInstance(getActivity().getApplicationContext()).getUserName());
        room.setActivityTime(activity_time.getText().toString());
        room.setRoomDescription(description.getText().toString());
        room.setRoomId(roid+"");

        room.setRoomMaxPeople(Integer.valueOf(maxpeople.getText().toString()));
        room.setRoomName(roomnames.getText().toString());
        room.setRoomType(activity_type.getText().toString());
        room.setCurrentUserNum("1");

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

    //智能补全监听器
    OnGetSuggestionResultListener suglistener = new OnGetSuggestionResultListener() {


        @Override
        public void onGetSuggestionResult(SuggestionResult msg) {
            // TODO Auto-generated method stub
            if (msg == null || msg.getAllSuggestions() == null) {
                Toast.makeText(getActivity(), "未检索到当前地址",Toast.LENGTH_SHORT).show();
                return;
            }

            if (list != null) {
                list.clear();
            }

            if (lists != null) {
                lists.clear();
            }

            if (listjl != null) {
                listjl.clear();
            }

            if (listinfo != null) {
                listinfo.clear();
            }
            arrayAdapter.clear();
            for (SuggestionResult.SuggestionInfo info : msg.getAllSuggestions()) {
                if (info.pt == null) continue;
                Log.e("info.ccity", "info.city" + info.city + "info.district" + info.district + "info.key" + info.key);
                listinfo.add(info);
                list.add(info.key);
                lists.add(info.city + info.district + info.key);
                DecimalFormat df = new DecimalFormat("######0");
                String distance = df.format(DistanceUtil.getDistance(listinfo.get(0).pt, info.pt));
                listjl.add(distance);
                arrayAdapter.add(info.key);
            }

            arrayAdapter.notifyDataSetChanged();


//            adapter_list_Address = new Adapter_list_Address(getActivity(), list, lists, listjl);
//            lvAddress.setAdapter(adapter_list_Address);
//            adapter_list_Address.notifyDataSetChanged();
            if (listinfo.size() == 0) {
                Toast.makeText(getActivity(), "未检索到当前地址", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    };

    //处理更新信息的Handler
    Handler mhandler = new Handler(){

        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            arrayAdapter = new ArrayAdapter(
                    getActivity(),android.R.layout.simple_list_item_1,list);
            autoCompleteTextView.setAdapter(arrayAdapter);
        }

    };

}
