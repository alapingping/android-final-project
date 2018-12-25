package com.sports.yue.UI.UI.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.sports.yue.R;
import com.sports.yue.UI.UI.local_db.DbManager;
import com.sports.yue.UI.UI.models.CurrentUser;
import com.sports.yue.UI.UI.models.User;

public class CharatorActivity extends AppCompatActivity {
    private TextView User_Sex;
    private TextView User_Email;
    private TextView User_Tel;
    private TextView User_Sign;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_info);

        User_Sex = findViewById(R.id.User_Sex);
        User_Email = findViewById(R.id.User_Email);
        User_Tel = findViewById(R.id.User_Tel);
        User_Sign = findViewById(R.id.User_Sign);

        User[] users =DbManager.getDbManager().selectUser(CurrentUser.getInstance(getApplicationContext()).getUserName());
        while (users.length == 0){
            users =DbManager.getDbManager().selectUser(CurrentUser.getInstance(getApplicationContext()).getUserName());
        }
        if (users.length>0){
            if (users[0].getUserSex() != null && users[0].getUserSex().length()>0){
                User_Sex.setText(users[0].getUserSex());
            }
            if (users[0].getUserEmail() != null && users[0].getUserEmail().length()>0){
                User_Email.setText(users[0].getUserEmail());
            }
            if (users[0].getUserPhone() != null && users[0].getUserPhone().length()>0){
                User_Tel.setText(users[0].getUserPhone());
            }
            if (users[0].getUserInfo() != null && users[0].getUserInfo().length()>0){
                User_Sign.setText(users[0].getUserInfo());
            }
        }
    }
}
