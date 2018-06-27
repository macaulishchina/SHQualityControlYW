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
import com.example.sinoyd.frameapplication.KotlinFrame.Code.db_Weekly_inspection.Instrument
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Dataclass.Keyvalue
import com.example.sinoyd.frameapplication.R
import kotlinx.android.synthetic.main.gv_item_ins_layout.view.*
import kotlinx.android.synthetic.main.gv_item_layout.view.*

/**
 * 作者： scj
 * 创建时间： 2018/1/11
 * 版权：
 * 描述： com.sinoyd.Code.Adapter   单选 多选功能的适配器
 *
 * @param only true:单选   false：多选
 * @param datas 数据源
 */


class GridviewAdapterInstrument(var datas: List<Instrument>, var context: Context) : BaseAdapter() {


    override fun getView(p0: Int, convertView: View?, p2: ViewGroup?): View {
        var view: View
        var holder: ViewHolder?
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.gv_item_ins_layout, null)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }
        //显示数据
        holder.tvname!!.text = "站点名称：${datas[p0].monitoringPointName}    原系统编号：${datas[p0].oldProductNumber}    新系统编号：${datas[p0].newProductNumber}    更换原因：${datas[p0].reason}"
        return view
    }

    override fun getItem(p0: Int) = datas[p0]

    override fun getItemId(p0: Int) = 0L

    override fun getCount() = datas.size


    internal inner class ViewHolder(view: View) {
        var tvname: TextView? = null

        init {
            tvname = view.tvtv
        }
    }
}



