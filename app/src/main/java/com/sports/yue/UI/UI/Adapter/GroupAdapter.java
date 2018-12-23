package com.sports.yue.UI.UI.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import com.sports.yue.R;
import com.sports.yue.UI.UI.fragment.RoomFragment;
import com.sports.yue.UI.UI.models.Group;
import com.sports.yue.UI.UI.models.Room;
import com.sports.yue.UI.UI.models.RoomUser;

public class GroupAdapter extends BaseExpandableListAdapter {

    Fragment fragment;
    private Context context;
    private List<Group> list;


    public GroupAdapter(Context context, List<Group> list,Fragment fragment) {
        this.context = context;
        this.list = list;
        this.fragment = fragment;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        GroupHolder holder;
        if(convertView == null){
            convertView = View.inflate(context, R.layout.item_group, null);
            holder = new GroupHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (GroupHolder) convertView.getTag();
        }
        //设置数据
        Group group = getGroup(groupPosition);
        holder.groupName.setText(group.groupName);
//        String settext = group.getOnlineCount()+"/"+getChildrenCount(groupPosition);
//        holder.groupOnline.setText(settext);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        ChildHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_child, null);
            holder = new ChildHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ChildHolder) convertView.getTag();
        }
////        //设置数据

        Room user = getGroup(groupPosition).getChild(childPosition);
        holder.Roomid.setText(user.getRoomId());
        holder.img.setImageResource(R.drawable.banner_gym);
        holder.RoomName.setText(user.getRoomName());
        holder.Type.setText(user.getRoomType());
        holder.Member.setText((user.getRoomMaxPeople()+""));
        holder.Location.setText(user.getActivityPosition());
        return convertView;
    }

    class GroupHolder{
        TextView groupName;
        TextView groupOnline;

        private GroupHolder(View convertView){
            groupName = (TextView) convertView.findViewById(R.id.groupName);
//            groupOnline = (TextView) convertView.findViewById(R.id.groupOnline);
        }
    }
    class ChildHolder {
        TextView Roomid;
        ImageView img;
        TextView RoomName;
        TextView Type;
        TextView Member;
        TextView Location;


        private ChildHolder(View convertView) {

            Roomid = (TextView) convertView.findViewById(R.id.roomid);
            img = (ImageView) convertView.findViewById(R.id.room_photo_image);
            RoomName = (TextView) convertView.findViewById(R.id.room_name_text);
            Type = (TextView) convertView.findViewById(R.id.activity_type_text);
            Member = (TextView) convertView.findViewById(R.id.num_people);
            Location = (TextView) convertView.findViewById(R.id.location);
            LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.Room_LinearLayout);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment targetfragment = new RoomFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("roomid",Roomid.getText().toString());
                    targetfragment.setArguments(bundle);
                    FragmentTransaction ft = fragment.getFragmentManager().beginTransaction();
                    ft.replace(R.id.frame_content,targetfragment);
                    ft.addToBackStack(null);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.commit();
                }
            });
        }
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

//    @Override
//    public int getChildrenCount(int groupPosition) {
//        return 0;
//    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).getChildCount();
    }

    @Override
    public Group getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Room getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getChild(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}