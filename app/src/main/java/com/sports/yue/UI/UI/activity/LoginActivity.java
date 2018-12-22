package com.sports.yue.UI.UI.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sports.yue.R;
import com.sports.yue.UI.UI.api.BmobService;
import com.sports.yue.UI.UI.api.Client;
import com.sports.yue.UI.UI.models.CurrentUser;
import com.sports.yue.UI.UI.models.User;

import com.sports.yue.UI.UI.Database_operation.Db_operation;
import com.sports.yue.UI.UI.Internet.isInternet;
import com.sports.yue.UI.UI.local_db.DbManager;
import com.sports.yue.UI.UI.models.Community;
import com.sports.yue.UI.UI.models.Message;
import com.sports.yue.UI.UI.models.Room;
import com.sports.yue.UI.UI.models.User;

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

public class LoginActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_login);
        //初始化bmob服务

        Bmob.initialize(this, "5fad9f2543ffa83e56155a46398d6ede");

        DbManager.getDb_M(getApplicationContext()).delete("USER",null,null);
        DbManager.getDb_M(getApplicationContext()).delete("ROOM",null,null);
        DbManager.getDb_M(getApplicationContext()).delete("COMMUNITY",null,null);
        DbManager.getDb_M(getApplicationContext()).delete("MESSAGE",null,null);
        if (isInternet.isNetworkAvalible(getApplicationContext())){
            User[] us = Db_operation.getDb_op().searchUser(null);
            for (User u : us){
                if (DbManager.getDb_M(getApplicationContext()).select(new String[]{"UserName"},new String[]{"USER"},null,null).size() > 0){
                    DbManager.getDb_M(getApplicationContext()).update(u);
                }else {
                    DbManager.getDb_M(getApplicationContext()).insert(u);
                }
            }

            Room[] r = Db_operation.getDb_op().searchRoom(null);
            for (Room u : r){
                if (DbManager.getDb_M(getApplicationContext()).select(new String[]{"RoomId"},new String[]{"ROOM"},null,null).size() > 0){
                    DbManager.getDb_M(getApplicationContext()).update(u);
                }else {
                    DbManager.getDb_M(getApplicationContext()).insert(u);
                }
            }

            Message[] m = Db_operation.getDb_op().searchAllMessage();
            for (Message u : m){
                if (DbManager.getDb_M(getApplicationContext()).select(new String[]{"UserName","RoomId","createdAT"},
                        new String[]{u.getUserName(),u.getRoomId(),u.getCreatedAt()},null,null).size() > 0){
                    DbManager.getDb_M(getApplicationContext()).update(u);
                }else {
                    DbManager.getDb_M(getApplicationContext()).insert(u);
                }
            }
            Community[] c = Db_operation.getDb_op().searchAllCommunity();
            for (Community u : c){
                if (DbManager.getDb_M(getApplicationContext()).select(new String[]{"UserName","RoomId","createdAT"},
                        new String[]{u.getUserName(),u.getRoomId(),u.getCreatedAt()},null,null).size() > 0){
                    DbManager.getDb_M(getApplicationContext()).update(u);
                }else {
                    DbManager.getDb_M(getApplicationContext()).insert(u);
                }
            }
        }
//        while (!isInternet.isNetworkAvalible(getApplicationContext())){
//
//        }
        if (CurrentUser.getInstance(getApplicationContext()).getUserName()=="-1" && CurrentUser.getInstance(getApplicationContext()).getUserPass() == "-1") {
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
            try {
                jump2main(new JSONObject(CurrentUser.getInstance(getApplicationContext()).getUserName()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    //登陆按钮的跳转
    public void onClickSignin(View view) throws JSONException{
        EditText username_input = findViewById(R.id.username_input);
        EditText password_input = findViewById(R.id.password_input);

        final String username = username_input.getText().toString();
        String password = password_input.getText().toString();

        CurrentUser.getInstance(getApplicationContext()).setCurrentUser(getApplicationContext(),username,password);

        JSONObject obj = new JSONObject();
//        obj.put("UserPhone","135");
        //使用retrofit实现登录请求
        BmobService service = Client.retrofit.create(BmobService.class);
        Call<ResponseBody> call = service.getUser(obj);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.code() == 200){
                    showmsg("登陆成功");
                    try {
                        String str =  response.body().string();
                        jsonObject = new JSONObject(str);

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
                            jump2main(jsonObject);
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
    public void jump2main(JSONObject jsonObject){


        try {
            CurrentUser.getInstance(getApplicationContext()).setUserName(jsonObject.getString("UserName"));
            CurrentUser.getInstance(getApplicationContext()).setUserName(jsonObject.getString("UserPass"));
        } catch (JSONException e) {
            e.printStackTrace();
        }



        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

}
