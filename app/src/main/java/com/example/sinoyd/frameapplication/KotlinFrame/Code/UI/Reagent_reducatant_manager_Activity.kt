package com.example.sinoyd.frameapplication.KotlinFrame.Code.UI

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.sinoyd.frameapplication.R
import com.example.sinoyd.frameapplication.R.id.ll_reagent
import com.example.sinoyd.frameapplication.R.id.ll_reductant
import kotlinx.android.synthetic.main.activity_reagent_standard_liquid_configuration_.*
import org.jetbrains.anko.onClick
import org.jetbrains.anko.startActivity

/**试剂标液管理**/
class Reagent_reducatant_manager_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reagent_standard_liquid_configuration_)
        //设置监听
        setlisteners()
    }

    private fun setlisteners() {
        //试剂管理
        ll_reagent.onClick {
            startActivity<Reagent_manager_Activity>()
        }

        //标液管理
        ll_reductant.onClick {
            startActivity<Reductant_manager_Activity>()
        }
    }
}
