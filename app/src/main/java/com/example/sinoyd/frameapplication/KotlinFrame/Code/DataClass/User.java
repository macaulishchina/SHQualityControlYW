package com.example.sinoyd.frameapplication.KotlinFrame.Code.DataClass;

import java.util.List;

/**
 * 作者： scj
 * 创建时间： 2018/5/7
 * 版权： 江苏远大信息股份有限公司
 * 描述： com.example.sinoyd.frameapplication.KotlinFrame.Code.DataClass
 */


public class User {

    /**
     * Result : True
     * data : [{"RowGuid":"4ce5bed9-78bd-489f-8b3f-a830098759c4"}]
     */

    private String Result;
    private String message;
    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String Result) {
        this.Result = Result;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * RowGuid : 4ce5bed9-78bd-489f-8b3f-a830098759c4
         */

        private String RowGuid;
        private String LoginID;
        private String DisplayName;

        public String getLoginID() {
            return LoginID;
        }

        public void setLoginID(String loginID) {
            LoginID = loginID;
        }

        public String getDisplayName() {
            return DisplayName;
        }

        public void setDisplayName(String displayName) {
            DisplayName = displayName;
        }

        public String getRowGuid() {
            return RowGuid;
        }

        public void setRowGuid(String RowGuid) {
            this.RowGuid = RowGuid;
        }
    }
}
