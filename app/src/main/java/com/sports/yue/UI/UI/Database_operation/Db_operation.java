package com.sports.yue.UI.UI.Database_operation;

import com.sports.yue.UI.UI.Adapter.GroupAdapter;
import com.sports.yue.UI.UI.models.Community;
import com.sports.yue.UI.UI.models.Message;
import com.sports.yue.UI.UI.models.Room;
import com.sports.yue.UI.UI.models.RoomUser;
import com.sports.yue.UI.UI.models.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class Db_operation {

    private Room[] rooms;
    private RoomUser[] roomuser;
    private static Db_operation Db_op = null;

    private Db_operation(){
        rooms = new Room[0];
        roomuser = new RoomUser[0];
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
        deleteRoom("123");

        //改
        //房间编号是固定的，不能修改
        Room[] roomss = searchRoom("123");
        for (Room i : roomss){
            i.setRoomName("房间213123");
            updata(i);
        }


        //查
        Room[] rooms = searchRoom(null);
        for (Room i : rooms){
            System.out.println(i);
        }
    }


    //查
    /*
    *   所有参数都能为空，都为空时是查找此表内所有信息。
    */

    /**
     *
     * @param roomid               房间ID
     * @return                      符合条件的Room数组
     */
    public Room[] searchRoom(String roomid){
        //查
        rooms = new Room[0];
        int[] na = new int[1];
        na[0]=0;
        BmobQuery<Room> newroom = new BmobQuery<Room>();
        newroom.findObjects(new FindListener<Room>() {
            @Override
            public void done(List<Room> list, BmobException e) {
                na[0] = 1;
                if (e == null){
                    if (e == null) {
                        int n = list.size();
                        rooms = new Room[n];
                        for (int i = 0; i < n; i++) {
                            rooms[i] = list.get(i);
                        }
                        Arrays.sort(rooms, 0, n);		// 新加了一个排序函数
                        String str = "";
                        for (int i = 0; i < n; i++) {
                            str = str + rooms[i].getRoomName() + " " + rooms[i].getRoomId() + "\n";
                            System.out.println(rooms[i].getRoomName() + "," + rooms[i].getRoomId() + "");
                        }                  //  textView.setText(str);
                    } else {
                        System.out.println(e.getErrorCode());
                    }
                }
            }
        });


        Room[] ro = new Room[rooms.length];
        int size = 0;
        for (int i = 0;i < rooms.length;i++){
            if (roomid == null || rooms[i].getRoomId().equalsIgnoreCase(roomid)){
                ro[size] = rooms[i];
                size++;
            }
        }
        rooms = new Room[size];
        for (int i = 0;i < size;i++){
            rooms[i] = ro[i];
        }
        return rooms;
    }
    /**
     *
     * @param username             用户名
     * @param roomid               房间ID
     * @param createdat            创建时间
     * @return                      符合条件的Community数组
     */
    public Community[] searchCommunity(String username,String roomid,Date createdat){
        //查
        int[] sizes = new int[1];
        BmobQuery<Community> room = new BmobQuery<Community>();
        room.findObjects(new FindListener<Community>() {  //按行查询，查到的数据放到List<Goods>的集合
//
//            @Override
//            public void onSuccess(JSONArray jsonArray) {                //注意：查询结果是JSONArray
////                showToast("查询成功：" + jsonArray.length());
////                Toast.makeText(HomeFragment.this,"该功能未开放",Toast.LENGTH_LONG).show();
//
//            }

            @Override
            public void done(List<Community> list, BmobException e) {
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

        Community[] lists = new Community[sizes[0]];
        BmobQuery<Community> rooms = new BmobQuery<Community>();
        rooms.findObjects(new FindListener<Community>() {
            @Override
            public void done(List<Community> list, BmobException e) {
                if (e == null){
                    if (e == null) {
                        int n = list.size();
                        for (int i = 0; i < n; i++) {
                            if ((username == null || list.get(i).getUserName().equalsIgnoreCase(username)) &&
                                    (roomid == null || list.get(i).getRoomId().equalsIgnoreCase(roomid)) &&
                                    (createdat == null || list.get(i).getCreatedAt().equalsIgnoreCase(createdat.toString())))   {
                                lists[i] = list.get(i);
                            }
                        }
                        Arrays.sort(lists, 0, n);		// 新加了一个排序函数
                        String str = "";
                        for (int i = 0; i < n; i++) {
                            str = str + lists[i].getEmail() + " " + lists[i].getRoomId()+
                                    " " + lists[i].getUserName()+ " " + lists[i].getVideo()+
                                    " " + lists[i].getCreatedAt()+ " " + lists[i].getLikes() +
                                    " " + lists[i].getObjectId() + "\n";
                            System.out.println(lists[i].getEmail() + "," + lists[i].getRoomId()+
                                    "," + lists[i].getUserName()+ "," + lists[i].getVideo()+
                                    "," + lists[i].getCreatedAt()+ "," + lists[i].getLikes()+
                                    "," + lists[i].getObjectId() + "");
                        }                  //  textView.setText(str);
                    } else {
                        System.out.println(e.getErrorCode());
                    }

                }
            }
        });
        return lists;
    }
    /**
     *
     * @param username             用户名
     * @param roomid               房间ID
     * @param createdat            创建时间
     * @return                      符合条件的Community数组
     */
    public Community[] searchCommunity(String username,String roomid,String createdat){
        //查
        int[] sizes = new int[1];
        BmobQuery<Community> room = new BmobQuery<Community>();
        room.findObjects(new FindListener<Community>() {  //按行查询，查到的数据放到List<Goods>的集合
//
//            @Override
//            public void onSuccess(JSONArray jsonArray) {                //注意：查询结果是JSONArray
////                showToast("查询成功：" + jsonArray.length());
////                Toast.makeText(HomeFragment.this,"该功能未开放",Toast.LENGTH_LONG).show();
//
//            }

            @Override
            public void done(List<Community> list, BmobException e) {
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

        Community[] lists = new Community[sizes[0]];
        BmobQuery<Community> rooms = new BmobQuery<Community>();
        rooms.findObjects(new FindListener<Community>() {
            @Override
            public void done(List<Community> list, BmobException e) {
                if (e == null){
                    if (e == null) {
                        int n = list.size();
                        for (int i = 0; i < n; i++) {
                            if ((username == null || list.get(i).getUserName().equalsIgnoreCase(username)) &&
                                    (roomid == null || list.get(i).getRoomId().equalsIgnoreCase(roomid)) &&
                                    (createdat == null || list.get(i).getCreatedAt().equalsIgnoreCase(createdat)))   {
                                lists[i] = list.get(i);
                            }
                        }
                        Arrays.sort(lists, 0, n);		// 新加了一个排序函数
                        String str = "";
                        for (int i = 0; i < n; i++) {
                            str = str + lists[i].getEmail() + " " + lists[i].getRoomId()+
                                    " " + lists[i].getUserName()+ " " + lists[i].getVideo()+
                                    " " + lists[i].getCreatedAt()+ " " + lists[i].getLikes() +
                                    " " + lists[i].getObjectId() + "\n";
                            System.out.println(lists[i].getEmail() + "," + lists[i].getRoomId()+
                                    "," + lists[i].getUserName()+ "," + lists[i].getVideo()+
                                    "," + lists[i].getCreatedAt()+ "," + lists[i].getLikes()+
                                    "," + lists[i].getObjectId() + "");
                        }                  //  textView.setText(str);
                    } else {
                        System.out.println(e.getErrorCode());
                    }

                }
            }
        });
        return lists;
    }

    /**
     *
     * @return  所有Community
     */
    public Community[] searchAllCommunity(){
        //查
        int[] sizes = new int[1];
        BmobQuery<Community> room = new BmobQuery<Community>();
        room.findObjects(new FindListener<Community>() {  //按行查询，查到的数据放到List<Goods>的集合
//
//            @Override
//            public void onSuccess(JSONArray jsonArray) {                //注意：查询结果是JSONArray
////                showToast("查询成功：" + jsonArray.length());
////                Toast.makeText(HomeFragment.this,"该功能未开放",Toast.LENGTH_LONG).show();
//
//            }

            @Override
            public void done(List<Community> list, BmobException e) {
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

        Community[] lists = new Community[sizes[0]];
        BmobQuery<Community> rooms = new BmobQuery<Community>();
        rooms.findObjects(new FindListener<Community>() {
            @Override
            public void done(List<Community> list, BmobException e) {
                if (e == null){
                    if (e == null) {
                        int n = list.size();
                        for (int i = 0; i < n; i++) {
                            lists[i] = list.get(i);
                        }
                        Arrays.sort(lists, 0, n);		// 新加了一个排序函数
                        String str = "";
                        for (int i = 0; i < n; i++) {
                            str = str + lists[i].getEmail() + " " + lists[i].getRoomId()+
                                    " " + lists[i].getUserName()+ " " + lists[i].getVideo()+
                                    " " + lists[i].getCreatedAt()+ " " + lists[i].getLikes() +
                                    " " + lists[i].getObjectId() + "\n";
                            System.out.println(lists[i].getEmail() + "," + lists[i].getRoomId()+
                                    "," + lists[i].getUserName()+ "," + lists[i].getVideo()+
                                    "," + lists[i].getCreatedAt()+ "," + lists[i].getLikes()+
                                    "," + lists[i].getObjectId() + "");
                        }                  //  textView.setText(str);
                    } else {
                        System.out.println(e.getErrorCode());
                    }

                }
            }
        });
        return lists;
    }
    /**
     *
     * @param username             用户名
     * @param roomid               房间ID
     * @param createdat            创建时间
     * @return                      符合条件的Message数组
     */
    public Message[] searchMessage(String username,String roomid,Date createdat){
        //查
        int[] sizes = new int[1];
        BmobQuery<Message> room = new BmobQuery<Message>();
        room.findObjects(new FindListener<Message>() {  //按行查询，查到的数据放到List<Goods>的集合
//
//            @Override
//            public void onSuccess(JSONArray jsonArray) {                //注意：查询结果是JSONArray
////                showToast("查询成功：" + jsonArray.length());
////                Toast.makeText(HomeFragment.this,"该功能未开放",Toast.LENGTH_LONG).show();
//
//            }

            @Override
            public void done(List<Message> list, BmobException e) {
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

        Message[] lists = new Message[sizes[0]];
        BmobQuery<Message> rooms = new BmobQuery<Message>();
        rooms.findObjects(new FindListener<Message>() {
            @Override
            public void done(List<Message> list, BmobException e) {
                if (e == null){
                    if (e == null) {
                        int n = list.size();
                        for (int i = 0; i < n; i++) {
                            if ((username == null || list.get(i).getUserName().equalsIgnoreCase(username)) &&
                                    (roomid == null || list.get(i).getRoomId().equalsIgnoreCase(roomid)) &&
                                    (createdat == null || list.get(i).getCreatedAt().equalsIgnoreCase(createdat.toString())))   {
                                lists[i] = list.get(i);
                            }
                        }
                        Arrays.sort(lists, 0, n);		// 新加了一个排序函数
                        String str = "";
                        for (int i = 0; i < n; i++) {
                            str = str + lists[i].getMessageContent() + " " + lists[i].getRoomId() +
                                    " " + lists[i].getUserName() + " " + lists[i].getObjectId() + "\n";
                            System.out.println(lists[i].getMessageContent() + "," + lists[i].getRoomId() +
                                    "," + lists[i].getUserName() + "," + lists[i].getObjectId() + "");
                        }                  //  textView.setText(str);
                    } else {
                        System.out.println(e.getErrorCode());
                    }

                }
            }
        });
        return lists;
    }
    /**
     *
     * @param username             用户名
     * @param roomid               房间ID
     * @param createdat            创建时间
     * @return                      符合条件的Message数组
     */
    public Message[] searchMessage(String username,String roomid,String createdat){
        //查
        int[] sizes = new int[1];
        BmobQuery<Message> room = new BmobQuery<Message>();
        room.findObjects(new FindListener<Message>() {  //按行查询，查到的数据放到List<Goods>的集合
//
//            @Override
//            public void onSuccess(JSONArray jsonArray) {                //注意：查询结果是JSONArray
////                showToast("查询成功：" + jsonArray.length());
////                Toast.makeText(HomeFragment.this,"该功能未开放",Toast.LENGTH_LONG).show();
//
//            }

            @Override
            public void done(List<Message> list, BmobException e) {
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

        Message[] lists = new Message[sizes[0]];
        BmobQuery<Message> rooms = new BmobQuery<Message>();
        rooms.findObjects(new FindListener<Message>() {
            @Override
            public void done(List<Message> list, BmobException e) {
                if (e == null){
                    if (e == null) {
                        int n = list.size();
                        for (int i = 0; i < n; i++) {
                            if ((username == null || list.get(i).getUserName().equalsIgnoreCase(username)) &&
                                    (roomid == null || list.get(i).getRoomId().equalsIgnoreCase(roomid)) &&
                                    (createdat == null || list.get(i).getCreatedAt().equalsIgnoreCase(createdat)))   {
                                lists[i] = list.get(i);
                            }
                        }
                        Arrays.sort(lists, 0, n);		// 新加了一个排序函数
                        String str = "";
                        for (int i = 0; i < n; i++) {
                            str = str + lists[i].getMessageContent() + " " + lists[i].getRoomId() +
                                    " " + lists[i].getUserName() + " " + lists[i].getObjectId() + "\n";
                            System.out.println(lists[i].getMessageContent() + "," + lists[i].getRoomId() +
                                    "," + lists[i].getUserName() + "," + lists[i].getObjectId() + "");
                        }                  //  textView.setText(str);
                    } else {
                        System.out.println(e.getErrorCode());
                    }

                }
            }
        });
        return lists;
    }
    /**
     *
     * @return  所有Message
     */
    public Message[] searchAllMessage(){
        //查
        int[] sizes = new int[1];
        BmobQuery<Message> room = new BmobQuery<Message>();
        room.findObjects(new FindListener<Message>() {  //按行查询，查到的数据放到List<Goods>的集合
//
//            @Override
//            public void onSuccess(JSONArray jsonArray) {                //注意：查询结果是JSONArray
////                showToast("查询成功：" + jsonArray.length());
////                Toast.makeText(HomeFragment.this,"该功能未开放",Toast.LENGTH_LONG).show();
//
//            }

            @Override
            public void done(List<Message> list, BmobException e) {
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

        Message[] lists = new Message[sizes[0]];
        BmobQuery<Message> rooms = new BmobQuery<Message>();
        rooms.findObjects(new FindListener<Message>() {
            @Override
            public void done(List<Message> list, BmobException e) {
                if (e == null){
                    if (e == null) {
                        int n = list.size();
                        for (int i = 0; i < n; i++) {
                            lists[i] = list.get(i);
                        }
                        Arrays.sort(lists, 0, n);		// 新加了一个排序函数
                        String str = "";
                        for (int i = 0; i < n; i++) {
                            str = str + lists[i].getMessageContent() + " " + lists[i].getRoomId() +
                                    " " + lists[i].getUserName() + " " + lists[i].getObjectId() + "\n";
                            System.out.println(lists[i].getMessageContent() + "," + lists[i].getRoomId() +
                                    "," + lists[i].getUserName() + "," + lists[i].getObjectId() + "");
                        }                  //  textView.setText(str);
                    } else {
                        System.out.println(e.getErrorCode());
                    }

                }
            }
        });
        return lists;
    }
    /**
     *
     * @param username             用户名,为空时查询所有用户
     * @return                      符合条件的User数组
     */
    public User[] searchUser(String username){
        //查
        int[] sizes = new int[1];
        BmobQuery<User> room = new BmobQuery<User>();
        room.findObjects(new FindListener<User>() {  //按行查询，查到的数据放到List<Goods>的集合
//
//            @Override
//            public void onSuccess(JSONArray jsonArray) {                //注意：查询结果是JSONArray
////                showToast("查询成功：" + jsonArray.length());
////                Toast.makeText(HomeFragment.this,"该功能未开放",Toast.LENGTH_LONG).show();
//
//            }

            @Override
            public void done(List<User> list, BmobException e) {
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

        User[] lists = new User[sizes[0]];
        BmobQuery<User> rooms = new BmobQuery<User>();
        rooms.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null){
                    if (e == null) {
                        int n = list.size();
                        for (int i = 0; i < n; i++) {
                            if (username == null || list.get(i).getUserName().equalsIgnoreCase(username)) {
                                lists[i] = list.get(i);
                            }
                        }
                        Arrays.sort(lists, 0, n);		// 新加了一个排序函数
                        String str = "";
                        for (int i = 0; i < n; i++) {
                            str = str + lists[i].getUserEmail() + " " + lists[i].getUserInfo() +" "+
                                    lists[i].getUserName() + " " + lists[i].getUserPass() + " " +
                                    lists[i].getUserPhone() + " " + lists[i].getUserSex() + " "+
                                    lists[i].getObjectId() + "\n";
                            System.out.println(lists[i].getUserEmail() + "," + lists[i].getUserInfo() +","+
                                    lists[i].getUserName() + "," + lists[i].getUserPass() + "," +
                                    lists[i].getUserPhone() + "," + lists[i].getUserSex() + ","+
                                    lists[i].getObjectId() + "");
                        }                  //  textView.setText(str);
                    } else {
                        System.out.println(e.getErrorCode());
                    }

                }
            }
        });
        return lists;
    }

    public RoomUser[] searchRoomUser(String username, String roomid){
        //查
        roomuser = new RoomUser[0];
        int[] na = new int[1];
        na[0] = 0;
        BmobQuery<RoomUser> rooms = new BmobQuery<RoomUser>();
        rooms.findObjects(new FindListener<RoomUser>() {
            @Override
            public void done(List<RoomUser> list, BmobException e) {
                na[0] = 1;
                if (e == null){
                    if (e == null) {
                        int n = list.size();

                        for (int i = 0; i < n; i++) {
                            roomuser[i] = list.get(i);
                        }

                        Arrays.sort(roomuser, 0, n);		// 新加了一个排序函数
                        String str = "";
                        for (int i = 0; i < n; i++) {
                            str = str + roomuser[i].getRoomId() + " " + roomuser[i].getUserName() +
                                    " " + roomuser[i].getObjectId() + "\n";
                            System.out.println(roomuser[i].getRoomId() + "," + roomuser[i].getUserName() +
                                    "," + roomuser[i].getObjectId() + "");
                        }                  //  textView.setText(str);
                    } else {
                        System.out.println(e.getErrorCode());
                    }

                }
            }
        });
        RoomUser[] ro = new RoomUser[roomuser.length];
        int size = 0;
        for (int i = 0;i < roomuser.length;i++){
            if (roomid == null || roomuser[i].getRoomId().equalsIgnoreCase(roomid)){
                if (username == null || roomuser[i].getUserName().equalsIgnoreCase(username)) {
                    ro[size] = roomuser[i];
                    size++;
                }
            }
        }
        roomuser = new RoomUser[size];
        for (int i = 0;i < size;i++){
            roomuser[i] = ro[i];
        }
        return roomuser;
    }

    //增
    /**
     *
     * @param room          Room
     */
    public void add(Room room){
        room.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
//                            tv_show.setText("添加数据成功，返回objectId为：" + objectId);
//                            temp_objectId = objectId;
//                    System.out.print(e.getMessage());
                    System.out.print("成功");
                } else {
//                            S
                    System.out.print(e.getMessage());
                }
            }
        });
    }
    /**
     *
     * @param community          Community
     */
    public void add(Community community){
        community.save(new SaveListener<String>() {
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
    /**
     *
     * @param message              Message
     */
    public void add(Message message){
        message.save(new SaveListener<String>() {
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
    /**
     *
     * @param user                  User
     */
    public void add(User user){
        user.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    System.out.print("a");
//                            tv_show.setText("添加数据成功，返回objectId为：" + objectId);
//                            temp_objectId = objectId; result[0] = true;
                } else {
                    System.out.print("b");
//                    result[0] = true;
//                            tv_show.setText("创建数据失败：" + e.getMessage());
                }
            }
        });
    }
    public void add(RoomUser roomuser){
        roomuser.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
//                            tv_show.setText("添加数据成功，返回objectId为：" + objectId);
//                            temp_objectId = objectId; result[0] = true;
                } else {
//                    result[0] = true;
//                            tv_show.setText("创建数据失败：" + e.getMessage());
                }
            }
        });
    }

    //删
    /**
     * 所有参数都能为空，都为空时是删除此表内所有信息，请慎重填写。
     *                       删除Room
     * @param roomid        房间ID
     */
    public void deleteRoom(String roomid){
        List<String> id = getRoomObjectIDbyRoomId(roomid);
        for (String ida : id){
            final Room book = new Room();
                book.setObjectId(ida);
                book.delete(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
//                                  tv_show.setText("数据删除成功 " + book.getUpdatedAt());
                        } else {
//                                   tv_show.setText("数据删除失败 " + e.getMessage());
                        }
                    }
                });

        }
    }
    /**
     * 所有参数都能为空，都为空时是删除此表内所有信息，请慎重填写。
     *                        删除Community
     * @param username      用户名
     * @param roomid        房间ID
     * @param createdat     创建时间
     */
    public void deleteCommunity(String username,String roomid,Date createdat){
        List<String> id = getCommunityId(username,roomid,createdat);
        for (String ida : id){
            final Community book = new Community();
            book.setObjectId(ida);
            book.delete(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
//                                  tv_show.setText("数据删除成功 " + book.getUpdatedAt());
                    } else {
//                                   tv_show.setText("数据删除失败 " + e.getMessage());
                    }
                }
            });
        }
    }
    /**
     * 所有参数都能为空，都为空时是删除此表内所有信息，请慎重填写。
     *                        删除message
     * @param username      用户名
     * @param roomid        房间ID
     * @param createdat     创建时间
     */
    public void deleteMessage(String username,String roomid,Date createdat){
        List<String> id = getMessageId(username,roomid,createdat);
        for (String ida : id){
            final Message book = new Message();
            book.setObjectId(ida);
            book.delete(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
//                                  tv_show.setText("数据删除成功 " + book.getUpdatedAt());
                    } else {
//                                   tv_show.setText("数据删除失败 " + e.getMessage());
                    }
                }
            });
        }
    }
    /**
     * 所有参数都能为空，都为空时是删除此表内所有信息，请慎重填写。
     *                        删除User
     * @param username      用户名
     */
    public void deleteUser(String username) {
        List<String> id = getUserId(username);
        for (String ida : id) {
            final User book = new User();
            book.setObjectId(ida);
            book.delete(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
//                                  tv_show.setText("数据删除成功 " + book.getUpdatedAt());
                    } else {
//                                   tv_show.setText("数据删除失败 " + e.getMessage());
                    }
                }
            });
        }
    }
    public void deleteRoomUser(String username,String roomid){
        List<String> id = getRoomUserId(username,roomid);
        for (String ida : id){
            final Message book = new Message();
            book.setObjectId(ida);
            book.delete(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
//                                  tv_show.setText("数据删除成功 " + book.getUpdatedAt());
                    } else {
//                                   tv_show.setText("数据删除失败 " + e.getMessage());
                    }
                }
            });
        }
    }


    //改

    /**
     *
     * @param room          Room
     */
    public void updata(Room room){
        List<String> id = getRoomObjectIDbyRoomId(room.getRoomId());
        if (id.size()==0){
            return;
        }
        for (String s:id){
            room.update(s, new UpdateListener() {
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
    /**
     *
     * @param community          Community
     */
    public void updata(Community community){
        List<String> id = getCommunityId(community.getUserName(),community.getRoomId(),community.getCreatedAt());
        if (id.size()==0){
            return;
        }
        for (String s:id){
            community.update(s, new UpdateListener() {
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
    /**
     *
     * @param message              Message
     */
    public void updata(Message message){
        List<String> id = getMessageId(message.getUserName(),message.getRoomId(),message.getCreatedAt());
        if (id.size()==0){
            return;
        }
        for (String s:id){
            message.update(s, new UpdateListener() {
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
    /**
     *
     * @param user                  User
     */
    public void updata(User user){
        List<String> id = getUserId(user.getUserName());
        if (id.size()==0){
            return;
        }
        for (String s:id){
            user.update(s, new UpdateListener() {
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


    private List<Map<String,String>> getIDbyRoomId(String id){
        List<Map<String,String>> objId = new ArrayList<Map<String, String>>();
        Room[] li = searchRoom(id);
        Community[] ci = searchCommunity(null,id, (Date) null);
        Message[] mi = searchMessage(null,id,(Date) null);
        if (true) {
            Map<String,String> map= new HashMap<String, String>();
            for (int i = 0; i < li.length; i++) {
                if (id.equalsIgnoreCase(li[i].getRoomId())) {
                    map.put("Room", li[i].getRoomId());
                }
            }
            objId.add(map);
        }
        if (true) {
            Map<String, String> map = new HashMap<String, String>();
            for (int i = 0; i < ci.length; i++) {
                if (id.equalsIgnoreCase(ci[i].getRoomId())) {
                    map.put("Community", ci[i].getRoomId());
                }
            }
            objId.add(map);
        }
        if (true) {
            Map<String, String> map = new HashMap<String, String>();
            for (int i = 0; i < mi.length; i++) {
                if (id.equalsIgnoreCase(mi[i].getRoomId())) {
                    map.put("Message", mi[i].getRoomId());
                }
            }
            objId.add(map);
        }
        return objId;
    }
    private List<String> getRoomObjectIDbyRoomId(String id){
        Room[] li = searchRoom(id);
        List<String> ll = new ArrayList<String>();
        for (int i = 0; i < li.length; i++) {
            if (id == null || id.equalsIgnoreCase(li[i].getRoomId())) {
                ll.add(li[i].getRoomId());
            }
        }
        return ll;
    }
    private List<String> getCommunityId(String username, String roomid, Date createdat){
        Community[] ci = searchCommunity(username,roomid,createdat);
        List<String> objid = new ArrayList<String >();
        for (Community c : ci){
            if ((username == null || c.getUserName().equalsIgnoreCase(username)) &&
                    (roomid == null || c.getRoomId().equalsIgnoreCase(roomid)) &&
                    (createdat == null || c.getCreatedAt().equalsIgnoreCase(createdat.toString()))){
                objid.add(c.getObjectId());
            }
        }
        return objid;
    }
    private List<String> getCommunityId(String username, String roomid, String createdat){
        Community[] ci = searchCommunity(username,roomid,createdat);
        List<String> objid = new ArrayList<String >();
        for (Community c : ci){
            if ((username == null || c.getUserName().equalsIgnoreCase(username)) &&
                    (roomid == null || c.getRoomId().equalsIgnoreCase(roomid)) &&
                    (createdat == null || c.getCreatedAt().equalsIgnoreCase(createdat))){
                objid.add(c.getObjectId());
            }
        }
        return objid;
    }
    private List<String> getMessageId(String username, String roomid, Date createdat){
        Message[] ci = searchMessage(username,roomid,createdat);
        List<String> objid = new ArrayList<String >();
        for (Message c : ci){
            if ((username == null || c.getUserName().equalsIgnoreCase(username)) &&
                    (roomid == null || c.getRoomId().equalsIgnoreCase(roomid)) &&
                    (createdat == null || c.getCreatedAt().equalsIgnoreCase(createdat.toString()))){
                objid.add(c.getObjectId());
            }
        }
        return objid;
    }
    private List<String> getMessageId(String username, String roomid, String createdat){
        Message[] ci = searchMessage(username,roomid,createdat);
        List<String> objid = new ArrayList<String >();
        for (Message c : ci){
            if ((username == null || c.getUserName().equalsIgnoreCase(username)) &&
                    (roomid == null || c.getRoomId().equalsIgnoreCase(roomid)) &&
                    (createdat == null || c.getCreatedAt().equalsIgnoreCase(createdat))){
                objid.add(c.getObjectId());
            }
        }
        return objid;
    }
    private List<String> getUserId(String username){
        User[] ci = searchUser(username);
        List<String> objid = new ArrayList<String >();
        for (User c : ci){
            if (username == null || c.getUserName().equalsIgnoreCase(username)){
                objid.add(c.getObjectId());
            }
        }
        return objid;
    }
    private List<String> getRoomUserId(String username, String roomid){
        RoomUser[] ci = searchRoomUser(username,roomid);
        List<String> objid = new ArrayList<String>();
        for (RoomUser c : ci){
            if ((username == null || c.getUserName().equalsIgnoreCase(username)) &&
                    (roomid == null || c.getRoomId().equalsIgnoreCase(roomid))){
                objid.add(c.getObjectId());
            }
        }
        return objid;
    }

//    public boolean add(String table,Object o){
//        switch (table){
//            case "ROOM":
//                add((Room)o);
//                return true;
//            case "COMMUNITY":
//                add((Community)o);
//                return true;
//            case "MESSAGE":
//                add((Message)o);
//                return true;
//            case "USER":
//                add((User)o);
//                return true;
//            default:
//                return false;
//        }
//    }
//
//    public boolean delete(String table,)
}
