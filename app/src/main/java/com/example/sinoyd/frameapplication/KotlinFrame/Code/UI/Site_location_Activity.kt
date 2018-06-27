package com.example.sinoyd.frameapplication.KotlinFrame.Code.UI

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.sinoyd.frameapplication.R
import kotlinx.android.synthetic.main.activity_site_location_.*
import org.jetbrains.anko.onClick

/**站点定位**/
class Site_location_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_site_location_)
        //监听时间
        setlisteners()
    }

    private fun setlisteners() {
        //返回主页
        iv_home.onClick {
            finish()
        }
    }
}
