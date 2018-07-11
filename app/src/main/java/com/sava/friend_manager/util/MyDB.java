package com.sava.friend_manager.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sava.friend_manager.model.Friend;
import java.util.ArrayList;

public class MyDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MR_SAVA";
    private static final String TABLE_NAME = "FRIEND";
    private static final String ID = "ID";
    private static final String AVATA = "AVATA";
    private static final String NAME = "NAME";
    private static final String BD = "BD";
    private static final String PHONE = "PHONE";
    private static final String EMAIL = "EMAIL";
    private static final String ADDRESS = "ADDRESS";
    private static final String TYPE = "TYPE";
    private Context mContext;
    public MyDB(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.mContext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableFriend = " CREATE TABLE " + TABLE_NAME + " ( "+
                ID + " integer primary key ,"+
                AVATA + " text, " +
                NAME + " text ," +
                BD + " text , "+
                PHONE + " text ," +
                EMAIL + " text , "+
                ADDRESS + " text , " +
                TYPE  + " integer )";
        db.execSQL(createTableFriend);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void addFriend(Friend friend){
        ContentValues values = new ContentValues();
        values.put(AVATA,friend.getAvata());
        values.put(NAME,friend.getName());
        values.put(BD,friend.getBrithDay());
        values.put(PHONE,friend.getPhone());
        values.put(EMAIL,friend.getEmail());
        values.put(ADDRESS,friend.getAddress());
        values.put(TYPE,friend.getType());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME,null,values);
        db.close();
    }
    public void editFriend(Friend friend){
        ContentValues values = new ContentValues();
        values.put(AVATA,friend.getAvata());
        values.put(NAME,friend.getName());
        values.put(BD,friend.getBrithDay());
        values.put(PHONE,friend.getPhone());
        values.put(EMAIL,friend.getEmail());
        values.put(ADDRESS,friend.getAddress());
        values.put(TYPE,friend.getType());
        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_NAME,values,ID +  " = ?",new String[]{String.valueOf(friend.getId())});
        db.close();
    }
    public void deleteFriend(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME,ID +" = ? ",new String[]{String.valueOf(id)});
        db.close();
    }
    public ArrayList<Friend> getAllFriendByType(int type){
        ArrayList<Friend> list = new ArrayList<>();
        String sql;
        if(type==1)
            sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + TYPE + " < 2 ";
        else
            sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + TYPE + " = " + type;
        Cursor cursor = getReadableDatabase().rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                Friend friend = new Friend(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getInt(7));
                list.add(friend);
            }while (cursor.moveToNext());
        }
        return list;
    }
    public ArrayList<Suggestion> getAllName(){
        ArrayList<Suggestion> list = new ArrayList<>();
        String sql = "SELECT  " + NAME +"," +ID + " FROM " + TABLE_NAME;
        Cursor cursor = getReadableDatabase().rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                list.add(new Suggestion(cursor.getString(0),cursor.getInt(1)));
            }while (cursor.moveToNext());
        }
        return list;
    }
    public Friend getFriendById(int id){
        Friend friend = null;
        String sql =   sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID + " = " + id;
        Cursor cursor = getReadableDatabase().rawQuery(sql,null);
        if(cursor.moveToFirst()){
                friend = new Friend(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getInt(7));
        }
        return  friend;
    }
}
