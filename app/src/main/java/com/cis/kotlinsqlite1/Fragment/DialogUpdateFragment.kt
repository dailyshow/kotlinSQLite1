package com.cis.kotlinsqlite1.Fragment

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.cis.kotlinsqlite1.MainActivity
import com.cis.kotlinsqlite1.R

class DialogUpdateFragment(val idx: Int, val textData: String, val intData: Int, val floatData: Float, val dateData: String) : DialogFragment() {
    var idxTv: TextView? = null
    var textEt: EditText? = null
    var intEt: EditText? = null
    var floatEt: EditText? = null
    var dateTv: TextView? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val view = requireActivity().layoutInflater.inflate(R.layout.dialog_update, null)

        idxTv = view.findViewById<TextView>(R.id.updateIdxTv)
        textEt = view.findViewById<EditText>(R.id.updateTextEt)
        intEt = view.findViewById<EditText>(R.id.updateIntEt)
        floatEt = view.findViewById<EditText>(R.id.updateFloatEt)
        dateTv = view.findViewById<TextView>(R.id.updateDateTv)


        idxTv?.text = idx.toString()
        // edittext 에 settext 할 때 setText() 또는 Editable... 중 하나를 사용하면 되는듯 함
        textEt?.setText(textData)
        // text.text = Editable.Factory.getInstance().newEditable(textData)
        intEt?.setText(intData.toString())
        floatEt?.setText(floatData.toString())
        dateTv?.text = dateData

        val listener = Listener()

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(view)
        builder.setTitle("idx ${idxTv?.text} 번 업데이트")
        builder.setMessage("${textEt?.text} 내용 업데이트")
        builder.setNegativeButton("취소", listener)
        builder.setPositiveButton("확인", listener)

        val dialog = builder.create()

        return dialog
    }

    inner class Listener : DialogInterface.OnClickListener{
        override fun onClick(dialog: DialogInterface?, which: Int) {
            when (which) {
                DialogInterface.BUTTON_NEGATIVE -> {
                    dismiss()
                }
                DialogInterface.BUTTON_POSITIVE -> {
                    val mainActivity = activity as MainActivity
                    mainActivity.updateData(requireActivity(), textEt?.text.toString(), intEt?.text.toString(), floatEt?.text.toString(), idxTv?.text.toString())
                    mainActivity.selectData(requireActivity())
                }
            }
        }
    }
}