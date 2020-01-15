package com.cis.kotlinsqlite1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.cis.kotlinsqlite1.Fragment.DialogUpdateFragment
import kotlinx.android.synthetic.main.view_layout.view.*


class RecyclerAdapter(private val activity: AppCompatActivity, private val items: ArrayList<Items>) : RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_layout, parent, false)

        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = items[position]
        val listener = View.OnClickListener {
            Toast.makeText(it.context, "${item.idx}선택됨 ", Toast.LENGTH_SHORT).show()

            val update = DialogUpdateFragment(
                item.idx,
                item.textData,
                item.intData,
                item.floatData,
                item.dateData
            )
            update.isCancelable = false
            update.show(activity.supportFragmentManager,"update")
        }
        holder.apply {
            bind(listener, item)
            itemView.tag = item
        }
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var v: View = view

        fun bind(listener: View.OnClickListener, item: Items) {
            v.rvIdxTv.text = "idx : ${item.idx.toString()}"
            v.rvTextTv.text = "text : ${item.textData}"
            v.rvIntTv.text = "intData : ${item.intData.toString()}"
            v.rvFloatTv.text = "floatData : ${item.floatData.toString()}"
            v.rvDateTv.text = "date : ${item.dateData}"
            v.setOnClickListener(listener)
        }
    }

}

