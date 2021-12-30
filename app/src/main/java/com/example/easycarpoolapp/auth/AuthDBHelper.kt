package com.example.easycarpoolapp.auth

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AuthDBHelper(context : Context) : SQLiteOpenHelper(context, "EasyCarPoolAppDB", null, 1){
    override fun onCreate(p0: SQLiteDatabase?) {}
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}
}
