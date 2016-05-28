package com.example.muditboss.keyvalue.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Key Value Persistent Storage Layer generic implementation
 * It supports basic data types int, float, long and String <br/>
 * Read/Write to Key Value pair requires shared/exclusive locks
 * Update of Values takes place through transaction to ensure consistency of db
 * Testing of all methods is done through classes under androidTest
 * CustomSharedPreferenceTest.java
 * DatabaseTest.java
 * */

public class CustomSharedPreference{

    Context mContext;

    // Tags for logs
    public static final String TAG_SUCCESS ="SUCCESS";
    public static final String TAG_EMPTY_CURSOR="EMPTY_CURSOR";

    // Specificies the column index for "value" field in table
    public static final int COLUMN_INDEX_VALUE= 1;

    //Specifying the data type
    public static final String STRING_TYPE = "string";
    public static final String INT_TYPE = "int";
    public static final String LONG_TYPE = "long";
    public static final String FLOAT_TYPE = "float";


    // ReadWrite Lock instance
    private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();


    private final Lock r = rwl.readLock();
    private final Lock w = rwl.writeLock();


    public CustomSharedPreference(Context mContext){
        this.mContext = mContext;
    }


    private String getReadQuery(String key){

       String SQL_READ = "SELECT * FROM"+" "+DbContract.KeyValue.TABLE_NAME
                +" "+"WHERE key=" + "'" +key+ "'" ;

        return SQL_READ;
    }


    private String getInsertQuery(String key, String value, String dataType){

        String SQL_INSERT = "INSERT INTO "+ DbContract.KeyValue.TABLE_NAME+
                " VALUES("
                + "'" + key +"'" +","
                +"'"+ value +"'" +","
                +"'"+dataType +"'"
                +")";

        return SQL_INSERT;

    }

    private String showType(Object o){

        String type="";
        String oType = o.getClass().getName();
        if(oType.equals("java.lang.Integer"))
         type = INT_TYPE;
        else if(oType.equals("java.lang.Float"))
          type = FLOAT_TYPE;
        else if(oType.equals("java.lang.Long"))
          type = LONG_TYPE;
        else
            type = STRING_TYPE;

        return type;
    }


    public <T> boolean put(String key,T val){
        boolean result = true;
        String value = String.valueOf(val);
        String dataType = showType(val);
        w.lock();

        try {

            SQLiteDatabase db = new DbHelper(mContext).getWritableDatabase();

            // Update Transaction starts from here
            db.beginTransaction();

            String query = getReadQuery(key);
            Cursor c = db.rawQuery(query,null);

            if(c.getCount()!=0&&c.moveToFirst()){

                ContentValues values = new ContentValues();

                values.put(DbContract.KeyValue.COLUMN_VALUE,value);

                c.close();
                db.update(DbContract.KeyValue.TABLE_NAME,values,"key = "+ "'"+ key +"'",null);
            }else{

                String insertQuery = getInsertQuery(key,value,dataType);

                SQLiteStatement stmtInsert = db.compileStatement(insertQuery);

                long id = stmtInsert.executeInsert();

                if(id!=-1)
                    Log.d(TAG_SUCCESS,"Insert is successfull:put()");
                else{
                    result = false;
                    Log.d("FAILURE","Insert failed : put()");
                }

            }

            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        }catch (SQLException e){
            result=false;
            Log.e("SQL_INSERT","SQLError"+e);
        }
        finally {
            w.unlock();
        }

        return result;
    }


    public <T> T get(String key) {
        r.lock();
        try{

            SQLiteDatabase db = new DbHelper(mContext).getReadableDatabase();

            String query = getReadQuery(key);

            Cursor c = db.rawQuery(query,null);

            String ans="";

            if(c!=null&c.moveToFirst()) {
                ans = c.getString(COLUMN_INDEX_VALUE);

                String type = c.getString(2);
                c.close();
                db.close();

                if(type.equals(INT_TYPE))
                    return (T) Integer.valueOf(ans);
                else if(type.equals(FLOAT_TYPE))
                    return (T) Float.valueOf(ans);
                else if(type.equals(LONG_TYPE))
                    return (T) Long.valueOf(ans);
                else
                    return (T) ans;
            }
            else{
                Log.d(TAG_EMPTY_CURSOR,"Cursr is null :- getInt()");
            }
            db.close();

        }
        catch (SQLException e){
            Log.d("SQL Query","ERROR GENERATED FROM getInt()",e);
        }
        finally {
            r.unlock();
        }

        return null;
    }

}
