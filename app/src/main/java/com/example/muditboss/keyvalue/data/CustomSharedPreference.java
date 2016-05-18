package com.example.muditboss.keyvalue.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 */
public class CustomSharedPreference {

    Context mContext;
    String SQL_READ;

    public static final int COLUMN_INDEX_VALUE= 1;
    public static String Key="";

    private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    private final Lock r = rwl.readLock();
    private final Lock w = rwl.writeLock();


    CustomSharedPreference(Context mContext){
        this.mContext = mContext;

    }

    public void setKey(String Key){
        this.Key = Key;
    }


    /* getString used to retrieve the value associated with the key*/
    @Nullable
    public String getString(String Key, String Value){

        setKey(Key);
        r.lock();
         try{

             SQLiteDatabase db = new DbHelper(mContext).getReadableDatabase();



             SQL_READ = "SELECT * FROM" + DbContract.KeyValue.TABLE_NAME + "WHERE key="+Key;

             Cursor c = db.rawQuery(SQL_READ,null);


             return c.getString(COLUMN_INDEX_VALUE);

         }
         catch (Exception e){
             Log.d("GET_STRING","ERROR GENERATED FROM getString()",e);
         }
         finally {
             r.unlock();
         }

        return null;
    }

    /*
    putString used to insert the value associated with key
    if it doesn't exist else it updates the existing value
    */

    public void putString(String Key, String Value){

        w.lock();

        try{
            SQLiteDatabase db = new DbHelper(mContext).getWritableDatabase();

            // Update Transaction starts from here
            db.beginTransaction();

            SQL_READ = "SELECT * FROM" + DbContract.KeyValue.TABLE_NAME + "WHERE key="+Key;

            Cursor c = db.rawQuery(SQL_READ,null);

            if(c!=null){

                String existingValue = c.getString(COLUMN_INDEX_VALUE);
                ContentValues values = new ContentValues();
                values.put(existingValue,Value);

                db.update(DbContract.KeyValue.TABLE_NAME,values,"key="+Key,null);
            }else{

                String SQL_INSERT = "INSERT INTO"+ DbContract.KeyValue.TABLE_NAME+ "VALUES(" +Key+","+Value+")";

                SQLiteStatement stmtInsert = db.compileStatement(SQL_INSERT);

               long id = stmtInsert.executeInsert();
            }

            db.setTransactionSuccessful();
            db.endTransaction();

        }
        finally {
            w.unlock();
        }
    }
}
