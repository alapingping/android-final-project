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
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sports.yue.R;
import com.sports.yue.UI.UI.Database_operation.Db_operation;
import com.sports.yue.UI.UI.api.BmobService;
import com.sports.yue.UI.UI.api.Client;
import com.sports.yue.UI.UI.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;

public class RegisterActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_register);

        TextView back = findViewById(R.id.backToLoginText);
        back.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });


    }

    public void onClickSignup(View view) throws JSONException {
        //注册功能的实现
        EditText username_input = findViewById(R.id.username_input);
        EditText password_input = findViewById(R.id.password_input);
        EditText ensure_password_input = findViewById(R.id.ensure_password_input);
        String username = username_input.getText().toString();
        String password = password_input.getText().toString();
        String ensure_password = ensure_password_input.getText().toString();


        if(!password.equals(ensure_password)){
            showmsg("两次输入密码不一致!");
            return;
        }

        //创建注册用户模板
        User user = new User(username, password);
        Db_operation.getDb_op().add(user);
        showmsg("注册成功");
        jump2login();


    }
    //显示信息
    public void showmsg(String msg){
        Toast.makeText(RegisterActivity.this,msg,Toast.LENGTH_LONG).show();
    }
    //跳转到主界面
    public void jump2login(){
        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }


}
