package com.example.sinoyd.frameapplication.KotlinFrame.Code.UI

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import com.example.sinoyd.frameapplication.KotlinFrame.Code.Fragment.Fragment_Analysis.Atatistical_station_sign_in_Fragment
import com.example.sinoyd.frameapplication.KotlinFrame.Code.Fragment.Fragment_Analysis.Atatistical_yw_info_Fragment
import com.example.sinoyd.frameapplication.R
import kotlinx.android.synthetic.main.activity_atatistical_analysis_.*
import org.jetbrains.anko.onClick
import android.support.v4.view.ViewPager
import android.view.View
import com.example.sinoyd.frameapplication.KotlinFrame.UI.BaseActivity


/**统计分析**/
class Atatistical_analysis_Activity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_atatistical_analysis_)
        rbsign.isChecked = true
        ll_color.visibility = View.GONE
        //设置监听
        setlisteners()
        //设置默认的Fragment   默认第一个界面"0"
        setDefaultFragment()
    }

    //设置默认的Fragment   默认第一个界面"0"
    private fun setDefaultFragment() {
        vp.currentItem = 0
        vp.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return when (position) {
                    0 -> Atatistical_station_sign_in_Fragment()
                    1 -> Atatistical_yw_info_Fragment()
                    else -> Atatistical_station_sign_in_Fragment()
                }
            }

            override fun getCount(): Int {
                return 2
            }

        }
    }


    //设置监听
    private fun setlisteners() {
        //rg监听
        rg.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                R.id.rbsign -> {
                    vp.currentItem = 0
                    ll_color.visibility = View.GONE
                }
                R.id.rbyw -> {
                    vp.currentItem = 1
                    rbyw.isChecked = true
                    ll_color.visibility = View.VISIBLE
                }
            }
        }
        //返回主页
        iv_home.onClick {
            finish()
        }
        //viewpager的滑动监听
        vp.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        rbsign.isChecked = true
                        rbyw.isChecked = false
                        ll_color.visibility = View.GONE
                    }
                    1 -> {
                        rbsign.isChecked = false
                        rbyw.isChecked = true
                        ll_color.visibility = View.VISIBLE
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }
}
