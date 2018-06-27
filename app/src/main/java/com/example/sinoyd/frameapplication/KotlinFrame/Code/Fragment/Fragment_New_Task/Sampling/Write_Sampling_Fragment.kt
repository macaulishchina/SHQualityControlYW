package com.example.sinoyd.frameapplication.KotlinFrame.Code.Fragment.Fragment_New_Task.Sampling

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Dataclass.Idnamevalue
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Fragment.BaseFragment
import com.example.sinoyd.frameapplication.R
import com.sinoyd.Code.Until.DisplayorhideSoftkeyboard
import java.util.ArrayList

/**填写采样记录**/
class Write_Sampling_Fragment : BaseFragment() {
    var list: ArrayList<Idnamevalue> = ArrayList()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val conview = inflater.inflate(R.layout.fragment_write_sampling_layout, null)
        //强制隐藏软键盘
        DisplayorhideSoftkeyboard.hideSoftkeyboard(activity)
        setview(conview)
        return conview
    }

    //控件的获取
    private fun setview(conview: View) {

    }


}