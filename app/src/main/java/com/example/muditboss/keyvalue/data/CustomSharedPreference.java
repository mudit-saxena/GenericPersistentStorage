package com.example.muditboss.keyvalue.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
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


    public static final String TAG_SUCCESS ="SUCCESS";
    public static final String TAG_EMPTY_CURSOR="EMPTY CURSOR";

    // Specificies the column index for "Value" field in table
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

    private String getReadQuery(String key,String dataType){

       String SQL_READ = "SELECT * FROM"+" "+DbContract.KeyValue.TABLE_NAME
                +" "+"WHERE key="+"'"+key+"'"
                + " AND "
                + DbContract.KeyValue.COLUMN_TYPE +"=" +"'"+dataType+"'" ;

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


    // getInt():- Return the int value stored
    // Return's -1 if the value doesn't exist

      public int getInt(String key){

            r.lock();
              try{

                  SQLiteDatabase db = new DbHelper(mContext).getReadableDatabase();

                    String query = getReadQuery(key,INT_TYPE);

                  Cursor c = db.rawQuery(query,null);

                  String ans="";

                  if(c!=null&c.moveToFirst()) {
                      ans = c.getString(COLUMN_INDEX_VALUE);
                      return Integer.parseInt(ans);
                  }
                  else{
                      Log.d(TAG_EMPTY_CURSOR,"Cursr is null :- getInt()");
                  }

                  db.close();
              }
              catch (Exception e){
                  Log.d("GET_INT","ERROR GENERATED FROM getInt()",e);
              }
        finally {
                  r.unlock();
              }

        return -1;
    }


    /*
    putInt used to insert the value associated with key
    if it doesn't exist else it updates the existing value
    */
    public void putInt(String key , int intVal){

        String value = new Integer(intVal).toString();
        w.lock();

        try {

            SQLiteDatabase db = new DbHelper(mContext).getWritableDatabase();

            // Update Transaction starts from here
            db.beginTransaction();

            String query = getReadQuery(key,INT_TYPE);
            Cursor c = db.rawQuery(query,null);

            if(c.getCount()!=0&&c.moveToFirst()){

                ContentValues values = new ContentValues();

                values.put(DbContract.KeyValue.COLUMN_VALUE,value);

                c.close();
                db.update(DbContract.KeyValue.TABLE_NAME,values,"key = "+ "'"+ key +"'",null);
            }else{

                String insertQuery = getInsertQuery(key,value,INT_TYPE);

                SQLiteStatement stmtInsert = db.compileStatement(insertQuery);

                long id = stmtInsert.executeInsert();

                if(id!=-1)
                    Log.d(TAG_SUCCESS,"Insert is successfull:putInt()");
                else
                    Log.d("FAILURE","Insert failed : putInt()");
            }

            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        }
        finally {
            w.unlock();
        }

    }


    // getLong():- Return the long value stored
    // Return's -1L if the value doesn't exist
    public long getLong(String key){
        r.lock();
        try{

            SQLiteDatabase db = new DbHelper(mContext).getReadableDatabase();

            String query = getReadQuery(key,LONG_TYPE);

            Cursor c = db.rawQuery(query,null);

            String ans="";

            if(c!=null&c.moveToFirst()) {
             ans = c.getString(COLUMN_INDEX_VALUE);
                c.close();
                return Long.parseLong(ans);
            }else{
                Log.d(TAG_EMPTY_CURSOR,"Cursr is null :- getLong()");
            }

            db.close();
        }
        catch (Exception e){
            Log.d("GET_STRING","ERROR GENERATED FROM getLong()",e);
        }
        finally {
            r.unlock();
        }

        return -1L;
    }


    /*
    putLong used to insert the value associated with key
    if it doesn't exist else it updates the existing value
    */
    public void putLong(String key,Long longVal){

        String value = Long.toString(longVal);
        w.lock();

        try {

            SQLiteDatabase db = new DbHelper(mContext).getWritableDatabase();

            // Update Transaction starts from here
            db.beginTransaction();

            String query = getReadQuery(key,LONG_TYPE);
            Cursor c = db.rawQuery(query,null);

            if(c.getCount()!=0&&c.moveToFirst()){

                ContentValues values = new ContentValues();

                values.put(DbContract.KeyValue.COLUMN_VALUE,value);

                 c.close();
                db.update(DbContract.KeyValue.TABLE_NAME,values,"key = "+ "'"+ key +"'",null);
            }else{

                String insertQuery = getInsertQuery(key,value,LONG_TYPE);

                SQLiteStatement stmtInsert = db.compileStatement(insertQuery);

                long id = stmtInsert.executeInsert();

                if(id!=-1)
                    Log.d(TAG_SUCCESS,"Insert is successfull:putLong()");
                else
                    Log.d("FAILURE","Insert failed : putLong()");
            }

            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();

        }
        finally {
            w.unlock();
        }
    }

    // getFloat():- Return the long value stored
    // Return's 0.0f if the value doesn't exist

    public Float getFloat(String key){

        r.lock();

        try{

            SQLiteDatabase db = new DbHelper(mContext).getReadableDatabase();

            String query = getReadQuery(key,FLOAT_TYPE);

            Cursor c = db.rawQuery(query,null);

            String ans ="";

            if(c.getCount()!=0&c.moveToFirst()) {

                ans = c.getString(COLUMN_INDEX_VALUE);
                c.close();
                db.close();
                return Float.parseFloat(ans);
            }else{
                Log.d(TAG_EMPTY_CURSOR,"Cursor is null :- getFloat()");
            }

        }
        catch (SQLException e){
            Log.d("GET_STRING","ERROR GENERATED FROM getString()",e);
        }
        finally {

            r.unlock();
        }

        return 0.0f;
    }


    /*
    putFloat used to insert the value associated with key
    if it doesn't exist else it updates the existing value
    */

    public void putFloat(String key, Float floatVal){
        String value = Float.toString(floatVal);
        w.lock();

        try {

            SQLiteDatabase db = new DbHelper(mContext).getWritableDatabase();

            // Update Transaction starts from here
            db.beginTransaction();

            String query = getReadQuery(key,FLOAT_TYPE);

            Cursor c = db.rawQuery(query,null);

            if(c.getCount()!=0&&c.moveToFirst()){

                ContentValues values = new ContentValues();

                values.put(DbContract.KeyValue.COLUMN_VALUE,value);

                c.close();
                db.update(DbContract.KeyValue.TABLE_NAME,values,"key = "+ "'"+ key +"'",null);
            }else{

                String insertQuery = getInsertQuery(key,value,FLOAT_TYPE);

                SQLiteStatement stmtInsert = db.compileStatement(insertQuery);

                long id = stmtInsert.executeInsert();

                if(id!=-1)
                    Log.d(TAG_SUCCESS,"Insert is successfull:putFloat()");
                else
                    Log.d("FAILURE","Insert failed : putFloat()");
            }

            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        }
        finally {
            w.unlock();
        }
    }


    /* getString used to retrieve the value associated with the key*/
    @Nullable
    public String getString(String key){


        r.lock();
         try{

             SQLiteDatabase db = new DbHelper(mContext).getReadableDatabase();

             String query = getReadQuery(key,STRING_TYPE);

             Cursor c = db.rawQuery(query,null);

             String ans;

                if(c!=null&c.moveToFirst()){
                    ans = c.getString(COLUMN_INDEX_VALUE);

                    db.close();

                    return ans;
                }else{
                    Log.d(TAG_EMPTY_CURSOR,"Cursor is null :- getString()");
                }

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

    public void putString(String key, String value){

        w.lock();

        try{
            SQLiteDatabase db = new DbHelper(mContext).getWritableDatabase();

            // Update Transaction starts from here
            db.beginTransaction();

            String query = getReadQuery(key,STRING_TYPE);
            Cursor c = db.rawQuery(query,null);

            if(c.getCount()!=0&&c.moveToFirst()){

                ContentValues values = new ContentValues();

                values.put(DbContract.KeyValue.COLUMN_VALUE,value);

                c.close();
                db.update(DbContract.KeyValue.TABLE_NAME,values,"key = "+ "'"+ key +"'",null);
              }else{

                String insertQuery = getInsertQuery(key,value,STRING_TYPE);
                SQLiteStatement stmtInsert = db.compileStatement(insertQuery);

               long id = stmtInsert.executeInsert();

                if(id!=-1)
                    Log.d(TAG_SUCCESS,"Insert is successfull:putString()");
                else
                    Log.d("FAILURE","Insert failed : putString()");
            }

            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        }
        finally {
            w.unlock();
        }
    }
}
