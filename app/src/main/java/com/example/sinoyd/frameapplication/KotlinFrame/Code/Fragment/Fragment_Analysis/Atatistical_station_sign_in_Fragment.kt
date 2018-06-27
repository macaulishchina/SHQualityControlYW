package com.example.sinoyd.frameapplication.KotlinFrame.Code.Fragment.Fragment_Analysis

import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Dataclass.Idnamevalue
import com.example.sinoyd.frameapplication.R
import java.util.ArrayList


/**站点签到情况**/
class Atatistical_station_sign_in_Fragment : Fragment() {
    var list: ArrayList<Idnamevalue> = ArrayList()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val conview = inflater.inflate(R.layout.fragment_atatistical_station_sign_in_layout, null)
        setview(conview)
        return conview
    }

    //控件的获取
    private fun setview(conview: View) {

    }


}