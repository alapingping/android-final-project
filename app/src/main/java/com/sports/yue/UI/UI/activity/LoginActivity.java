package com.sports.yue.UI.UI.activity;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.google.gson.Gson;
import com.sports.yue.R;
import com.sports.yue.UI.UI.Adapter.OnSwipeTouchListener;
import com.sports.yue.UI.UI.api.BmobService;
import com.sports.yue.UI.UI.api.Client;
import com.sports.yue.UI.UI.models.CurrentUser;
import com.sports.yue.UI.UI.models.RoomUser;
import com.sports.yue.UI.UI.models.User;

import com.sports.yue.UI.UI.Database_operation.Db_operation;
import com.sports.yue.UI.UI.Internet.isInternet;
import com.sports.yue.UI.UI.local_db.DbManager;
import com.sports.yue.UI.UI.models.Community;
import com.sports.yue.UI.UI.models.Message;
import com.sports.yue.UI.UI.models.Room;
import com.sports.yue.UI.UI.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.util.List;

import cn.bmob.v3.Bmob;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.sports.yue.UI.UI.models.CurrentUser;

import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class LoginActivity extends AppCompatActivity {

    private UserInfo mInfo;
    public static Tencent mTencent;
    public static String mAppid="1106062414";
    ImageView imageView;
    TextView textView;
    int count = 0;

    private void onClickLogin() {
        if (!mTencent.isSessionValid()) {
            mTencent.login(this, "all", loginListener);
        }
    }
    /**
     * 获取登录QQ腾讯平台的权限信息(用于访问QQ用户信息)
     * @param jsonObject
     */
    public static void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!android.text.TextUtils.isEmpty(token) && !android.text.TextUtils.isEmpty(expires)
                    && !android.text.TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
            }
        } catch(Exception e) {
        }
    }
    private void updateUserInfo() {
        if (mTencent != null && mTencent.isSessionValid()) {
            IUiListener listener = new IUiListener() {
                @Override
                public void onError(UiError e) {
                }
                @Override
                public void onComplete(Object response) {
//                    User user=new User();
//
//                    try {
//                        user.setUserName(((JSONObject)response).getString("nickname"));
//                        user.setUserSex(((JSONObject)response).getString("gender"));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
                      android.os.Message msg = new android.os.Message();
                    msg.obj = response;
                    android.util.Log.i("tag", response.toString());
                    msg.what = 0;
                    mHandler.sendMessage(msg);
                }
                @Override
                public void onCancel() {
                }
            };
            mInfo = new UserInfo(this, mTencent.getQQToken());
            mInfo.getUserInfo(listener);

        }
    }
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0) {
                JSONObject response = (JSONObject) msg.obj;

                User user=new User();

                try {
                    user.setUserName(response.getString("nickname"));
                    user.setUserSex(response.getString("gender"));
                    if (DbManager.getDbManager().selectUser(user.getUserName()).length==0){
                        DbManager.getDbManager().insert(user,null);
                    }
                    Db_operation.getDb_op().add(user);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

// if (response.has("nickname")) {
//                    try {
//                        Gson gson=new Gson();
//                        User user=gson.fromJson(response.toString(),User.class);
//                        if (user!=null) {
////                            tv_name.setText("昵称："+user.getNickname()+"  性别:"+user.getGender()+"  地址："+user.getProvince()+user.getCity());
////                            tv_content.setText("头像路径："+user.getFigureurl_qq_2());
////                            Picasso.with(LoginActivity.this).load(response.getString("figureurl_qq_2")).into(imageView);
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
            }
        }
    };
    /**
     * 继承的到BaseUiListener得到doComplete()的方法信息
     */
    IUiListener loginListener = new BaseUiListener() {
        @Override
        protected void doComplete(JSONObject values) {//得到用户的ID  和签名等信息  用来得到用户信息
//            android.util.Log.i("lkei",values.toString());
            initOpenidAndToken(values);
            updateUserInfo();
        }
    };
    /***
     * QQ平台返回返回数据个体 loginListener的values
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_LOGIN ||
                requestCode == Constants.REQUEST_APPBAR) {
            Tencent.onActivityResultData(requestCode,resultCode,data,loginListener);
        }
        super.onActivityResult(requestCode, resultCode, data);

    }
    private class BaseUiListener implements IUiListener {
        @Override
        public void onComplete(Object response) {
            if (null == response) {
                Toast.makeText(LoginActivity.this, "登录失败",Toast.LENGTH_LONG).show();
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {
                Toast.makeText(LoginActivity.this, "登录失败",Toast.LENGTH_LONG).show();
                return;
            }


            User user=new User();

            try {
                user.setUserName(((JSONObject)response).getString("nickname"));
                user.setUserSex(((JSONObject)response).getString("gender"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Toast.makeText(LoginActivity.this, "登录成功",Toast.LENGTH_LONG).show();
            doComplete((JSONObject)response);




        }

        protected void doComplete(JSONObject values) {





        }
        @Override
        public void onError(UiError e) {
            //登录错误
        }

        @Override
        public void onCancel() {
            // 运行完成
        }
    }



    private JSONObject jsonObject = null;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};
    private List<String> mPermissionList = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
             // setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_login);
        //初始化bmob服务
        imageView = findViewById(R.id.login_imageView);
        textView = findViewById(R.id.login_textView);
        imageView.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
            public void onSwipeTop() {
            }

            public void onSwipeRight() {
                if (count == 0) {
                    imageView.setImageResource(R.drawable.good_night_img);
                    textView.setText("Night");
                    count = 1;
                } else {
                    imageView.setImageResource(R.drawable.good_morning_img);
                    textView.setText("Morning");
                    count = 0;
                }
            }

            public void onSwipeLeft() {
                if (count == 0) {
                    imageView.setImageResource(R.drawable.good_night_img);
                    textView.setText("Night");
                    count = 1;
                } else {
                    imageView.setImageResource(R.drawable.good_morning_img);
                    textView.setText("Morning");
                    count = 0;
                }
            }

            public void onSwipeBottom() {
            }

        });



        findViewById(R.id.qq_login_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLogin();
//                    Toast.makeText(LoginActivity.this,"该功能未开放",Toast.LENGTH_LONG).show();
            }
        });
        if (mTencent == null) {
            mTencent = Tencent.createInstance(mAppid, this);
        }



        Bmob.initialize(this, "5fad9f2543ffa83e56155a46398d6ede");


        if (isInternet.isNetworkAvalible(getApplicationContext())){
            DbManager.getDb_M(getApplicationContext()).delete("USER",null,null);
            DbManager.getDb_M(getApplicationContext()).delete("ROOM",null,null);
            DbManager.getDb_M(getApplicationContext()).delete("COMMUNITY",null,null);
            DbManager.getDb_M(getApplicationContext()).delete("MESSAGE",null,null);
            DbManager.getDb_M(getApplicationContext()).delete("ROOMUSER",null,null);
            Db_operation.getDb_op().searchUser();
            Db_operation.getDb_op().searchRoom();
            Db_operation.getDb_op().searchMessage();
            Db_operation.getDb_op().searchCommunity();
            Db_operation.getDb_op().searchRoomUser();
        }
//        while (!isInternet.isNetworkAvalible(getApplicationContext())){
//
//        }
        if (CurrentUser.getInstance(getApplicationContext()).getUserName().equalsIgnoreCase("-1") && CurrentUser.getInstance(getApplicationContext()).getUserPass().equalsIgnoreCase("-1")) {
            //设置下划线
            TextView forget_text = findViewById(R.id.forget_text);
            forget_text.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            //设置监听
            forget_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(LoginActivity.this, "该功能未开放", Toast.LENGTH_LONG).show();
                }
            });

            TextView signup_text = findViewById(R.id.register_text);
            signup_text.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            signup_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(intent);
                }
            });
        }else {
            jump2main(CurrentUser.getInstance(getApplicationContext()).getUserName(),
                    CurrentUser.getInstance(getApplicationContext()).getUserPass());
        }
    }


    //登陆按钮的跳转
    public void onClickSignin(View view) throws JSONException{
        EditText username_input = findViewById(R.id.username_input);
        EditText password_input = findViewById(R.id.password_input);

        final String username = username_input.getText().toString();
        String password = password_input.getText().toString();

        JSONObject obj = new JSONObject();
        obj.put("UserName",username);
        //使用retrofit实现登录请求
        BmobService service = Client.retrofit.create(BmobService.class);
        Call<ResponseBody> call = service.getUser(obj);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.code() == 200){

                    try {
                        String str =  response.body().string();
                        jsonObject = new JSONObject(str);
                        JSONArray jsonArray = jsonObject.getJSONArray("results");
                        if(password.equals(jsonArray.getJSONObject(0).getString("UserPass"))) {
                            showmsg("登陆成功");
                            CurrentUser.getInstance(getApplicationContext()).setCurrentUser(getApplicationContext(),username,password);
                            jump2main(username, password);
                        }
                        else{
                            showmsg("用户名或密码错误");
                            return ;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (Build.VERSION.SDK_INT >= 23) {
                        for (int i = 0; i < PERMISSIONS_STORAGE.length; i++) {
                            int checkCallPhonePermission = ContextCompat.checkSelfPermission(LoginActivity.this, PERMISSIONS_STORAGE[i]);
                            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                                mPermissionList.add(PERMISSIONS_STORAGE[i]);
                            }
                        }
                        if (mPermissionList.size() > 0) {
                            ActivityCompat.requestPermissions(LoginActivity.this,
                                    PERMISSIONS_STORAGE, 222);
                        } else {
                            jump2main(username, password);
                        }
                    }
                }
                else if(response.code() == 400) {
                    showmsg("用户名或密码错误");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                showmsg(t.getMessage());
            }
        });
}



    //显示信息
    public void showmsg(String msg){
        Toast.makeText(LoginActivity.this,msg,Toast.LENGTH_LONG).show();
    }
    //跳转至主界面
    public void jump2main(String username, String userpass){



        CurrentUser.getInstance(getApplicationContext()).setUserName(username);
        CurrentUser.getInstance(getApplicationContext()).setUserName(userpass);



        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

}
