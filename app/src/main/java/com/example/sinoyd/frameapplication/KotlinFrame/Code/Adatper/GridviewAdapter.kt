package com.example.sinoyd.frameapplication.KotlinFrame.Code.Adatper

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.*
import com.example.sinoyd.frameapplication.KotlinFrame.Code.db_Weekly_inspection.Check2clean
import com.example.sinoyd.frameapplication.R
import kotlinx.android.synthetic.main.gv_item_layout.view.*
import java.util.ArrayList
import java.util.HashMap

/**
 * 作者： scj
 * 创建时间： 2018/1/11
 * 版权：
 * 描述： com.sinoyd.Code.Adapter   单选 多选功能的适配器
 *
 * @param only true:单选   false：多选
 * @param datas 数据源
 */


class GridviewAdapter(var datas: List<Check2clean>, var context: Context) : BaseAdapter() {

    var newlist: List<Check2clean> = ArrayList()

    init {
        newlist = datas
    }


    override fun getView(p0: Int, convertView: View?, p2: ViewGroup?): View {
        var view: View
        var holder: ViewHolder?
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.gv_item_layout, null)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }
        //显示数据
        holder.tvname!!.text = datas[p0].monitorItemText
//        holder.st!!.tag = datas[p0].valuename
        when (datas[p0].monitorItemValue) {
            "1" -> {
                holder.st!!.isChecked = false
            }
            "0" -> {
                holder.st!!.isChecked = true
            }
        }

        //Switch单击事件
        holder.st!!.setOnClickListener {
            when (datas[p0].monitorItemValue) {
                "1" -> {
                    newlist[p0].monitorItemValue = "0"
                }
                "0" -> {
                    newlist[p0].monitorItemValue = "1"
                }
            }
        }

        return view
    }

    override fun getItem(p0: Int) = datas[p0]

    override fun getItemId(p0: Int) = 0L

    override fun getCount() = datas.size


    internal inner class ViewHolder(view: View) {
        var tvname: TextView? = null
        var st: Switch? = null

        init {
            tvname = view.name
            st = view.st
        }
    }
}



