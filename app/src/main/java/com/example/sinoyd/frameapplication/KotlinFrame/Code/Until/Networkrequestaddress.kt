package com.sinoyd.Code.Until

/**
 * 作者： scj
 * 创建时间： 2018/1/30
 * 版权： 江苏远大信息股份有限公司
 * 描述： com.sinoyd.Code.Until
 */

object Networkrequestaddress {
    //账号：admin    密码：sinoyd123!@%23
    //账号：leici    密码：leici2017
    var RELEASE: Boolean = false
    var SERVER_URL = ""
    val Formaladdress: String = "http://192.168.11.178:9527"
    val Testaddress: String = "http://192.168.90.177:8888"//苏州服务器
//    val Testaddress: String = "http://192.168.90.3:8888"//lcm本机

    init {
        SERVER_URL = if (RELEASE) Formaladdress else Testaddress
    }

    /**登陆**/
    val URL_User = SERVER_URL + "/api/v1/QualityConOperation/QualiConOperApp/User"
    /**下载任务**/
    val URL_Task = SERVER_URL + "/api/v1/QualityConOperation/QualiConOperApp/Samp"
    /**上传任务**/
    val URL_Up = SERVER_URL + "/api/v1/QualityConOperation/QualiConOperApp/Up"
    /**获取站点**/
    val URL_PointInfo = SERVER_URL + "/api/v1/QualityConOperation/QualiConOperApp/PointInfo"
    /**获取站点所对应因子**/
    val URL_FactorInfo = SERVER_URL + "/api/v1/QualityConOperation/QualiConOperApp/FactorInfo"
    /**版本更新**/
    val URL_Version = SERVER_URL + "/api/v1/QualityConOperation/QualiConOperApp/Version"
    /**周巡检获取配置**/
    val URL_GetInspectionInfo = SERVER_URL + "/api/v1/QualityControl/GetInspectionInfo"
    /**获取性能考核配置**/
    val URL_PerforDown = SERVER_URL + "/api/v1/QualityConOperation/QualiConOperApp/PerforDown"
    /**性能考核上传接口**/
    val URL_PerforUp = SERVER_URL + "/api/v1/QualityConOperation/QualiConOperApp/PerforUp"

}