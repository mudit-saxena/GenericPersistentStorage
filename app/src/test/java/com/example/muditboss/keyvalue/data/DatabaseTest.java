package com.example.muditboss.keyvalue.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.test.mock.MockContext;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
/**
 *
 */
public class DatabaseTest extends AndroidTestCase{

  //Context mContext;
   Context context;
    void deleteTheDatabase() {
        context.deleteDatabase(DbHelper.DATABASE_NAME);
    }

    @Before
    public void setUp() throws Exception {

        context = new MockContext();
        setContext(context);
        deleteTheDatabase();
    }

    @Test
    public void testOnCreate() throws Throwable {
        final HashSet<String> tableNameHashSet = new HashSet<String>();
       tableNameHashSet.add(DbContract.KeyValue.TABLE_NAME);


        context.deleteDatabase(DbHelper.DATABASE_NAME);
        DbHelper dbHelper= new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        assertEquals(true, db.isOpen());

        // have we created the table we want?
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        assertTrue("Error: This means that the database has not been created correctly",
                c.moveToFirst());

        // verify that the tables have been created
        do {
            tableNameHashSet.remove(c.getString(0));
        } while( c.moveToNext() );

        // if this fails, it means that your database doesn't contain keyValue table
        assertTrue("Error: Your database was created without table",
                tableNameHashSet.isEmpty());
    }


}