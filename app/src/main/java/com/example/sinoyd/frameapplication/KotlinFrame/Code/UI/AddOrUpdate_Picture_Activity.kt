package com.example.sinoyd.frameapplication.KotlinFrame.Code.UI

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.sinoyd.frameapplication.KotlinFrame.UI.BaseActivity
import com.example.sinoyd.frameapplication.R
import org.jetbrains.anko.onClick


class AddOrUpdate_Picture_Activity : BaseActivity() {


    private val mButtonAddPic: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_picture_activity)
        Log.i("hyd","successfully launch AddOrUpdate_Picture_Activity with guid: ${intent.getStringExtra("rowGuid")}")
        Toast.makeText(this,"guid=${intent.getStringExtra("rowGuid")}",Toast.LENGTH_LONG).show()
        mButtonAddPic?.onClick {
            Toast.makeText(this,"guid=${intent.getStringExtra("rowGuid")}",Toast.LENGTH_LONG).show()
        }
    }

}
