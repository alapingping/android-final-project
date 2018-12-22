package com.sports.yue.UI.UI.local_db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sports.yue.R;
import com.sports.yue.UI.UI.models.Community;
import com.sports.yue.UI.UI.models.Message;
import com.sports.yue.UI.UI.models.Room;
import com.sports.yue.UI.UI.models.User;

import java.util.ArrayList;

public class DbManager {

    private DBHelper dbHelper;
    private static DbManager Db_M = null;

    private DbManager(Context context){

        //在这里创建User表
        dbHelper=new DBHelper(context);
        SQLiteDatabase db1 = dbHelper.getWritableDatabase();
        String sql = "CREATE TABLE IF NOT EXISTS USER ("
                + "UserSex varchar, UserEmail varchar,UserPhone varchar,UserPass varchar,"
                + "UserName varchar PRIMARY KEY,UserInfo varchar,createdAt datetime,updatedAt datetime)";
        db1.execSQL(sql);
        db1.close();

        //这里创建Room表
        sql = "CREATE TABLE IF NOT EXISTS ROOM ("
                + "ActivityTime datetime, ActivityPosition varchar,RoomMaxPeople number,RoomType varchar,"
                + "RoomDescription varchar,RoomCreator varchar,RoomName varchar,RoomId varchar PRIMARY KEY,"
                + "createdAt datetime,updateAt datetime)";
        SQLiteDatabase db2 = dbHelper.getWritableDatabase();
        db2.execSQL(sql);
        db2.close();

        //这里创建Message表
        sql = "CREATE TABLE IF NOT EXISTS MESSAGE ("
                + "UserName varchar, RoomId varchar,MessageContent varchar,"
                + "createdAt datetime,updatedAt datetime, PRIMARY KEY(UserName,RoomId,createdAt))";
        SQLiteDatabase db3 = dbHelper.getWritableDatabase();
        db3.execSQL(sql);
        db3.close();

        //这里创建Community表
        sql = "CREATE TABLE IF NOT EXISTS COMMUNITY ("
                + "UserName varchar, Likes number,Video varchar,RoomId varcahr ,"
                + "Email varchar,createdAT datetime,updatedAt datetime, PRIMARY KEY (UserName,RoomId,createdAT))";
        SQLiteDatabase db4 = dbHelper.getWritableDatabase();
        db4.execSQL(sql);
        db4.close();
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
                        "RoomCreator","RoomName","RoomId","createdAt","updateAt"},
                new String[]{((Room)o).getActivityTime() + "",((Room)o).getActivityPosition()+"",((Room)o).getRoomMaxPeople()+ "", ((Room)o).getRoomType(),
                        ((Room)o).getRoomDescription(),((Room)o).getRoomCreator(),((Room)o).getRoomName()+"",((Room)o).getRoomId(),
                        ((Room)o).getCreatedAt(),((Room)o).getUpdatedAt()});
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
     * @param selectfield this can help you find the data you want to updata
     * @param selectfieldvalue this can help you find the data you want to updata
     */
    public void update(String tablename,String[] field,String[] fieldvalue,String[] selectfield,String[] selectfieldvalue){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String whereClause = "";
        for (int i = 0;i < selectfield.length;i++){
            if (i == selectfield.length - 1){
                whereClause += selectfield[i] + "=?";
                break;
            }
            whereClause += selectfield[i] + "=?,";
        }
        ContentValues values=new ContentValues();
        for (int i = 0;i < field.length;i++){
            values.put(field[i],fieldvalue[i]);
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
                        "RoomCreator","RoomName","RoomId","createdAt","updateAt"},
                new String[]{((Room)o).getActivityTime() + "",((Room)o).getActivityPosition()+"",((Room)o).getRoomMaxPeople()+ "", ((Room)o).getRoomType(),
                        ((Room)o).getRoomDescription(),((Room)o).getRoomCreator(),((Room)o).getRoomName()+"",((Room)o).getRoomId(),
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
}
