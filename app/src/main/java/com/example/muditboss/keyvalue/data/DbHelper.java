package com.example.muditboss.keyvalue.data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

/**
 *
 */
public class DbHelper extends SQLiteOpenHelper{


    public final static String DATABASE_NAME = "keyValue.db";
    public final static int  DATABASE_VERSION = 1;

    public DbHelper (Context context){

        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        final String CREATE_TABLE = "CREATE TABLE"+ DbContract.KeyValue.TABLE_NAME + "("
                + DbContract.KeyValue.COLUMN_KEY + "TEXT PRIMARY KEY," +
                DbContract.KeyValue.COLUMN_VALUE + "TEXT" + ")";

        SQLiteStatement stmt = db.compileStatement(CREATE_TABLE);

        try{
            stmt.execute();
        }
        catch (SQLException Sqlexception){
            Log.d("SQL_QUERY","Something wrong in query", Sqlexception);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sb, int oldVersion, int newVersion){

    }

}
