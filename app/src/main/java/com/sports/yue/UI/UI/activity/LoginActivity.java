package com.sports.yue.UI.UI.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sports.yue.R;


import cn.bmob.v3.Bmob;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //初始化bmob服务

        Bmob.initialize(this, "5fad9f2543ffa83e56155a46398d6ede");

            //设置下划线
            TextView forget_text = findViewById(R.id.forget_text);
            forget_text.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            //设置监听
            forget_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(LoginActivity.this,"该功能未开放",Toast.LENGTH_LONG).show();
                }
            });

            TextView signup_text = findViewById(R.id.register_text);
            signup_text.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            signup_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                    startActivity(intent);
                }
            });
        }


    //登陆按钮的跳转
    public void onClickSignin(View view) {
        EditText username_input = findViewById(R.id.username_input);
        EditText password_input = findViewById(R.id.password_input);

        final String username = username_input.getText().toString();
        String password = password_input.getText().toString();

        jump2main(username);


    }



    //显示信息
    public void showmsg(String msg){
        Toast.makeText(LoginActivity.this,msg,Toast.LENGTH_LONG).show();
    }
    //跳转至主界面
    public void jump2main(String username){
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("username",username);
        startActivity(intent);
        finish();
    }

}
