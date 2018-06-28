package com.example.sinoyd.frameapplication.KotlinFrame.Code.Adatper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.sinoyd.frameapplication.KotlinFrame.Code.UI.Task_management_Activity
import com.example.sinoyd.frameapplication.KotlinFrame.Code.db.FormTask
import com.example.sinoyd.frameapplication.R
import kotlinx.android.synthetic.main.item_task_manager_layout.view.*
import org.jetbrains.anko.backgroundDrawable
import org.jetbrains.anko.onClick

/**
 * 作者： scj
 * 创建时间： 2018/5/9
 * 版权： 江苏远大信息股份有限公司
 * 描述： com.example.sinoyd.frameapplication.KotlinFrame.Code.Adatper
 */

class TaskManagerAdapter(var con: Context, var list: MutableList<FormTask>) : BaseAdapter() {
    override fun getItem(p0: Int): Any = list[p0]

    override fun getItemId(p0: Int): Long = 0L

    override fun getCount(): Int = list.size

    override fun getView(p0: Int, convertView: View?, p2: ViewGroup?): View {
        var view: View
        var holder: ViewHolder?
        if (convertView == null) {
            view = LayoutInflater.from(con).inflate(R.layout.item_task_manager_layout, null)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }

        //部署数据
        holder.number.text = list[p0].id.toString() //任务编号
        holder.stationname.text = list[p0].pointName.toString()  //站点名称
        holder.taskname.text = list[p0].taskName.toString()  //任务名称

        holder.tasktype.text = list[p0].taskTypeName.toString()  //任务类型

        holder.planstarttime.text = list[p0].endDate.toString()  //任务要求完成时间
        holder.endtime.text = list[p0].endTime.toString()  //结束时间
        holder.taskstatus.text = list[p0].taskStatusName.toString() //任务状态

        //是否包含图片，显示不同
        if((con as Task_management_Activity).containPicture(list[p0].rowGuid)){
            holder.addpic.backgroundDrawable = con.resources.getDrawable(R.drawable.uploadyes_bt_bg_color)
            holder.addpic.text = "查看图片"
        }else{
            holder.addpic.backgroundDrawable = con.resources.getDrawable(R.drawable.btn_bg_gray)
            holder.addpic.text = "添加图片"
        }
        //上传是否
        if (list[p0].upload) {
            holder.uploadyes.visibility = View.VISIBLE
            holder.upload.visibility = View.GONE
        } else {
            holder.uploadyes.visibility = View.GONE
            holder.upload.visibility = View.VISIBLE
        }

        if (list[p0].taskStatusName == "未上传") {
            holder.upload.backgroundDrawable = con.resources.getDrawable(R.drawable.upload_bt_bg2_color)
        } else {
            holder.upload.backgroundDrawable = con.resources.getDrawable(R.drawable.upload_bt_bg_color)
        }

        //详细
        holder.detailed.onClick {
            (con as Task_management_Activity).goto(list[p0].formCode, list[p0].rowGuid, list[p0].taskTypeName, list[p0].pointId.toString())
        }

        holder.addpic.onClick {
            (con as Task_management_Activity).gotoAddPic(list[p0].rowGuid)
        }
        view.onClick {
            holder.detailed.performClick()
        }

        //上传
        holder.upload.onClick {
            when (list[p0].formCode) {
                "XingNengKH" -> {
                    (con as Task_management_Activity).upload2(list[p0].formCode, list[p0].rowGuid, list[p0].pointId)
                }
                else -> {
                    (con as Task_management_Activity).upload(list[p0].formCode, list[p0].rowGuid, list[p0].pointId)
                }
            }

        }

        return view

    }

    internal inner class ViewHolder(view: View) {

        var number = TextView(con)
        var stationname = TextView(con)
        var taskname = TextView(con)
        var tasktype = TextView(con)
        var planstarttime = TextView(con)
        var taskstatus = TextView(con)
        var endtime = TextView(con)
        var detailed = TextView(con)
        var upload = TextView(con)
        var uploadyes = TextView(con)
        var addpic = TextView(con)


        init {
            number = view.item_task_manager_no
            stationname = view.item_task_manager_station_name
            taskname = view.item_task_manager_task_name
            tasktype = view.item_task_manager_task_type
            planstarttime = view.item_task_manager_plan_start_time
            taskstatus = view.item_task_manager_task_status
            endtime = view.item_task_manager_end_time
            detailed = view.item_task_manager_detailed
            upload = view.item_task_manager_upload
            uploadyes = view.item_task_manager_upload_yes
            addpic = view.item_task_manager_add_pic
        }
    }
}