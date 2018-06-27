package com.example.sinoyd.frameapplication.KotlinFrame.Code.Fragment.Fragment_Manage

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shenchuanjiang.kotlin1013test.CommonSelector
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Dataclass.Idnamevalue
import com.example.sinoyd.frameapplication.R
import com.sinoyd.Code.Until.DisplayorhideSoftkeyboard
import kotlinx.android.synthetic.main.fragment_biaoyepeizhi_reductant_layout.*
import kotlinx.android.synthetic.main.fragment_biaoyepeizhi_reductant_layout.view.*
import org.jetbrains.anko.onClick
import java.util.ArrayList

/**标液管理**/
/**标液配置**/
class Reductant_manager_biaoyepeizhi_Fragment : Fragment() {
    var listoptype: ArrayList<Idnamevalue> = ArrayList()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val conview = inflater.inflate(R.layout.fragment_biaoyepeizhi_reductant_layout, null)
        DisplayorhideSoftkeyboard.hideSoftkeyboard(activity)
        listoptype.add(Idnamevalue("1", "标准溶液"))
        listoptype.add(Idnamevalue("2", "有证物质"))
        conview.tvoptype.text = listoptype[0].namevalue
        setview(conview)
        setlisteners(conview)
        return conview
    }

    //设置监听
    private fun setlisteners(conview: View) {
        //仪器类型
        conview.optype.onClick {
            var comm = CommonSelector(activity, listoptype, object : CommonSelector.OnSelectClickListener {
                override fun onCommonItemSelect(postions: Int) {
                    tvoptype.text = listoptype[postions].namevalue
                    if (listoptype[postions].namevalue == "标准溶液") {
                        tv_hunbiao.visibility = View.VISIBLE
                    } else {
                        tv_hunbiao.visibility = View.GONE
                    }
                }
            })
            comm.showPop()
        }

    }

    //控件的获取
    private fun setview(conview: View) {

    }


}