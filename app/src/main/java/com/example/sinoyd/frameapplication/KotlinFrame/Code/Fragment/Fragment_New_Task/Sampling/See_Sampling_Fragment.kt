package com.example.sinoyd.frameapplication.KotlinFrame.Code.Fragment.Fragment_New_Task.Sampling

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Fragment.BaseFragment
import com.example.sinoyd.frameapplication.R

/**查看采样记录**/
class See_Sampling_Fragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val conview = inflater.inflate(R.layout.fragment_see_sampling_layout, null)
        setview(conview)
        return conview
    }

    //控件的获取
    private fun setview(conview: View) {

    }


}