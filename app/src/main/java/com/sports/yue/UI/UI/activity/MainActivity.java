package com.sports.yue.UI.UI.activity;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sports.yue.UI.UI.Database_operation.Db_operation;
import com.sports.yue.UI.UI.Service.MyIntentService;
import com.sports.yue.UI.UI.fragment.CreateRoomFragment;

import com.sports.yue.UI.UI.fragment.CommunityFragment;
import com.sports.yue.UI.UI.fragment.FavoriteFragment;
import com.sports.yue.UI.UI.fragment.MeFragment;
import com.sports.yue.UI.UI.fragment.HomeFragment;
import com.sports.yue.R;
import com.sports.yue.UI.UI.fragment.GroupFragment;
import com.sports.yue.UI.UI.fragment.My_groupFragment;
import com.sports.yue.UI.UI.fragment.RoomFragment;
import com.sports.yue.UI.UI.fragment.ScheduleFragment;
import com.sports.yue.UI.UI.local_db.DbManager;
import com.sports.yue.UI.UI.models.CurrentUser;

import cn.bmob.v3.BmobUser;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static boolean isExist = false;   //判断是否退出
    private String [] titles = {"Home","Schedule","Favorite","Group"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_left_navigation);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //设置滑动监听，利用DrawLayout以及ActionBarDrawerToggle
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //设置导航监听
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.navigation_home);

        //设置底部导航按钮
        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        View headerLayout = navigationView.getHeaderView(0);

        String username = CurrentUser.getInstance(getApplicationContext()).getUserName();
        TextView nav_username = headerLayout.findViewById(R.id.nav_username);
        nav_username.setText(username);

        Intent service = new Intent(this, MyIntentService.class);
        startService(service);



        //设置初始片段为HomeFragment
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_content,new HomeFragment());
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.left_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //处理左侧导航抽屉点击事件
        int id = item.getItemId();
        int position = -1;
        Fragment fragment = null;
        if (id == R.id.nav_home) {
            // Handle the home action
            fragment = new HomeFragment();
            position = 0;
        } else if (id == R.id.nav_favorite) {
           fragment = new FavoriteFragment();
            position = 1;
        } else if (id == R.id.nav_schedule) {
            fragment = new ScheduleFragment();
            position = 2;
        } else if (id == R.id.nav_share) {
            Toast.makeText(MainActivity.this,"该功能未开放",Toast.LENGTH_LONG).show();
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        } else if (id == R.id.nav_send) {
            Toast.makeText(MainActivity.this,"该功能未开放",Toast.LENGTH_LONG).show();
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

        changeFragment(R.id.frame_content,fragment);
        setActionBarTitle(position,1);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //设置底端导航按钮响应事件
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        //打开侧滑功能
                        changeFragment(R.id.frame_content,new HomeFragment());
                        setActionBarTitle(-1,1);
                        NavigationView navigationView = findViewById(R.id.nav_view);
                        navigationView.setCheckedItem(R.id.navigation_home);
                        return true;
                    case R.id.navigation_dashboard:
                        //禁用侧滑功能
                        changeFragment(R.id.frame_content,new My_groupFragment());
                        setActionBarTitle(-1,2);
                        return true;
                    case R.id.navigation_coaches:
                        //禁用侧滑功能
                        changeFragment(R.id.frame_content,new CommunityFragment());
                        setActionBarTitle(-1,3);
                        return true;
                    case R.id.navigation_group:
                        //禁用侧滑功能
                        changeFragment(R.id.frame_content,new MeFragment());
                        setActionBarTitle(-1,4);
                        return true;
                }
                return false;
            };

    /**
     * 切换片段所用方法
     * @param currentLayout 当前页面布局
     * @param targetFragment  目标片段
     */
    public void changeFragment(int currentLayout, Fragment targetFragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(currentLayout,targetFragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    @Override
    public boolean onKeyDown(int keyNode, KeyEvent event){
        if(!isExist) {
            isExist = true;
            Toast.makeText(MainActivity.this, "再按一次退出应用", Toast.LENGTH_LONG).show();
            new ExitHandler().sendEmptyMessageDelayed(0, 2000);
        }
        else{
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }
        return false;
    }


    private void setActionBarTitle(int position, int page_num){
        Toolbar toolbar = findViewById(R.id.toolbar);
        if(page_num == 1){
            if(position == -1){
                toolbar.setTitle("主页");
                return;
            }
            toolbar.setTitle(titles[position]);
        }else if(page_num == 2){
            toolbar.setTitle("日程");
        }else if(page_num == 3){
            toolbar.setTitle("收藏");
        }
    }


    public void onClickLogout(View view){
        CurrentUser.getInstance(getApplicationContext()).setCurrentUser(getApplicationContext(),"-1","-1");
        Intent service = new Intent(this, MyIntentService.class);
        stopService(service);
        BmobUser.logOut();
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    //处理退出信息的Handler
    static class ExitHandler extends Handler {
      @Override
      public void handleMessage(Message msg){
          isExist = false;
      }
    }

    public void onClickCreateRoom(View view){
        changeFragment(R.id.frame_content,new CreateRoomFragment());
        setActionBarTitle(-1,2);
    }

    public void onClickRoom(View view){
        Fragment targetfragment = new RoomFragment();
        Bundle bundle = new Bundle();
        bundle.putString("roomid",((TextView) view.findViewById(R.id.roomid)).getText().toString());
        targetfragment.setArguments(bundle);
        changeFragment(R.id.frame_content,targetfragment);
    }

}
