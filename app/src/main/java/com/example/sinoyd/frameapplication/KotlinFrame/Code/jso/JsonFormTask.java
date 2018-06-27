package com.example.sinoyd.frameapplication.KotlinFrame.Code.jso;

import com.example.sinoyd.frameapplication.KotlinFrame.Code.db.FormTask;
import com.example.sinoyd.frameapplication.KotlinFrame.Code.db.FormTaskFactor;
import com.google.gson.Gson;

import java.util.List;

/**
 * 作者： scj
 * 创建时间： 2018/5/10
 * 版权： 江苏远大信息股份有限公司
 * 描述： com.example.sinoyd.frameapplication.KotlinFrame.Code.jso
 */


public class JsonFormTask {

    /**
     * Task : {"RowGuid":"AD9C98EE-A27D-4F5D-BD78-C789153CE66C","OperationCompany":"62e03aa2-6079-4a25-a392-a643a5823a6b","TaskCode":"Test20180508","TaskName":"任务名称","PointId":"45","FormGuid":"417e8fc3-8a07-41c2-bfba-3f270f1cf9b0","EndTime":"2018-05-10 23:00:00","StartDate":"2018-05-10 23:00:00","EndDate":"2018-05-10 23:00:00","TaskType":"任务类型","TaskStatus":"Completed"}
     * TaskData : [{"PollutantCode":"W01001","PollutantValue":"2.1","StandardValue":"标液使用量"},{"PollutantCode":"W01010","PollutantValue":"1.1","StandardValue":"2.1"}]
     */

    private FormTask Task;
    private List<FormTaskFactor> TaskData;

    public FormTask getTask() {
        return Task;
    }

    public void setTask(FormTask task) {
        Task = task;
    }

    public List<FormTaskFactor> getTaskData() {
        return TaskData;
    }

    public void setTaskData(List<FormTaskFactor> taskData) {
        TaskData = taskData;
    }

    @Override
    public String toString() {
        Gson gs = new Gson();
        return gs.toJson(this);
    }
}
