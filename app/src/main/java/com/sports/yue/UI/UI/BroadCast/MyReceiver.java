package com.sports.yue.UI.UI.BroadCast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.sports.yue.UI.UI.Database_operation.Db_operation;
import com.sports.yue.UI.UI.activity.MainActivity;
import com.sports.yue.UI.UI.local_db.DbManager;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {



        Db_operation.getDb_op().searchRoom();
        Intent newintent = new Intent(context, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(newintent);

    }





}
