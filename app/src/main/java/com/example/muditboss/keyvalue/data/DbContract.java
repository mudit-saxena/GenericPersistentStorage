package com.example.muditboss.keyvalue.data;

import android.provider.BaseColumns;

/**
 * DbContract specifying the schema of DB
 */
public class DbContract {

    public static class KeyValue implements BaseColumns{

        //Table Name
        public static final String TABLE_NAME = "keyStore";

        //Column for specifying key
        public static final String COLUMN_KEY = "key";

        //Column for specifying value
        public static final String COLUMN_VALUE = "value";

        //Column for specifying data type
        public static final String COLUMN_TYPE = "type";

    }
}
