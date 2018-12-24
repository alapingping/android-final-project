package com.sports.yue.UI.UI.Database_operation;

import android.os.Bundle;

import com.sports.yue.UI.UI.Adapter.GroupAdapter;
import com.sports.yue.UI.UI.fragment.My_groupFragment;
import com.sports.yue.UI.UI.local_db.DbManager;
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

import static com.baidu.location.d.g.an;

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


    //查
    /*
    *   所有参数都能为空，都为空时是查找此表内所有信息。
    */

    public void searchRoom(){
        //查
        BmobQuery<Room> newroom = new BmobQuery<Room>();
        newroom.findObjects(new FindListener<Room>() {
            @Override
            public void done(List<Room> list, BmobException e) {
                if (e == null){
                    if (e == null) {
                        for (Room room : list){
                            if(DbManager.getDbManager().selectRoom(room.getRoomId()).length==0)
                                DbManager.getDbManager().insert(room);
                        }                 //  textView.setText(str);
                    } else {
                        System.out.println(e.getErrorCode());
                    }
                }
            }
        });
    }

    public void searchCommunity(){
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
                        for (Community community : list){
                            DbManager.getDbManager().insert(community);
                        }
                        //  textView.setText(str);
                    } else {
                        System.out.println(e.getErrorCode());
                    }

                }
            }
        });
    }

    public void searchMessage(){
        //查
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
                        for (Message mes : list){
                            DbManager.getDbManager().insert(mes);
                        }
                        //  textView.setText(str);
                    } else {
                        System.out.println(e.getErrorCode());
                    }

                }
            }
        });
    }

    public void searchUser(){
        //查
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
                        for (User user : list){
                            DbManager.getDbManager().insert(user);
                        }
                        //  textView.setText(str);
                    } else {
                        System.out.println(e.getErrorCode());
                    }

                }
            }
        });
    }

    public void searchRoomUser(){
        //查
        BmobQuery<RoomUser> rooms = new BmobQuery<RoomUser>();
        rooms.findObjects(new FindListener<RoomUser>() {
            @Override
            public void done(List<RoomUser> list, BmobException e) {
                if (e == null){
                    if (e == null) {
                        for (RoomUser roomUser : list){
                            DbManager.getDbManager().insert(roomUser);
                        }                //  textView.setText(str);
                    } else {
                        System.out.println(e.getErrorCode());
                    }
                }
            }
        });
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
        Room[] li = DbManager.getDbManager().selectRoom(id);
        Community[] ci = DbManager.getDbManager().selectCommunity(null,id, (Date) null);
        Message[] mi = DbManager.getDbManager().selectMessage(null,id,(Date) null);
        if (li.length>0) {
            Map<String,String> map= new HashMap<String, String>();
            for (int i = 0; i < li.length; i++) {
                if (id.equalsIgnoreCase(li[i].getRoomId())) {
                    map.put("Room", li[i].getObjectId());
                }
            }
            objId.add(map);
        }
        if (ci.length>0) {
            Map<String, String> map = new HashMap<String, String>();
            for (int i = 0; i < ci.length; i++) {
                if (id.equalsIgnoreCase(ci[i].getRoomId())) {
                    map.put("Community", ci[i].getObjectId());
                }
            }
            objId.add(map);
        }
        if (mi.length>0) {
            Map<String, String> map = new HashMap<String, String>();
            for (int i = 0; i < mi.length; i++) {
                if (id.equalsIgnoreCase(mi[i].getRoomId())) {
                    map.put("Message", mi[i].getObjectId());
                }
            }
            objId.add(map);
        }
        return objId;
    }
    private List<String> getRoomObjectIDbyRoomId(String id){
        Room[] li = DbManager.getDbManager().selectRoom(id);
        List<String> ll = new ArrayList<String>();
        for (int i = 0; i < li.length; i++) {
            if (id == null || id.equalsIgnoreCase(li[i].getRoomId())) {
                ll.add(li[i].getObjectId());
            }
        }
        return ll;
    }
    private List<String> getCommunityId(String username, String roomid, Date createdat){
        Community[] ci = DbManager.getDbManager().selectCommunity(username,roomid,createdat);
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
        Community[] ci = DbManager.getDbManager().selectCommunity(username,roomid,createdat);
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
        Message[] ci = DbManager.getDbManager().selectMessage(username,roomid,createdat);
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
        Message[] ci = DbManager.getDbManager().selectMessage(username,roomid,createdat);
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
        User[] ci = DbManager.getDbManager().selectUser(username);
        List<String> objid = new ArrayList<String >();
        for (User c : ci){
            if (username == null || c.getUserName().equalsIgnoreCase(username)){
                objid.add(c.getObjectId());
            }
        }
        return objid;
    }
    private List<String> getRoomUserId(String username, String roomid){
        RoomUser[] ci = DbManager.getDbManager().selectRoomUser(username,roomid);
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
