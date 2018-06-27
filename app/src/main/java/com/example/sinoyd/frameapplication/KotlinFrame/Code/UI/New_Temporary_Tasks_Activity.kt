package com.example.sinoyd.frameapplication.KotlinFrame.Code.UI

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.sinoyd.frameapplication.KotlinFrame.UI.BaseActivity
import com.example.sinoyd.frameapplication.R
import kotlinx.android.synthetic.main.activity_new_temporary_tasks_.*
import org.jetbrains.anko.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**新建临时任务**/
class New_Temporary_Tasks_Activity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_temporary_tasks_)
        //设置监听
        setlisteners()
    }

    private fun setlisteners() {

        //准确度和精密度
        newAccuracyandprecision.onClick {
            startActivity<New_Accuracy_and_precision_Activity>("rowGuid" to "新建临时任务", "TaskTypeName" to "周期任务")
        }
        //水源地水质自动
        newWaterqualityanto.onClick {
            startActivity<New_Water_quality_anto_Activity>()
        }
        //盲样考核
        newBlindnessexamination.onClick {
            startActivity<New_Blindness_examination_Activity>()
        }

        /**************************************************************/
        //实样比对
        newRealsamplecomparison.onClick {
            startActivity<New_Realsample_comparison_Activity>("rowGuid" to "新建临时任务", "TaskTypeName" to "周期任务")
        }
          //水质巡检
        newwaterMonitoringinspection.onClick {
            startActivity<New_Water_Monitoring_Inspection_Activity>("rowGuid" to "新建临时任务", "TaskTypeName" to "周期任务")
        }
        //标样核查
       newStandardsolutionverification.onClick {
            startActivity<New_Standard_solution_verification_Activity>("rowGuid" to "新建临时任务", "TaskTypeName" to "周期任务")
        }

        //加标回收
        newStandardRecovery.onClick {
            startActivity<New_Standard_Recovery_Activity>("rowGuid" to "新建临时任务", "TaskTypeName" to "周期任务")
        }

        //性能考核
        newPerformanceassessment.onClick {
            startActivity<New_Performance_assessment_Activity>("rowGuid" to "新建临时任务", "TaskTypeName" to "周期任务")
        }
    }
}
