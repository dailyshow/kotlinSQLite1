package com.cis.kotlinsqlite1

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

// 생성할 때는 버전을 1을 넣었고, db 구조를 변경하기 위해서 버전을 2, 3, 4 등으로 증가시켜준다.
// 버전이 변경되면 onUpgrade 가 호출된다.
class DBHelper(context: Context): SQLiteOpenHelper(context, "Test.db", null, 2) {

    override fun onCreate(db: SQLiteDatabase?) {
        Log.d("msg", "onCreate")

        val sql = "create table TestTable (" +
                "idx integer primary key autoincrement," +
                "textData text not null," +
                "intData integer not null," +
                "floatData real not null," +
                "dateData date not null" +
                ")"
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Log.d("msg", "oldVersion : ${oldVersion}, newVersion : ${newVersion}")
    }
}