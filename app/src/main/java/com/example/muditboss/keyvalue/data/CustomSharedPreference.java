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
 * Key Value Persistent Storage Layer
 * It supports basic data types int, float, long and String
 * Read/Write to Key Value pair requires shared/exclusive locks
 * Update of Values takes place through transaction to ensure consistency of db
 * getString and putString methods contains log statement for queue length of threads acquired or waiting for locks respectively
 */

/*Testing of all methods is done through MainActivity.java
* CustomSharedPreferenceTest.java
* DatabaseTest.java
* */

public class CustomSharedPreference {

    Context mContext;


    // Tags for logs
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

    /* getInt():- Return the int value stored
       return's -1 if the value doesn't exist
    */
      public int getInt(String key){

            r.lock();
              try{

                  SQLiteDatabase db = new DbHelper(mContext).getReadableDatabase();

                    String query = getReadQuery(key,INT_TYPE);

                  Cursor c = db.rawQuery(query,null);

                  String ans;

                  if(c!=null&c.moveToFirst()) {
                      ans = c.getString(COLUMN_INDEX_VALUE);
                      c.close();
                      db.close();
                      return Integer.parseInt(ans);
                  }
                  else{
                      Log.d(TAG_EMPTY_CURSOR,"Cursr is null :- getInt()");
                  }
                  db.close();

              }
              catch (SQLException e){
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
    return true if value successfully inserted/updated
     false otherwise
    */
    public boolean putInt(String key , int intVal){

        boolean result = true;

        String value = Integer.toString(intVal);
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
                 else{
                    result = false;
                    Log.d("FAILURE","Insert failed : putInt()");
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


    /* getLong():- Return the long value stored
       return's -1L if the value doesn't exist
    */
    public long getLong(String key){
        r.lock();
        try{

            SQLiteDatabase db = new DbHelper(mContext).getReadableDatabase();

            String query = getReadQuery(key,LONG_TYPE);

            Cursor c = db.rawQuery(query,null);

            String ans;

            if(c!=null&c.moveToFirst()) {
             ans = c.getString(COLUMN_INDEX_VALUE);
                c.close();
                db.close();
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
    @return true if successful false otherwise
    */
    public boolean putLong(String key,Long longVal){

        boolean result= true;

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
                else{
                    result = false;
                    Log.d("FAILURE","Insert failed : putLong()");
                }

            }

            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();

        }catch (SQLException e){
            result = false;
            Log.e("SQL_INSERT","SQLError"+e);
        }
        finally {
            w.unlock();
        }

        return result;
    }

    /* getFloat():- Return the long value stored
     * return's 0.0f if the value doesn't exist
    */
    public Float getFloat(String key){

        r.lock();

        try{

            SQLiteDatabase db = new DbHelper(mContext).getReadableDatabase();

            String query = getReadQuery(key,FLOAT_TYPE);

            Cursor c = db.rawQuery(query,null);

            String ans;

            if(c.getCount()!=0&c.moveToFirst()) {

                ans = c.getString(COLUMN_INDEX_VALUE);
                c.close();
                db.close();
                return Float.parseFloat(ans);
            }else{
                Log.d(TAG_EMPTY_CURSOR,"Cursor is null :- getFloat()");
            }
            db.close();
        }
        catch (SQLException e){
            Log.d("GET_STRING","ERROR GENERATED FROM getString()",e);
        }
        finally {

            r.unlock();
        }

        return 0.0f;
    }


    /* putFloat used to insert the value associated with key
     * if it doesn't exist else it updates the existing value
     * @return true if successful false otherwise
    */

    public boolean putFloat(String key, Float floatVal){

        boolean result = true;

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
                else{
                    result = false;
                    Log.d("FAILURE","Insert failed : putFloat()");
                }

            }


            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();

        }catch (SQLException e){
            result =false;
           Log.e("SQL_INSERT","SQLError"+e);
        }
        finally {
            w.unlock();
        }

        return result;
    }


    /* getString used to retrieve the value associated with the key
    * @return value else null
    */
    @Nullable
    public String getString(String key){

        // Check for number of read locks held simultaneously
        Log.d("Waiting on string read","Read"+rwl.getReadLockCount());

        r.lock();
         try{

             SQLiteDatabase db = new DbHelper(mContext).getReadableDatabase();

             String query = getReadQuery(key,STRING_TYPE);

             Cursor c = db.rawQuery(query,null);

             String ans;

                if(c!=null&c.moveToFirst()){
                    ans = c.getString(COLUMN_INDEX_VALUE);

                    c.close();
                    db.close();

                    return ans;
                }else{
                    Log.d(TAG_EMPTY_CURSOR,"Cursor is null :- getString()");
                }
             db.close();
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
    @return true if successful false otherwise
    */

    public boolean putString(String key, String value){


        boolean result = true;
        //Check for no. of threads waiting in Queue to acquire write lock
        Log.d("Thread Write","Queue Length"+rwl.getQueueLength());

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
                else{
                    result = false;
                    Log.d("FAILURE","Insert failed : putString()");
                }

            }

            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        }catch (SQLException e){
            result = false;
            Log.e("SQL_INSERT","SQLError"+e);
        }
        finally {
            w.unlock();
        }

        return result;
    }
}
