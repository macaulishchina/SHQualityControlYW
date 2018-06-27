package com.example.sinoyd.frameapplication.KotlinFrame.Code.UI

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import com.example.sinoyd.frameapplication.KotlinFrame.Code.Fragment.Fragment_Manage.Reductant_manager_biaoyepeizhi_Fragment
import com.example.sinoyd.frameapplication.KotlinFrame.Code.Fragment.Fragment_Manage.Reductant_manager_peizhiqingdan_Fragment
import com.example.sinoyd.frameapplication.KotlinFrame.Code.Fragment.Fragment_Manage.Reductant_manager_xianxingyouxiaobiaoye_Fragment
import com.example.sinoyd.frameapplication.R
import kotlinx.android.synthetic.main.activity_reductant_manager.*

class Reductant_manager_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reductant_manager)
        rb1.isChecked = true
        //设置监听
        setlisteners()
        //设置默认的Fragment   默认第一个界面"0"
        setDefaultFragment()
    }

    //设置默认的Fragment   默认第一个界面"0"
    private fun setDefaultFragment() {
        vp.currentItem = 0
        tv_upload.visibility = View.GONE
        vp.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return when (position) {
                    0 -> Reductant_manager_biaoyepeizhi_Fragment()
                    1 -> Reductant_manager_peizhiqingdan_Fragment()
                    2 -> Reductant_manager_xianxingyouxiaobiaoye_Fragment()
                    else -> Reductant_manager_biaoyepeizhi_Fragment()
                }
            }

            override fun getCount(): Int {
                return 3
            }

        }
    }

    //设置监听
    private fun setlisteners() {
        //rg监听事件
        rg.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                R.id.rb1 -> {
                    vp.currentItem = 0
                    tv_upload.visibility = View.GONE
                }
                R.id.rb2 -> {
                    vp.currentItem = 1
                    tv_upload.visibility = View.VISIBLE
                }
                R.id.rb3 -> {
                    vp.currentItem = 2
                    tv_upload.visibility = View.GONE
                }
            }
        }

        //viewpager
        vp.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        rb1.isChecked = true
                        rb2.isChecked = false
                        rb3.isChecked = false
                        tv_upload.visibility = View.GONE
                    }
                    1 -> {
                        rb1.isChecked = false
                        rb2.isChecked = true
                        rb3.isChecked = false
                        tv_upload.visibility = View.VISIBLE
                    }
                    2 -> {
                        rb1.isChecked = false
                        rb2.isChecked = false
                        rb3.isChecked = true
                        tv_upload.visibility = View.GONE
                    }
                }
            }

        })
    }
}
