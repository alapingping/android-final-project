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

public class RegisterActivity extends AppCompatActivity {

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

    public void onClickSignup(View view) {
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
