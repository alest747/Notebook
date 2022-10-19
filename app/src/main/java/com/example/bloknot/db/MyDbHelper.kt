package com.example.bloknot.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

val myDbName = MyDbNameClass()

class MyDbHelper(context: Context):SQLiteOpenHelper(context, myDbName.DATABASE_NAME, null, myDbName.DATABASE_VERSION) {


    override fun onCreate(db: SQLiteDatabase?) {

        db?.execSQL(myDbName.CREAT_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db?.execSQL(myDbName.SQL_DELETE_TABLE)
        onCreate(db)
    }
}