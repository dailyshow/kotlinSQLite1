package com.cis.kotlinsqlite1

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.get
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.dialog.*

class DialogFragment : DialogFragment() {
    var listPos = 0
    var dialogTv : TextView? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = requireActivity().layoutInflater.inflate(R.layout.dialog, null)

        dialogTv = view.findViewById<TextView>(R.id.dialogTv)
        var lv = view.findViewById<ListView>(R.id.dialogLv)

        val dbHelper = DBHelper(requireActivity())
        val db = dbHelper.writableDatabase
        val sql = "select * from TestTable"

        val list = arrayListOf<Int>()

        var cursor = db.rawQuery(sql, null)
        while (cursor.moveToNext()) {
            val idxPos = cursor.getColumnIndex("idx")
            val idx = cursor.getInt(idxPos)
            list.add(idx)
        }
        Log.d("tag", "${list.size}")

        val adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_1, list)

        lv.adapter = adapter
        
        lv.setOnItemClickListener { parent, view, position, id ->
            dialogTv?.text = "${list[position]} 번"
            listPos = position
        }

        db.close()

        var listener = Listener()

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(view)
        builder.setTitle("삭제하기")
        builder.setMessage("삭제하실 번호를 선택해주세요.")
        builder.setNegativeButton("취소", listener)
        builder.setPositiveButton("확인", listener)

        val dialog = builder.create()

        return dialog
    }

    inner class Listener : DialogInterface.OnClickListener {
        override fun onClick(dialog: DialogInterface?, which: Int) {
            when (which) {
                DialogInterface.BUTTON_NEGATIVE -> {
                    dismiss()
                }
                DialogInterface.BUTTON_POSITIVE -> {
                    var pos = dialogTv?.text?.substring(0, 1)?:0

                    var dbHelper = DBHelper(requireContext())
                    var db = dbHelper.writableDatabase

                    var sql = "delete from TestTable where idx=?"
                    var args = arrayOf(pos)

                    Log.d("tag", "listPos : ${pos}")
                    Log.d("tag", "listPos : ${listPos}")

                    db.execSQL(sql, args)

                    db.close()

                    val reloadData = activity as MainActivity
                    reloadData.selectData(requireActivity())
                }
            }
        }
    }
}