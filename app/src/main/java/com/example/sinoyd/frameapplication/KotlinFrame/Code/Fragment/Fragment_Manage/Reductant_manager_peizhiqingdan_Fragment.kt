package com.example.sinoyd.frameapplication.KotlinFrame.Code.Fragment.Fragment_Manage

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Dataclass.Idnamevalue
import com.example.sinoyd.frameapplication.R
import java.util.ArrayList
/**标液管理**/
/**配置清单**/
class Reductant_manager_peizhiqingdan_Fragment : Fragment() {
    var list: ArrayList<Idnamevalue> = ArrayList()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val conview = inflater.inflate(R.layout.fragment_peizhiqingdan_reductant_layout, null)
        setview(conview)
        return conview
    }

    //控件的获取
    private fun setview(conview: View) {

    }


}