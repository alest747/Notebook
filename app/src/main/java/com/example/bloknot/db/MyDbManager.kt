package com.example.bloknot.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class MyDbManager(val context: Context) {

    private val myDbHelper = MyDbHelper(context)  //меременная с классом MyDbHelper в которую передали контекст в качетсве аргумента
    var db:SQLiteDatabase? = null  // переменная базы изначально равная null
    private val myDbClass = MyDbNameClass()  //переменная с классой с логикой бд

    fun openDb(){  //функция открытия бд
        db = myDbHelper.writableDatabase
    }

    fun insertToDb(title:String, content:String, uri:String){  //функция добавления данных в базу данных
        val values = ContentValues().apply {
            put(myDbClass.COLUMN_NAME_TITLE, title)  //  ложим наши данные в бд
            put(myDbClass.COLUMN_NAME_CONTENT, content)
            put(myDbClass.COLUMN_NAME_IMAGE_URI, uri)
        }
        db?.insert(myDbClass.TABLE_NAME,null, values)  //добавление данных в бд
    }

    fun removeItemFromDb(id:String){  //функция удаления данных из базы данных
        val selection = BaseColumns._ID + "=$id"  //переменная  равная колонам нашей бд по id
        db?.delete(myDbClass.TABLE_NAME,selection, null)  //удаление данных из бд id
    }

    @SuppressLint("Range")
    fun readDbData():ArrayList<ListItem>{  //чтение данных из бд
        val dataList = ArrayList<ListItem>()
        val cursor = db?.query(myDbClass.TABLE_NAME, null, null, null, null, null, null)

        with(cursor){
            while(this?.moveToNext()!!){
                val dataTitle = cursor?.getString(cursor.getColumnIndex(myDbClass.COLUMN_NAME_TITLE)).toString()  //переменные с базы данных
                val dataContent = cursor?.getString(cursor.getColumnIndex(myDbClass.COLUMN_NAME_CONTENT)).toString()
                val dataUri = cursor?.getString(cursor.getColumnIndex(myDbClass.COLUMN_NAME_IMAGE_URI)).toString()
                val dataId = cursor?.getInt(cursor.getColumnIndex(BaseColumns._ID)).toString().toInt()  //переменная для id
                val item = ListItem()
                item.title = dataTitle  //присваемаем переменным класса значения с базы
                item.desc = dataContent
                item.uri = dataUri
                item.id = dataId
                //val dataText2 = cursor?.getString(cursor.getColumnIndex(myDbClass.COLUMN_NAME_CONTENT)).toString()
                dataList.add(item)  //передаем класс с измененными данными
            }
        }
        cursor?.close()  //закрываем курсор
        return dataList  //возвращаем дата лист
    }

    fun closeDb(){
        myDbHelper.close()  //закрываем бд
    }
}