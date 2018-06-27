package com.example.sinoyd.frameapplication.KotlinFrame.Code.Adatper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.sinoyd.frameapplication.KotlinFrame.Code.DataClass.SignInInfo
import com.example.sinoyd.frameapplication.R
import kotlinx.android.synthetic.main.item_sign_in_layout.view.*
import org.jetbrains.anko.backgroundDrawable
import org.jetbrains.anko.onClick


/**
 * 作者： scj
 * 创建时间： 2018/5/5
 * 版权： 江苏远大信息股份有限公司
 * 描述： com.example.sinoyd.frameapplication.KotlinFrame.Code.Adatper
 */

class SigninAdapter(var con:Context, var list:MutableList<SignInInfo>):BaseAdapter(){

    override fun getView(p0: Int, convertView: View?, p2: ViewGroup?): View {
        var view:View
        var holder:ViewHolder?
        if(convertView == null){
            view = LayoutInflater.from(con).inflate(R.layout.item_sign_in_layout,null)
            holder = ViewHolder(view)
            view.tag = holder
        }else{
            view = convertView
            holder = view.tag as ViewHolder
        }

        //部署数据
        holder.id.text = list[p0].id.toString()
        holder.pointName.text = list[p0].pointName.toString()
        holder.userName.text = list[p0].userName.toString()
        holder.signInTime.text = list[p0].signInTime.toString()
        holder.signOutTime.text = list[p0].signOutTime.toString()
        holder.signState.text = list[p0].signState.toString()
        holder.signState.backgroundDrawable = when(list[p0].signState){
            "已签到" ->con.resources.getDrawable(R.drawable.sign_in_state)
            "已签退" ->con.resources.getDrawable(R.drawable.sign_out_state)
            else -> con.resources.getDrawable(R.drawable.sign_in_default)
        }
        return view
    }

    override fun getItem(p0: Int): Any = list[p0]

    override fun getItemId(p0: Int): Long = 0L

    override fun getCount(): Int = list.size


    internal inner class ViewHolder(view:View){
        var id = TextView(con)
        var pointName = TextView(con)
        var userName = TextView(con)
        var signInTime = TextView(con)
        var signOutTime = TextView(con)
        var signState = TextView(con)

        init {
            id = view.tv_no
            pointName = view.tv_station_name
            userName = view.tv_sign_person
            signInTime = view.tv_starttime
            signOutTime = view.tv_endtime
            signState = view.tv_upload_state
        }
    }
}