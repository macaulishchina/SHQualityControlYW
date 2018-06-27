package com.example.sinoyd.frameapplication.KotlinFrame.Code.DataClass;

import java.util.List;

/**
 * 作者： scj
 * 创建时间： 2018/5/24
 * 版权： 江苏远大信息股份有限公司
 * 描述： com.example.sinoyd.frameapplication.KotlinFrame.Code.DataClass
 */


public class Version {

    /**
     * Result : True
     * message :
     * Version : [{"verName":"0.0.2","verCode":"2","verDesc":"质控运维,表单任务","verPath":"http://222.92.42.178:8083/EnvWaterKS/Environment.apk"}]
     */

    private String Result;
    private String message;
    private List<VersionBean> Version;

    public String getResult() {
        return Result;
    }

    public void setResult(String Result) {
        this.Result = Result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<VersionBean> getVersion() {
        return Version;
    }

    public void setVersion(List<VersionBean> Version) {
        this.Version = Version;
    }

    public static class VersionBean {
        /**
         * verName : 0.0.2
         * verCode : 2
         * verDesc : 质控运维,表单任务
         * verPath : http://222.92.42.178:8083/EnvWaterKS/Environment.apk
         */

        private String verName;
        private String verCode;
        private String verDesc;
        private String verPath;

        public String getVerName() {
            return verName;
        }

        public void setVerName(String verName) {
            this.verName = verName;
        }

        public String getVerCode() {
            return verCode;
        }

        public void setVerCode(String verCode) {
            this.verCode = verCode;
        }

        public String getVerDesc() {
            return verDesc;
        }

        public void setVerDesc(String verDesc) {
            this.verDesc = verDesc;
        }

        public String getVerPath() {
            return verPath;
        }

        public void setVerPath(String verPath) {
            this.verPath = verPath;
        }
    }
}
