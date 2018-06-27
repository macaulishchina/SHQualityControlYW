package com.example.sinoyd.frameapplication.KotlinFrame.Frame.Fragment

import android.annotation.SuppressLint
import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Adapter.Todoadapter
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Dataclass.Idnamevalue
import com.example.sinoyd.frameapplication.KotlinFrame.UI.*
import com.example.sinoyd.frameapplication.R
import kotlinx.android.synthetic.main.test_fragment_one.view.*
import org.jetbrains.anko.onItemClick
import org.jetbrains.anko.startActivity
import java.util.*

@SuppressLint("ValidFragment")
class OneFragment(var name: String = "首页") : Fragment() {
    var list: ArrayList<Idnamevalue> = ArrayList()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val conview = inflater.inflate(R.layout.test_fragment_one, null)
        setview(conview)
        setlisterens(conview)
        return conview
    }

    //控件的获取
    private fun setview(conview: View) {
        list = ArrayList()
        list.add(Idnamevalue("0", "Gridview_实现柱状图"))
        list.add(Idnamevalue("1", "Gridview_实现单选or多选"))
        list.add(Idnamevalue("2", "二维列表"))
        list.add(Idnamevalue("3", "选择栏"))
        list.add(Idnamevalue("4", "仿ios弹出提示框"))
        list.add(Idnamevalue("5", "时间区域选择框"))
        list.add(Idnamevalue("6", "时间选择栏"))
        list.add(Idnamevalue("7", "单个时间选择,[datetime,date,time]"))
        var todoadapter = Todoadapter(list)
        conview.testlistview.adapter = todoadapter
    }

    //控件的监听事件
    private fun setlisterens(conview: View) {
        conview.testlistview.onItemClick { _, _, i, _ ->
            when (i) {
                0 -> {
                    //柱状图
                    activity.startActivity<BarChartActivity>()
                }
                1 -> {
                    //单选or多选
                    activity.startActivity<SingleMoreActivity>()
                }
                2 -> {
                    //二维列表
                    activity.startActivity<TwodimensionallistActivity>()
                }
                3 -> {
                    //选择栏
                    activity.startActivity<SelectionbarActivity>()
                }
                4 -> {
                    //仿ios弹出提示框
                    activity.startActivity<ImitateIOSActivity>()
                }
                5 -> {
                    //时间区域选择框
                    activity.startActivity<TimeselectiondialogActivity>()
                }
                6 -> {
                    //时间选择栏
                    activity.startActivity<TimeSelectionBarActivity>()
                }
                7 -> {
                    //自定义格式选择框
                    activity.startActivity<TimeDialogdemoActivity>()
                }

            }

        }

    }


}