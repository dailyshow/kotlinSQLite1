package com.cis.kotlinsqlite1.Fragment

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.cis.kotlinsqlite1.MainActivity
import com.cis.kotlinsqlite1.R

class DialogInsertFragment : DialogFragment() {
    var str: EditText? = null
    var intNum: EditText? = null
    var floatNum: EditText? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val view = requireActivity().layoutInflater.inflate(R.layout.dialog_insert, null)

        str = view.findViewById<EditText>(R.id.inputTextEt)
        intNum = view.findViewById<EditText>(R.id.inputIntEt)
        floatNum = view.findViewById<EditText>(R.id.inputFloatEt)

        val listener = Listener()

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(view)
        builder.setTitle("내용 입력")
        builder.setMessage("내용 입력하기")
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
                    val str2 = str?.text.toString()
                    val intNum2 = intNum?.text.toString().toInt()
                    val floatNum2 = floatNum?.text.toString().toFloat()

                    val mainActivity = activity as MainActivity
                    mainActivity.insertData(requireActivity(), str2, intNum2, floatNum2)

                    Toast.makeText(requireActivity(), "저장 완료", Toast.LENGTH_SHORT).show()

                    mainActivity.selectData(requireActivity())
                }
            }
        }
    }
}