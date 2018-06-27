package com.example.sinoyd.frameapplication.KotlinFrame.Code.UI

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import com.example.sinoyd.frameapplication.KotlinFrame.Code.Fragment.Fragment_New_Task.Sampling.See_Sampling_Fragment
import com.example.sinoyd.frameapplication.KotlinFrame.Code.Fragment.Fragment_New_Task.Sampling.Write_Sampling_Fragment
import com.example.sinoyd.frameapplication.R
import kotlinx.android.synthetic.main.activity_new__sampling_record_.*

/**采样记录**/
class New_Sampling_record_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new__sampling_record_)
        rb1.isChecked = true
        tv_save.visibility = View.GONE
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
                    0 -> Write_Sampling_Fragment()
                    1 -> See_Sampling_Fragment()
                    else -> Write_Sampling_Fragment()
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
                R.id.rb1 -> {
                    vp.currentItem = 0
                    rb1.isChecked = true
                    tv_save.visibility = View.GONE
                }
                R.id.rb2 -> {
                    vp.currentItem = 1
                    rb2.isChecked = true
                    tv_save.visibility = View.VISIBLE
                }
            }
        }

        //viewpager的滑动监听
        vp.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        rb1.isChecked = true
                        rb2.isChecked = false
                        tv_save.visibility = View.GONE
                    }
                    1 -> {
                        rb1.isChecked = false
                        rb2.isChecked = true
                        tv_save.visibility = View.VISIBLE
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }
}
