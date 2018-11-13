package com.example.aria.notificationa.contract

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.example.aria.notificationa.contract.TodoContract.*

class TodoDBHelper(val DB_NAME: String = "todolist.db",var DB_VER: Int = 1,val context: Context):
        SQLiteOpenHelper(context,DB_NAME,null,DB_VER){

    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        val CREATE_TABLE: String = "CREATE TABLE " +
                TodoContractEntry.TABLE_NAME + " (" +
                BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TodoContractEntry.TODO_THING + " TEXT NOT NULL, " +
                TodoContractEntry.TODO_TIME + " TIMESTAMP NOT NULL, " +
                TodoContractEntry.TODO_ADD_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                "); "
        sqLiteDatabase.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase,i: Int,i1: Int){
        onCreate(sqLiteDatabase)
    }
}