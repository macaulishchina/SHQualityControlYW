package com.example.sinoyd.frameapplication.KotlinFrame.UI

import android.os.Bundle
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Dataclass.getTwodimensionallistdata
import com.example.sinoyd.frameapplication.R
import kotlinx.android.synthetic.main.test_activity_twodimensionallist.*

/**
 * 二维列表  demo 使用方法
 */
class TwodimensionallistActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_activity_twodimensionallist)

        twodimensionallistview.initview().setdata(getTwodimensionallistdata()).show()


        tvusage.text="""
            twodimensionallistview
            .initview()
                //数据类型 【GVkeyvalue】
            .setdata(getTwodimensionallistdata())
            .show()
            """
    }
}
