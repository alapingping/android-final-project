package com.sports.yue.UI.UI.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.jaren.lib.view.LikeView;
import com.sports.yue.R;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

import static com.baidu.mapapi.BMapManager.getContext;


public class VideoAdapter extends CommonAdapter<String> {





    private Activity mContext;
    ArrayList<Map<String, Object>> datas = new ArrayList<>();
    private LikeView mLikeView;
    private LikeView mLikeViewSet;
    public ViewHolder vh;





    public VideoAdapter(Activity context, ArrayList<String> datas, int layoutId)
    {
        super(context, layoutId, datas);
        this.mContext = context;
    }

    public VideoAdapter(Activity context, ArrayList<String> datas,
                        int layoutId, ArrayList<Map<String, Object>> data)
    {
        super(context, layoutId, datas);
        this.mContext = context;
        this.datas = data;
    }


    @Override protected void convert(ViewHolder viewHolder, String url, int position)
    {

        vh =viewHolder;
        JCVideoPlayerStandard player = viewHolder.getView(R.id.videoplayer);
        TextView author_name = viewHolder.getView(R.id.author_name);
        author_name.setText(datas.get(position).get("author_name").toString());
        TextView Community_description = viewHolder.getView(R.id.Community_description);

        mLikeView = viewHolder.getView(R.id.lv1);
        mLikeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                useToggle();
            }
        });


        Community_description.setText(datas.get(position).get("communityMsg").toString());
        author_name.setOnTouchListener((v, event) -> true);
        if (player != null)
        {
            player.release();
        }
        player.setUp(url, JCVideoPlayer.SCREEN_LAYOUT_LIST, "");

        Glide.with(mContext).load(datas.get(position).get("image_url")).into(player.thumbImageView);

    }


    interface Callback {

        void onSuccess();

        void onFail();
    }
    private void useSet(ViewHolder viewHolder) {
        requestPraise(new Callback() {
            @Override
            public void onSuccess() {
                Toast.makeText(getContext(),"toggle Success ",Toast.LENGTH_SHORT).show();
                if (mLikeViewSet.isChecked()) {
                    mLikeViewSet.setChecked(false);
//                  mLikeViewSet.setCheckedWithoutAnimator(false);
                } else {
                    mLikeViewSet.setChecked(true);
                }
            }
            @Override
            public void onFail() {
                Toast.makeText(getContext(),"toggle Fail ",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void useToggle() {
        mLikeView.toggle();
        requestPraise(new Callback() {
            @Override
            public void onSuccess() {
                Toast.makeText(getContext(),"Like Success ",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFail() {
                mLikeView.toggleWithoutAnimator();
                Toast.makeText(getContext(),"Unlike Success ",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void requestPraise(final Callback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(400);
                    mContext.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Random random = new Random();
                            if (random.nextInt() % 2 == 0) {
                                callback.onSuccess();
                            } else {
                                callback.onFail();
                            }
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }



}
