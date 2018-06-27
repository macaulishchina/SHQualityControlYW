package com.example.sinoyd.frameapplication.KotlinFrame.Code.db

import org.xutils.db.annotation.Column

/**
 * 作者： scj
 * 创建时间： 2018/5/11
 * 版权： 江苏远大信息股份有限公司
 * 描述： com.example.sinoyd.frameapplication.KotlinFrame.Code.db
 */


interface IFormTaskFactor {

    //因子编号  英文名*
    var PollutantCode: String
    //因子名称
    var PollutantName: String
    //单位
    var unit: String
    //标液编号Guid
    var StaLiquidGuid: String
    //稀释方式
    var DilutionMethod: String
    //使用量单位
    var RemainderUnit: String

    /***下面是个是标样需要的参数***/
    //标液编号*
    var StanLiquidCode: String
    //标液使用量*
    var UseAmount: String
    //标准值*
    var StanValue: String
    //标准值单位*
    var StanvalueUnit: String
    //定容体积
    var Capacity: String
    //定容体积单位
    var CapacityUnit: String

    //剩余量*
    var SurplusAmount: String
    //因子值[测试值]*
    var PollutantValue: String
    var PollutantValue2: String
    var PollutantValue3: String
    var PollutantValue4: String
    var PollutantValue5: String
    var PollutantValue6: String
    //任务id*
    var TaskGuid: String


    /***加标回收需要用***/
    //加标量
    var AddValue: String
    //加标测试值
    var PollutantValueAdd: String

    //仪器
    var InstrumentName: String
    //仪器GUID
    var InstrumentGuid: String


    var AverageValue: String//平均值

    var StandardDeviation: String//标准偏差

    var AccuracyValue: String//准确度

    var PrecisionValue: String//精密度

    var DecimalDigit: String//小数位数


}
