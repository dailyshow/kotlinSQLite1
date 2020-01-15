package com.cis.kotlinsqlite1

import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import com.cis.kotlinsqlite1.Fragment.DialogDeleteFragment
import com.cis.kotlinsqlite1.Fragment.DialogInsertFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        insertBtn.setOnClickListener { view ->
            val dialog = DialogInsertFragment()
            dialog.isCancelable = false
            dialog.show(supportFragmentManager, "insert")
        }

        selectBtn.setOnClickListener { view ->
            selectData(this)
        }

        deleteBtn.setOnClickListener { view ->
            val dialog = DialogDeleteFragment()
            dialog.isCancelable = false
            dialog.show(supportFragmentManager, "delete")
        }
    }

    fun insertData(context: Context, str: String, num: Int, flotNum: Float) {
        val helper = DBHelper(context)
        val db = helper.writableDatabase

        val sql = "insert into TestTable (textData, intData, floatData, dateData) values (?, ?, ?, ?)"

        val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:dd", Locale.getDefault())
        val date = sdf.format(Date())

        val arg1 = arrayOf(str, num, flotNum, date)

        db.execSQL(sql, arg1)
        db.close()

    }

    fun updateData(context: Context, textData: String, intData: String, floatData: String, idx: String) {
        val helper = DBHelper(context)
        val db = helper.writableDatabase

        val sql = "update TestTable set textData=?, intData=?, floatData=? where idx=?"
        val args = arrayOf(textData, intData, floatData, idx)

        db.execSQL(sql, args)
        db.close()

        Toast.makeText(this, "수정 완료", Toast.LENGTH_SHORT).show()

    }

    fun selectData(context: Context) {
        val helper = DBHelper(context)
        val db = helper.writableDatabase

        val sql = "select * from TestTable"

        val cursor: Cursor = db.rawQuery(sql, null)

//        tv.text = ""
        val recyclerView = mainRv
        val list = arrayListOf<Items>()

        while (cursor.moveToNext()) {
            val idxPos = cursor.getColumnIndex("idx")
            val textDataPos = cursor.getColumnIndex("textData")
            val intDataPos = cursor.getColumnIndex("intData")
            val floatDataPos = cursor.getColumnIndex("floatData")
            val dateDataPos = cursor.getColumnIndex("dateData")

            val idx = cursor.getInt(idxPos)
            val textData = cursor.getString(textDataPos)
            val intData = cursor.getInt(intDataPos)
            val floatData = cursor.getDouble(floatDataPos).toFloat()
            val dateData = cursor.getString(dateDataPos)

            /*
            이렇게 커서 열로 뽑아내도 되긴 한다.
            var idx = cursor.getString(0)
            var textData = cursor.getString(1)
            var intData = cursor.getInt(2)
            var floatData = cursor.getFloat(3)
            var dateData = cursor.getString(4)
            */

//            tv.append("idx : ${idx}\n")
//            tv.append("textData : ${textData}\n")
//            tv.append("intData : ${intData}\n")
//            tv.append("floatData : ${floatData}\n")
//            tv.append("dateData : ${dateData}\n\n")

            list.add(Items(idx, textData, intData, floatData, dateData))
        }

        val adapter = RecyclerAdapter(this, list)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        db.close()

        Toast.makeText(context, "값 불러오기 실행됨", Toast.LENGTH_SHORT).show()
    }
}
