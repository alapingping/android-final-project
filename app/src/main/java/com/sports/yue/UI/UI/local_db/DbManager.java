package com.sports.yue.UI.UI.local_db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sports.yue.R;
import com.sports.yue.UI.UI.models.Community;
import com.sports.yue.UI.UI.models.Message;
import com.sports.yue.UI.UI.models.Room;
import com.sports.yue.UI.UI.models.RoomUser;
import com.sports.yue.UI.UI.models.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DbManager {

    private DBHelper dbHelper;
    private static DbManager Db_M = null;
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private DbManager(Context context){

        //在这里创建User表
        dbHelper=new DBHelper(context);
        SQLiteDatabase db1 = dbHelper.getWritableDatabase();
        String sql = "CREATE TABLE IF NOT EXISTS USER ("
                + "UserSex varchar DEFAULT '', UserEmail varchar DEFAULT '',UserPhone varchar DEFAULT '',UserPass varchar DEFAULT '',"
                + "UserName varchar PRIMARY KEY,UserInfo varchar DEFAULT '',createdAt datetime DEFAULT (datetime(CURRENT_TIMESTAMP,'localtime')),"
                + "updatedAt datetime DEFAULT (datetime(CURRENT_TIMESTAMP,'localtime')))";
        db1.execSQL(sql);
        db1.close();

        //这里创建Room表
        sql = "CREATE TABLE IF NOT EXISTS ROOM ("
                + "ActivityTime varcahr DEFAULT '', ActivityPosition varchar DEFAULT '',RoomMaxPeople number DEFAULT 0,RoomType varchar DEFAULT '',"
                + "RoomDescription varchar DEFAULT '',RoomCreator varchar DEFAULT '',RoomName varchar DEFAULT '',RoomId varchar PRIMARY KEY,CurrentUserNum number DEFAULT '',"
                + "createdAt datetime DEFAULT (datetime(CURRENT_TIMESTAMP,'localtime')),updateAt datetime DEFAULT (datetime(CURRENT_TIMESTAMP,'localtime')))";
        SQLiteDatabase db2 = dbHelper.getWritableDatabase();
        db2.execSQL(sql);
        db2.close();

        //这里创建Message表
        sql = "CREATE TABLE IF NOT EXISTS MESSAGE ("
                + "UserName varchar DEFAULT '', RoomId varchar DEFAULT '',MessageContent varchar DEFAULT '',"
                + "createdAt datetime DEFAULT (datetime(CURRENT_TIMESTAMP,'localtime')),"
                + "updatedAt datetime DEFAULT (datetime(CURRENT_TIMESTAMP,'localtime')), PRIMARY KEY(UserName,RoomId,createdAt))";
        SQLiteDatabase db3 = dbHelper.getWritableDatabase();
        db3.execSQL(sql);
        db3.close();

        //这里创建Community表
        sql = "CREATE TABLE IF NOT EXISTS COMMUNITY ("
                + "UserName varchar DEFAULT '', Likes number DEFAULT 0,Video varchar DEFAULT '',RoomId varcahr DEFAULT '',"
                + "Email varchar DEFAULT '',createdAT datetime DEFAULT (datetime(CURRENT_TIMESTAMP,'localtime')),"
                + "updatedAt datetime DEFAULT (datetime(CURRENT_TIMESTAMP,'localtime')), PRIMARY KEY (UserName,createdAT))";
        SQLiteDatabase db4 = dbHelper.getWritableDatabase();
        db4.execSQL(sql);
        db4.close();

        sql = "CREATE TABLE IF NOT EXISTS CURRENTUSER ("
                + "UserName varchar PRIMARY KEY DEFAULT '',Password varchar DEFAULT '')";
        SQLiteDatabase db5 = dbHelper.getWritableDatabase();
        db5.execSQL(sql);
        db5.close();

        List<String[]> se = select(new String[]{"UserName"},new String[]{"CURRENTUSER"},null,null);
        if (se.size() == 0){
            insert("CURRENTUSER",new String[]{"UserName","Password"},new String[]{"-1","-1"});
        }

        sql = "CREATE TABLE IF NOT EXISTS ROOMUSER ("
                + "RoomId varchar,UserName varchar,PRIMARY KEY (RoomId,UserName))";
        SQLiteDatabase db6 = dbHelper.getWritableDatabase();
        db6.execSQL(sql);
        db6.close();
    }

    public static DbManager getDbManager(){
        return Db_M;
    }

    public static DbManager getDb_M(Context context){
        if (Db_M == null){
            Db_M = new DbManager(context);
        }
        return Db_M;
    }

    /**
     * @param tablename the name of table
     * @param field the field you want to set
     * @param fieldvalue the value of each field
     * @return long(Object_id) the object id for the data you have insert
     */
    public boolean insert(String tablename,String[] field,String[] fieldvalue){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //打开连接，写入数据
        ContentValues values=new ContentValues();
        for (int i = 0;i < field.length;i++){
            values.put(field[i],fieldvalue[i]);
        }

        //
        long student_Id=db.insert(tablename,null,values);
        db.close();
        if (student_Id > 0)
            return true;
        return false;
    }

    public boolean insert(User o){
        return insert("USER",
                new String[]{"UserSex", "UserEmail","UserPhone","UserPass","UserName","UserInfo","createdAt","updatedAt"},
                new String[]{((User)o).getUserSex(),((User)o).getUserEmail(),((User)o).getUserPhone(),((User)o).getUserPass(),((User)o).getUserName(),
                        ((User)o).getUserInfo(),((User)o).getCreatedAt(),((User)o).getUpdatedAt()});
    }
    public boolean insert(Community o){
        return insert("COMMUNITY",
                new String[]{"UserName", "Likes","Video","RoomId","Email","createdAT","updatedAt"},
                new String[]{((Community)o).getUserName(),((Community)o).getLikes()+"",((Community)o).getVideo(),((Community)o).getRoomId(),
                        ((Community)o).getEmail(),((Community)o).getCreatedAt(),((Community)o).getUpdatedAt()});
    }
    public boolean insert(Message o){
        return insert("MESSAGE",
                new String[]{"UserName", "RoomId","MessageContent","createdAT","updatedAt"},
                new String[]{((Message)o).getUserName(),((Message)o).getRoomId()+"",((Message)o).getMessageContent(),
                        ((Message)o).getCreatedAt(),((Message)o).getUpdatedAt()});
    }
    public boolean insert(Room o){
        return insert("ROOM",
                new String[]{"ActivityTime", "ActivityPosition","RoomMaxPeople","RoomType","RoomDescription",
                        "RoomCreator","RoomName","RoomId","CurrentUserNum","createdAt","updateAt"},
                new String[]{((Room)o).getActivityTime() + "",((Room)o).getActivityPosition()+"",((Room)o).getRoomMaxPeople()+ "", ((Room)o).getRoomType(),
                        ((Room)o).getRoomDescription(),((Room)o).getRoomCreator(),((Room)o).getRoomName()+"",((Room)o).getRoomId(),((Room)o).getCurrentUserNum(),
                        ((Room)o).getCreatedAt(),((Room)o).getUpdatedAt()});
    }
    public boolean insert(RoomUser o){
        return insert("ROOMUSER",
                new String[]{"RoomId", "UserName"},
                new String[]{((RoomUser)o).getRoomId(),((RoomUser)o).getUserName()});
    }

    public boolean insert(User o,String s){
        return insert("USER",
                new String[]{"UserSex", "UserEmail","UserPhone","UserPass","UserName","UserInfo"},
                new String[]{((User)o).getUserSex(),((User)o).getUserEmail(),((User)o).getUserPhone(),((User)o).getUserPass(),((User)o).getUserName(),
                        ((User)o).getUserInfo()});
    }
    public boolean insert(Community o,String s){
        return insert("COMMUNITY",
                new String[]{"UserName", "Likes","Video","RoomId","Email"},
                new String[]{((Community)o).getUserName(),((Community)o).getLikes()+"",((Community)o).getVideo(),((Community)o).getRoomId(),
                        ((Community)o).getEmail()});
    }
    public boolean insert(Message o,String s){
        return insert("MESSAGE",
                new String[]{"UserName", "RoomId","MessageContent"},
                new String[]{((Message)o).getUserName(),((Message)o).getRoomId()+"",((Message)o).getMessageContent()});
    }
    public boolean insert(Room o,String s){
        return insert("ROOM",
                new String[]{"ActivityTime", "ActivityPosition","RoomMaxPeople","RoomType","RoomDescription",
                        "RoomCreator","RoomName","RoomId","CurrentUserNum"},
                new String[]{((Room)o).getActivityTime() + "",((Room)o).getActivityPosition()+"",((Room)o).getRoomMaxPeople()+ "", ((Room)o).getRoomType(),
                        ((Room)o).getRoomDescription(),((Room)o).getRoomCreator(),((Room)o).getRoomName()+"",((Room)o).getRoomId(),((Room)o).getCurrentUserNum()});
    }
    public boolean insert(RoomUser o,String s){
        return insert("ROOMUSER",
                new String[]{"RoomId", "UserName"},
                new String[]{((RoomUser)o).getRoomId(),((RoomUser)o).getUserName()});
    }



    /**
     * @param tablename the name of table
     * @param field the field you want to set,if null delete all
     * @param fieldvalue the value of each field
     */
    public void delete(String tablename,String[] field,String[] fieldvalue){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (field == null){
            db.delete(tablename,null, null);
            return;
        }
        String whereClause = "";
        for (int i = 0;i < field.length;i++){
            if (i == field.length - 1){
                whereClause += field[i] + "=?";
                break;
            }
            whereClause += field[i] + "=?,";
        }
        db.delete(tablename,whereClause, fieldvalue);
        db.close();
    }

    /**
     * @param tablename the name of table
     * @param field the field you want to set
     * @param fieldvalue the value of each field
     * @param selectfield this can help you find the data you want to updata, if null update all
     * @param selectfieldvalue this can help you find the data you want to updata
     */
    public void update(String tablename,String[] field,String[] fieldvalue,String[] selectfield,String[] selectfieldvalue){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        for (int i = 0;i < field.length;i++){
            values.put(field[i],fieldvalue[i]);
        }
        if (selectfield == null){
            db.update(tablename,values,null,null);
            return;
        }
        String whereClause = "";
        for (int i = 0;i < selectfield.length;i++){
            if (i == selectfield.length - 1){
                whereClause += selectfield[i] + "=?";
                break;
            }
            whereClause += selectfield[i] + "=?,";
        }
        db.update(tablename,values,whereClause,selectfieldvalue);
        db.close();
    }

    public void update(User o){
        update("USER",
                new String[]{"UserSex", "UserEmail","UserPhone","UserPass","UserName","UserInfo","createdAt","updatedAt"},
                new String[]{((User)o).getUserSex(),((User)o).getUserEmail(),((User)o).getUserPhone(),((User)o).getUserPass(),((User)o).getUserName(),
                        ((User)o).getUserInfo(),((User)o).getCreatedAt(),((User)o).getUpdatedAt()},
                new String[]{"UserName"},
                new String[]{((User)o).getUserName()});
    }
    public void update(Message o){
        update("MESSAGE",
                new String[]{"UserName", "RoomId","MessageContent","createdAT","updatedAt"},
                new String[]{((Message)o).getUserName(),((Message)o).getRoomId()+"",((Message)o).getMessageContent(),
                        ((Message)o).getCreatedAt(),((Message)o).getUpdatedAt()},
                new String[]{"UserName","RoomId","createdAT"},
                new String[]{((Message)o).getUserName(),((Message)o).getRoomId(),((Message)o).getCreatedAt()});
    }
    public void update(Room o){
        update("ROOM",
                new String[]{"ActivityTime", "ActivityPosition","RoomMaxPeople","RoomType","RoomDescription",
                        "RoomCreator","RoomName","RoomId","CurrentUserNum","createdAt","updateAt"},
                new String[]{((Room)o).getActivityTime() + "",((Room)o).getActivityPosition()+"",((Room)o).getRoomMaxPeople()+ "", ((Room)o).getRoomType(),
                        ((Room)o).getRoomDescription(),((Room)o).getRoomCreator(),((Room)o).getRoomName()+"",((Room)o).getRoomId(),((Room)o).getCurrentUserNum(),
                        ((Room)o).getCreatedAt(),((Room)o).getUpdatedAt()},
                new String[]{"RoomId"},
                new String[]{((Room)o).getRoomId()});
    }
    public void update(Community o){
        update("COMMUNITY",
                new String[]{"UserName", "Likes","Video","RoomId","Email","createdAT","updatedAt"},
                new String[]{((Community)o).getUserName(),((Community)o).getLikes()+"",((Community)o).getVideo(),((Community)o).getRoomId(),
                        ((Community)o).getEmail(),((Community)o).getCreatedAt(),((Community)o).getUpdatedAt()},
                new String[]{"UserName","RoomId","createdAT"},
                new String[]{((Community)o).getUserName(),((Community)o).getRoomId(),((Community)o).getCreatedAt()});
    }

    /**
     * @param selectlist your selectClause
     * @param formlist your fromClause
     * @param wherelist your whereClause, it can be null.
     * @param wherelistvalue the value of whereClause "?"
     * @return String[] the result set of select operation
     */
    public ArrayList<String[]> select(String[] selectlist, String[] formlist, String[] wherelist, String[] wherelistvalue){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selectQuery="SELECT * ";
        for (int i = 0;i < formlist.length;i++){
            if (i == 0){
                selectQuery += "FROM " + formlist[i];
            }else {
                selectQuery += "," + formlist[i];
            }
        }
        if (wherelist != null) {
            for (int i = 0; i < wherelist.length; i++) {
                if (i == 0) {
                    selectQuery += " WHERE " + wherelist[i] + "=?";
                } else {
                    selectQuery += "," + wherelist[i] + "=?";
                }
            }
        }

        ArrayList<String[]> ResultSet=new ArrayList<String[]>();
        Cursor cursor=db.rawQuery(selectQuery,wherelistvalue);

        if (selectlist == null){
            if(cursor.moveToFirst()){
                do{
                    String[] message=new String[cursor.getColumnCount()];
                    for (int i = 0;i < cursor.getColumnCount();i++){
                        message[i] = cursor.getString(i);
                    }
                    ResultSet.add(message);
                }while(cursor.moveToNext());
            }
        }else {
            if(cursor.moveToFirst()){
                do{
                    String[] message=new String[selectlist.length];
                    for (int i = 0;i < selectlist.length;i++){
                        message[i] = cursor.getString(cursor.getColumnIndex(selectlist[i]));
                    }
                    ResultSet.add(message);
                }while(cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return ResultSet;
    }

    public Room[] selectRoom(String roomid){

        String[] where = null;
        String[] value = null;
        if (roomid != null){
            where = addwhere(where,"RoomId");
            value = addwhere(value,roomid);
        }
        List<String[]> ro = select(null,new String[]{"ROOM"},where,value);
        Room[] rooms = new Room[ro.size()];
        for (int i = 0;i < ro.size();i++){
            rooms[i] = new Room();
            rooms[i].setActivityTime(ro.get(i)[0]);
            rooms[i].setActivityPosition(ro.get(i)[1]);
            rooms[i].setRoomMaxPeople(Integer.valueOf(ro.get(i)[2]));
            rooms[i].setRoomType(ro.get(i)[3]);
            rooms[i].setRoomDescription(ro.get(i)[4]);
            rooms[i].setRoomCreator(ro.get(i)[5]);
            rooms[i].setRoomName(ro.get(i)[6]);
            rooms[i].setRoomId(ro.get(i)[7]);
            rooms[i].setCurrentUserNum(ro.get(i)[8]);
            try {
                rooms[i].setCreateAt(format.parse(ro.get(i)[9]));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            rooms[i].setUpdatedAt(ro.get(i)[10]);
        }
        return rooms;
    }
    public RoomUser[] selectRoomUser(String roomid,String username){

        String[] where = null;
        String[] value = null;
        if (roomid != null){
            where = addwhere(where,"RoomId");
            value = addwhere(value,roomid);
        }
        if (username != null){
            where = addwhere(where,"UserName");
            value = addwhere(value,username);
        }
        List<String[]> ro = select(null,new String[]{"ROOMUSER"},where,value);
        RoomUser[] rooms = new RoomUser[ro.size()];
        for (int i = 0;i < ro.size();i++){
            rooms[i] = new RoomUser();
            rooms[i].setRoomId(ro.get(i)[0]);
            rooms[i].setUserName(ro.get(i)[1]);
        }
        return rooms;
    }
    public Community[] selectCommunity(String username,String roomid,Date createAt){
        String[] where = null;
        String[] value = null;
        if (roomid != null){
            where = addwhere(where,"RoomId");
            value = addwhere(value,roomid);
        }
        if (username != null){
            where = addwhere(where,"UserName");
            value = addwhere(value,username);
        }
        if (createAt != null){
            where = addwhere(where,"createAt");
            value = addwhere(value,createAt + "");
        }
        List<String[]> ro = select(null,new String[]{"COMMUNITY"},where,value);
        Community[] rooms = new Community[ro.size()];
        for (int i = 0;i < ro.size();i++){
            rooms[i] = new Community();
            rooms[i].setUserName(ro.get(i)[0]);
            rooms[i].setLikes(Integer.valueOf(ro.get(i)[1]));
            rooms[i].setVideo(ro.get(i)[2]);
            rooms[i].setRoomId(ro.get(i)[3]);
            rooms[i].setEmail(ro.get(i)[4]);
            try {
                rooms[i].setCreateAt(format.parse(ro.get(i)[5]));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            rooms[i].setUpdatedAt(ro.get(i)[6]);
        }
        return rooms;
    }
    public Community[] selectCommunity(String username,String roomid,String createAt){
        String[] where = null;
        String[] value = null;
        if (roomid != null){
            where = addwhere(where,"RoomId");
            value = addwhere(value,roomid);
        }
        if (username != null){
            where = addwhere(where,"UserName");
            value = addwhere(value,username);
        }
//        if (createAt != null){
//            where = addwhere(where,"createAt");
//            value = addwhere(value,createAt);
//        }
        List<String[]> ro = select(null,new String[]{"COMMUNITY"},where,value);

        int size = 0;
        for (int i = 0;i < ro.size();i++){
            if (ro.get(i)[5].equalsIgnoreCase(createAt)) {
                size++;
            }
        }
        Community[] rooms = new Community[size];
        for (int i = 0;i < ro.size();i++){
            if (ro.get(i)[5].equalsIgnoreCase(createAt)) {
                rooms[i] = new Community();
                rooms[i].setUserName(ro.get(i)[0]);
                rooms[i].setLikes(Integer.valueOf(ro.get(i)[1]));
                rooms[i].setVideo(ro.get(i)[2]);
                rooms[i].setRoomId(ro.get(i)[3]);
                rooms[i].setEmail(ro.get(i)[4]);
                try {
                    rooms[i].setCreateAt(format.parse(ro.get(i)[5]));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                rooms[i].setUpdatedAt(ro.get(i)[6]);
            }
        }
        return rooms;
    }
    public User[] selectUser(String username){
        String[] where = null;
        String[] value = null;
        if (username != null){
            where = addwhere(where,"UserName");
            value = addwhere(value,username);
        }
        List<String[]> ro = select(null,new String[]{"USER"},where,value);
        User[] rooms = new User[ro.size()];
        for (int i = 0;i < ro.size();i++){
            rooms[i] = new User();
            rooms[i].setUserSex(ro.get(i)[0]);
            rooms[i].setUserEmail(ro.get(i)[1]);
            rooms[i].setUserPhone(ro.get(i)[2]);
            rooms[i].setUserPass(ro.get(i)[3]);
            rooms[i].setUserName(ro.get(i)[4]);
            rooms[i].setUserInfo(ro.get(i)[5]);
            try {
                rooms[i].setCreateAt(format.parse(ro.get(i)[6]));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            rooms[i].setUpdatedAt(ro.get(i)[7]);
        }
        return rooms;
    }
    public Message[] selectMessage(String username,String roomid,Date createAt){
        String[] where = null;
        String[] value = null;
        if (roomid != null){
            where = addwhere(where,"RoomId");
            value = addwhere(value,roomid);
        }
        if (username != null){
            where = addwhere(where,"UserName");
            value = addwhere(value,username);
        }
        if (username != null){
            where = addwhere(where,"createAt");
            value = addwhere(value,createAt + "");
        }
        List<String[]> ro = select(null,new String[]{"MESSAGE"},where,value);
        Message[] rooms = new Message[ro.size()];
        for (int i = 0;i < ro.size();i++){
            rooms[i] = new Message();
            rooms[i].setUserName(ro.get(i)[0]);
            rooms[i].setRoomId(ro.get(i)[1]);
            rooms[i].setMessageContent(ro.get(i)[2]);
            try {
                rooms[i].setCreateAt(format.parse(ro.get(i)[3]));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            rooms[i].setUpdatedAt(ro.get(i)[4]);
        }
        return rooms;
    }
    public Message[] selectMessage(String username,String roomid,String createAt){
        String[] where = null;
        String[] value = null;
        if (roomid != null){
            where = addwhere(where,"RoomId");
            value = addwhere(value,roomid);
        }
        if (username != null){
            where = addwhere(where,"UserName");
            value = addwhere(value,username);
        }
        if (username != null){
            where = addwhere(where,"createAt");
            value = addwhere(value,createAt);
        }
        List<String[]> ro = select(null,new String[]{"MESSAGE"},where,value);
        Message[] rooms = new Message[ro.size()];
        for (int i = 0;i < ro.size();i++){
            rooms[i] = new Message();
            rooms[i].setUserName(ro.get(i)[0]);
            rooms[i].setRoomId(ro.get(i)[1]);
            rooms[i].setMessageContent(ro.get(i)[2]);
            try {
                rooms[i].setCreateAt(format.parse(ro.get(i)[3]));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            rooms[i].setUpdatedAt(ro.get(i)[4]);
        }
        return rooms;
    }

    public ArrayList<String[]> select(String[] selectlist, String[] formlist, String[] wherelist, String[] wherelistvalue,String orderby){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selectQuery="SELECT * ";
        for (int i = 0;i < formlist.length;i++){
            if (i == 0){
                selectQuery += "FROM " + formlist[i];
            }else {
                selectQuery += "," + formlist[i];
            }
        }
        if (wherelist != null) {
            for (int i = 0; i < wherelist.length; i++) {
                if (i == 0) {
                    selectQuery += " WHERE " + wherelist[i] + "=?";
                } else {
                    selectQuery += "," + wherelist[i] + "=?";
                }
            }
        }

        if (orderby != null){
            selectQuery += " ORDER BY " + orderby;
        }

        ArrayList<String[]> ResultSet=new ArrayList<String[]>();
        Cursor cursor=db.rawQuery(selectQuery,wherelistvalue);

        if(cursor.moveToFirst()){
            do{
                String[] message=new String[selectlist.length];
                for (int i = 0;i < selectlist.length;i++){
                    message[i] = cursor.getString(cursor.getColumnIndex(selectlist[i]));
                }
                ResultSet.add(message);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return ResultSet;
    }

    private String[] addwhere(String[] where,String value){
        String[] wh = new String[0];
        if (where == null){
            wh = new String[1];
        }else{
            wh = new String[where.length];
        }

        for (int i = 0;i < wh.length;i++){
            if (i == (wh.length - 1)){
                wh[i] = value;
                break;
            }
            wh[i] = where[i];
        }

        return wh;
    }
}
