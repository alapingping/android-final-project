package com.sports.yue.UI.UI.Database_operation;

import com.sports.yue.UI.UI.models.Community;
import com.sports.yue.UI.UI.models.Message;
import com.sports.yue.UI.UI.models.Room;
import com.sports.yue.UI.UI.models.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Room[] searchRoom(String roomid){
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
                            if (roomid == null || list.get(i).getRoomId().equalsIgnoreCase(roomid)) {
                                lists[i] = list.get(i);
                            }
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
    private Community[] searchCommunity(String username,String roomid,Date createdat){
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
                                    " " + lists[i].getCreatedAt()+ " " + lists[i].getLike() +
                                    " " + lists[i].getObjectId() + "\n";
                            System.out.println(lists[i].getEmail() + "," + lists[i].getRoomId()+
                                    "," + lists[i].getUserName()+ "," + lists[i].getVideo()+
                                    "," + lists[i].getCreatedAt()+ "," + lists[i].getLike()+
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
    private Community[] searchCommunity(String username,String roomid,String createdat){
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
                                    " " + lists[i].getCreatedAt()+ " " + lists[i].getLike() +
                                    " " + lists[i].getObjectId() + "\n";
                            System.out.println(lists[i].getEmail() + "," + lists[i].getRoomId()+
                                    "," + lists[i].getUserName()+ "," + lists[i].getVideo()+
                                    "," + lists[i].getCreatedAt()+ "," + lists[i].getLike()+
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
    private Message[] searchMessage(String username,String roomid,Date createdat){
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
    private Message[] searchMessage(String username,String roomid,String createdat){
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
    private User[] searchUser(String username){
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
    private void add(Community room){
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
    private void add(Message room){
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
    private void add(User room){
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
    /*
     *   所有参数都能为空，都为空时是删除此表内所有信息，请慎重填写。
     */
    private void deleteRoom(String roomid){
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
    private void deleteCommunity(String username,String roomid,Date createdat){
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
    private void deleteMessage(String username,String roomid,Date createdat){
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
    private void deleteUser(String username) {
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


    //改
    private void updata(Room room){
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
    private void updata(Community room){
        List<String> id = getCommunityId(room.getUserName(),room.getRoomId(),room.getCreatedAt());
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
    private void updata(Message room){
        List<String> id = getMessageId(room.getUserName(),room.getRoomId(),room.getCreatedAt());
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
    private void updata(User room){
        List<String> id = getUserId(room.getUserName());
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
}
