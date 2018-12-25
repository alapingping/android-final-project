package com.sports.yue.UI.UI.Adapter;

import android.content.Context;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.sports.yue.R;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.Map;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;


public class VideoAdapter extends CommonAdapter<String> {

    private Context mContext;
    ArrayList<Map<String, Object>> datas = new ArrayList<>();

    public VideoAdapter(Context context, ArrayList<String> datas, int layoutId)
    {
        super(context, layoutId, datas);
        this.mContext = context;
    }

    public VideoAdapter(Context context, ArrayList<String> datas,
                        int layoutId, ArrayList<Map<String, Object>> data)
    {
        super(context, layoutId, datas);
        this.mContext = context;
        this.datas = data;
    }


    @Override protected void convert(ViewHolder viewHolder, String url, int position)
    {
        JCVideoPlayerStandard player = viewHolder.getView(R.id.videoplayer);
        TextView author_name = viewHolder.getView(R.id.author_name);
        author_name.setText(datas.get(position).get("author_name").toString());
        TextView Community_description = viewHolder.getView(R.id.Community_description);
        Community_description.setText(datas.get(position).get("communityMsg").toString());
        author_name.setOnTouchListener((v, event) -> true);
        if (player != null)
        {
            player.release();
        }
        player.setUp(url, JCVideoPlayer.SCREEN_LAYOUT_LIST, "");

        Glide.with(mContext).load("http://a4.att.hudong.com/05/71/01300000057455120185716259013.jpg").into(player.thumbImageView);

    }


}
