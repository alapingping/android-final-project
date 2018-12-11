package com.sports.yue.UI.UI.Database_operation;

import com.sports.yue.UI.UI.models.Room;

import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class Db_operation {

    private static Db_operation Db_op = null;

    private Db_operation(){
    }

    public static Db_operation getDb_op() {
        if (Db_op == null){
            Db_op = new Db_operation();
        }
        return Db_op;
    }

    private void test(){
        //增
        Room room1 = new Room("123","房间123");
        add(room1);

        //删
        delete("123");

        //改
        Room room = new Room("123","");//房间编号是固定的，不能修改
        room.setRoomName("房间213123");
        updata(room);

        //查
        Room[] rooms = search();
        for (int i = 0;i < rooms.length;i++){
            System.out.println(rooms[i]);
        }
    }


    //查
    private Room[] search(){
        //查
        int[] sizes = new int[1];
        BmobQuery<Room> room = new BmobQuery<Room>();
        room.findObjects(new FindListener<Room>() {  //按行查询，查到的数据放到List<Goods>的集合
//
//            @Override
//            public void onSuccess(JSONArray jsonArray) {                //注意：查询结果是JSONArray
////                showToast("查询成功：" + jsonArray.length());
////                Toast.makeText(HomeFragment.this,"该功能未开放",Toast.LENGTH_LONG).show();
//
//            }

            @Override
            public void done(List<Room> list, BmobException e) {
                if (e == null){
                    if (e == null) {
                        sizes[0] = list.size();
                        //  textView.setText(str);
                    } else {
                        System.out.println(e.getErrorCode());
                    }

                }
            }
        });

        Room[] lists = new Room[sizes[0]];
        BmobQuery<Room> rooms = new BmobQuery<Room>();
        rooms.findObjects(new FindListener<Room>() {
            @Override
            public void done(List<Room> list, BmobException e) {
                if (e == null){
                    if (e == null) {
                        int n = list.size();
                        for (int i = 0; i < n; i++) {
                            lists[i] = list.get(i);
                        }
                        Arrays.sort(lists, 0, n);		// 新加了一个排序函数
                        String str = "";
                        for (int i = 0; i < n; i++) {
                            str = str + lists[i].getRoomName() + " " + lists[i].getRoomId() + "\n";
                            System.out.println(lists[i].getRoomName() + "," + lists[i].getRoomId() + "");
                        }                  //  textView.setText(str);
                    } else {
                        System.out.println(e.getErrorCode());
                    }

                }
            }
        });
        return lists;
    }

    //增
    private void add(Room room){
        room.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
//                            tv_show.setText("添加数据成功，返回objectId为：" + objectId);
//                            temp_objectId = objectId;
                } else {
//                            tv_show.setText("创建数据失败：" + e.getMessage());
                }
            }
        });
    }

    //删
    private void delete(String roomid){
        //删
        final Room book = new Room();
        String id = getIDbyRoomId("123");
        if (id.equalsIgnoreCase("error")){
            return;
        }
        book.setObjectId(id);
        book.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
//                    tv_show.setText("数据删除成功 " + book.getUpdatedAt());
                } else {
//                    tv_show.setText("数据删除失败 " + e.getMessage());
                }
            }
        });
    }
    private String getIDbyRoomId(String id){
        String objId = "";
        Room[] li = search();
        boolean flag = false;
        for(int i = 0; i< li.length;i++){
            if (id.equalsIgnoreCase(li[i].getRoomId())){
                objId = li[i].getObjectId();
                flag = true;
                break;
            }
        }
        if (flag){
            return objId;
        }

        return "error";
    }

    //改
    private void updata(Room room){
        String id = getIDbyRoomId(room.getRoomId());
        if (id.equalsIgnoreCase("error")){
            return;
        }
        room.update(id, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
//                    tv_show.setText("更新成功 " + book.getUpdatedAt());
                } else {
//                    tv_show.setText("更新失败 " + e.getMessage());
                }
            }
        });
    }
}
