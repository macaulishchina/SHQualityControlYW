package com.example.sinoyd.frameapplication.KotlinFrame.UI

import android.os.Bundle
import android.app.AlertDialog
import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Dialog.DateTimePickerControl
import com.example.sinoyd.frameapplication.R
import kotlinx.android.synthetic.main.activity_time_dialogdemo.*
import org.jetbrains.anko.act
import org.jetbrains.anko.onClick
import org.jetbrains.anko.toast

class TimeDialogdemoActivity : BaseActivity() {
    var type: String = "datetime"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_dialogdemo)
        rg.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                R.id.rb1 -> {
                    type = "datetime"
                }
                R.id.rb2 -> {
                    type = "date"
                }
                R.id.rb3 -> {
                    type = "time"
                }
            }
        }
        dialogbutton.onClick {
            var dateDialog = DateTimePickerControl(act, AlertDialog.THEME_HOLO_LIGHT, type, "#0f82ff")
            dateDialog.SetDateSelectListener { datestring -> toast(datestring) }
            dateDialog.show()
        }

        usger.text = """
var dateDialog = DateTimePickerControl(act, AlertDialog.THEME_HOLO_LIGHT, type, "#0f82ff")
dateDialog.SetDateSelectListener { datestring -> toast(datestring) }
dateDialog.show()
            """
    }
}
