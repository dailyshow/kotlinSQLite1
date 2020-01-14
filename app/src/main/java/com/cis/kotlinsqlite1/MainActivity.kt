package com.cis.kotlinsqlite1

import android.content.Context
import android.database.Cursor
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        insertBtn.setOnClickListener { view ->
            var helper = DBHelper(this)
            var db = helper.writableDatabase

            var sql = "insert into TestTable (textData, intData, floatData, dateData) values (?, ?, ?, ?)"

            var sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            var date = sdf.format(Date())

            var arg1 = arrayOf("문자열1", "100", "11.11", date)
            var arg2 = arrayOf("문자열2", "200", "22.22", date)

            db.execSQL(sql, arg1)
            db.execSQL(sql, arg2)

            db.close()

            tv.text = "저장 완료"
        }

        selectBtn.setOnClickListener { view ->
            selectData(this)
        }

        updateBtn.setOnClickListener { view ->
            var helper = DBHelper(this)
            var db = helper.writableDatabase

            var sql = "update TestTable set textData=? where idx=?"
            var args = arrayOf("문자열3", "1")

            db.execSQL(sql, args)

            db.close()

            tv.text = "수정 완료"
        }

        deleteBtn.setOnClickListener { view ->
//            var helper = DBHelper(this)
//            var db = helper.writableDatabase
//
//            var sql = "delete from TestTable where idx=?"
//            var args = arrayOf("1")
//
//            db.execSQL(sql, args)
//
//            db.close()
//
//            tv.text = "삭제 완료"

            var dialog = DialogFragment()
            dialog.isCancelable = false
            dialog.show(supportFragmentManager, "tag")
        }
    }

    fun selectData(context: Context) {
        var helper = DBHelper(context)
        var db = helper.writableDatabase

        var sql = "select * from TestTable"

        var cursor: Cursor = db.rawQuery(sql, null)

        tv.text = ""

        while (cursor.moveToNext()) {
            val idxPos = cursor.getColumnIndex("idx")
            val textDataPos = cursor.getColumnIndex("textData")
            val intDataPos = cursor.getColumnIndex("intData")
            val floatDataPos = cursor.getColumnIndex("floatData")
            val dateDataPos = cursor.getColumnIndex("dateData")

            val idx = cursor.getInt(idxPos)
            val textData = cursor.getString(textDataPos)
            val intData = cursor.getInt(intDataPos)
            val floatData = cursor.getDouble(floatDataPos)
            val dateData = cursor.getString(dateDataPos)

            /*
            이렇게 커서 열로 뽑아내도 되긴 한다.
            var idx = cursor.getString(0)
            var textData = cursor.getString(1)
            var intData = cursor.getInt(2)
            var floatData = cursor.getFloat(3)
            var dateData = cursor.getString(4)
            */

            tv.append("idx : ${idx}\n")
            tv.append("textData : ${textData}\n")
            tv.append("intData : ${intData}\n")
            tv.append("floatData : ${floatData}\n")
            tv.append("dateData : ${dateData}\n\n")
        }

        db.close()
    }
}
