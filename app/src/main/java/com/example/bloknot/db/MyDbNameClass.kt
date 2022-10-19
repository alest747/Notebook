package com.example.bloknot.db

import android.provider.BaseColumns

class MyDbNameClass:BaseColumns {
    val TABLE_NAME = "my_table"
    val COLUMN_NAME_TITLE = "title"
    val COLUMN_NAME_CONTENT = "content"
    val COLUMN_NAME_IMAGE_URI = "uri"

    val DATABASE_VERSION = 2
    val DATABASE_NAME = "MyDb.db"

    val CREAT_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY,$COLUMN_NAME_TITLE TEXT,$COLUMN_NAME_CONTENT TEXT,$COLUMN_NAME_IMAGE_URI TEXT)"

    val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"

}