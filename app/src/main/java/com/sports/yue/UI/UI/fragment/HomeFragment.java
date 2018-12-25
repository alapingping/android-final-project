package com.sports.yue.UI.UI.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Button;
import android.support.v7.widget.Toolbar;

import com.sports.yue.R;
import com.sports.yue.UI.UI.Service.MyIntentService;
import com.sports.yue.UI.UI.activity.CameraActivity;
import com.sports.yue.UI.UI.local_db.DbManager;
import com.sports.yue.UI.UI.models.Room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ListView roomlist = view.findViewById(R.id.home_room_list);

        SimpleAdapter adapter=new SimpleAdapter(getActivity(),DataList(),R.layout.item_child,
                new String[]{"roomid","room_name_text","activity_type_text","num_people","location"},
                new int[]{R.id.roomid,R.id.room_name_text,R.id.activity_type_text,R.id.num_people,R.id.location});

        roomlist.setAdapter(adapter);

        roomlist.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String roomid = ((TextView) view.findViewById(R.id.roomid)).getText().toString();

                Fragment targetfragment = new RoomFragment();
                Bundle bundle = new Bundle();
                bundle.putString("roomid",roomid);
                targetfragment.setArguments(bundle);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frame_content,targetfragment);
                ft.addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        });

//        Button startserviceBtn = view.findViewById(R.id.startservice);
//        startserviceBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), CameraActivity.class);
//                getActivity().startActivity(intent);
//            }
//        });


        return view;
    }

    public List<Map<String,Object>> DataList() {

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Room[] rooms = DbManager.getDbManager().selectRoom(null);

        for (Room r : rooms) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("roomid", r.getRoomId());
            map.put("room_name_text", r.getRoomName());
            map.put("activity_type_text", r.getRoomType());
            map.put("num_people", r.getRoomMaxPeople());
            map.put("location", r.getActivityPosition());
            list.add(map);
        }

        return list;
    }

    @Override
    public void onResume() {
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        super.onResume();
    }

}

