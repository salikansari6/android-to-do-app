package com.salik.todoapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObservable;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.salik.todoapp.model.ToDo;
import com.salik.todoapp.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHandler extends SQLiteOpenHelper {


    private final Context context;

    public DataBaseHandler(@Nullable Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TO_DO_TABLE = "CREATE TABLE "+Constants.TABLE_NAME + "("+
                Constants.KEY_ID+ " INTEGER PRIMARY KEY,"+
                Constants.KEY_TITLE+" TEXT,"+
                Constants.KEY_COMPLETED+" INTEGER DEFAULT 0)";
        db.execSQL(CREATE_TO_DO_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.TABLE_NAME);
        onCreate(db);
    }

    public void addToDo(ToDo todo){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.KEY_TITLE,todo.getTitle());
        values.put(Constants.KEY_COMPLETED,false);

        db.insert(Constants.TABLE_NAME,null,values);
        Log.d("DB","added toDo " + todo.getTitle());
    }

    public ToDo getToDo(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Constants.TABLE_NAME,
                new String[]{Constants.KEY_ID,Constants.KEY_TITLE,Constants.KEY_COMPLETED},Constants.KEY_ID+"=?",
                new String[]{
                        String.valueOf(id)},null,null,null,null);
        if(cursor!=null)
            cursor.moveToFirst();

        ToDo toDo = new ToDo();
        if(cursor!=null){
            toDo.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
            toDo.setTitle(cursor.getString(cursor.getColumnIndex(Constants.KEY_TITLE)));
            toDo.setCompleted(cursor.getInt(cursor.getColumnIndex(Constants.KEY_COMPLETED)) == 1);

        }

        return toDo;
    }


    public List<ToDo> getAllToDos(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(Constants.TABLE_NAME,
                new String[]{Constants.KEY_ID,Constants.KEY_TITLE,Constants.KEY_COMPLETED},Constants.KEY_ID+"=?",
                null,null,null,null);
        List<ToDo> toDoList = new ArrayList<>();


        if(cursor.moveToFirst()){
            do{
                cursor.moveToNext();
                ToDo toDo = new ToDo();
                toDo.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
                toDo.setTitle(cursor.getString(cursor.getColumnIndex(Constants.KEY_TITLE)));
                toDo.setCompleted(cursor.getInt(cursor.getColumnIndex(Constants.KEY_COMPLETED)) == 1);
                toDoList.add(toDo);
            }
            while(cursor.moveToNext());
        }

        return toDoList;
    }

    public int updateToDo(ToDo toDo){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.KEY_TITLE,toDo.getTitle());
        values.put(Constants.KEY_COMPLETED,false);

        return db.update(Constants.TABLE_NAME,values,Constants.KEY_ID+"=?",new String[]{String.valueOf(toDo.getId())});
    }

    public void deleteToDo(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(Constants.TABLE_NAME,Constants.KEY_ID+"=?",new String[]{String.valueOf(id)});
    }

}
