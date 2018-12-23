package com.sports.yue.UI.UI.Service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import android.os.Handler;

import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.sports.yue.R;
import com.sports.yue.UI.UI.activity.LoginActivity;
import com.sports.yue.UI.UI.api.BmobService;
import com.sports.yue.UI.UI.api.Client;
import com.sports.yue.UI.UI.local_db.DbManager;
import com.sports.yue.UI.UI.models.CurrentUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Thread.sleep;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class MyIntentService extends IntentService {

    private Handler myHandler;
    private int RoomNum;
    private int LocalRoomNum;
    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        myHandler = new Handler();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        synchronized (this){
            if (intent != null) {
                try{
                    wait(100);
//                    getRomateRoomNum();
                    startQuery();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }

    private void startQuery(){
            new Thread(() -> {
                while (true){
                    LocalRoomNum = DbManager.getDbManager().selectRoom(null).length;
                    try{
                        sleep(5000);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    new Thread(() -> getRomateRoomNum()).start();
                }
            }).start();


    }

    private void getRomateRoomNum(){
        BmobService service = Client.retrofit.create(BmobService.class);
        Call<ResponseBody> call = service.getRoomNum(1,0);
        call.enqueue(new Callback<ResponseBody>(){
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String result = response.body().string();
                    JSONObject jsonObject = new JSONObject(result);
                    RoomNum = jsonObject.getInt("count");
                    if(RoomNum != LocalRoomNum)
                        myHandler.post(() ->
                                Toast.makeText(getApplicationContext(),"数据不一致",Toast.LENGTH_LONG).show()
                        );
                    else if(RoomNum == LocalRoomNum)
                        CreateNotification();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.print(1);
            }
        });
    }

    private void CreateNotification(){
        Notification notification = new Notification.Builder(getApplicationContext())
                .setAutoCancel(true)
                .setContentTitle("Yue")
                .setContentText("有人创建了新房间，快来看看吧~")
                //不设置小图标通知栏显示通知（不确定）
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(PendingIntent.getService(getApplicationContext(),0,
                        new Intent(this, MyIntentService.class), PendingIntent.FLAG_ONE_SHOT))
                .build();
        notification.flags = Notification.FLAG_INSISTENT;
        //利用 NotificationManager 类发出通知栏通知
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(1, notification);
    }

}
