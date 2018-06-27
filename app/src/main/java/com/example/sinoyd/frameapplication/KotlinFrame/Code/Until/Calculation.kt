package com.example.sinoyd.frameapplication.KotlinFrame.Code.Until

import java.math.BigDecimal


/**
 * 作者： scj
 * 创建时间： 2018/5/24
 * 版权： 江苏远大信息股份有限公司
 * 描述： com.example.sinoyd.frameapplication.KotlinFrame.Code.Until
 */

class Calculation(var list: ArrayList<String>, var DecimalDigit: String, var StandardValue: String) {

    var conunt = 0
    var newlist: ArrayList<String> = ArrayList()
    var newlistcsz: ArrayList<String> = ArrayList()

    init {
        for (item in list) {
            if (item != "") {
                newlist.add(item)
            }
        }
        conunt = newlist.size


        if (DecimalDigit == "") {
            DecimalDigit = "0"
        }



        for ((idnex, item) in list.withIndex()) {
            if (idnex < 3) {
                if (item != "") {
                    newlistcsz.add(item)
                }
            }
        }

    }

    //计算平均值
    fun get平均值(): String = if (newlist.size == 0) {
        ""
    } else {
        var result1 = newlist.sumByDouble { it.toDouble() }
        var bigDecimal = BigDecimal(result1 / (newlist.size.toDouble()))
        bigDecimal.setScale(DecimalDigit.toInt(), BigDecimal.ROUND_HALF_UP).toString()


    }

    //计算标准偏差
    fun get标准偏差(): String = if (newlist.size == 0) {
        ""
    } else {
        val result2 = newlist.sumByDouble { (it.toDouble() - get平均值().toDouble()) * (it.toDouble() - get平均值().toDouble()) }
        var bigDecimal = BigDecimal(result2)
        bigDecimal.setScale(DecimalDigit.toInt(), BigDecimal.ROUND_HALF_UP).toString()
    }

    //计算准确度
    fun get准确度(): String = when {
        newlist.size == 0 -> ""
        StandardValue == "" -> ""
        else -> {
            var result3 = (get平均值().toDouble() - StandardValue.toDouble()) / StandardValue.toDouble() * 100
            var bigDecimal = BigDecimal(result3)
            bigDecimal.setScale(DecimalDigit.toInt(), BigDecimal.ROUND_HALF_UP).toString()
        }
    }

    //计算精密度
    fun get精密度(): String = when {
        newlist.size == 0 -> ""
        else -> {
            var result4 = get标准偏差().toDouble() / get平均值().toDouble() * 100
            var bigDecimal = BigDecimal(result4)
            bigDecimal.setScale(DecimalDigit.toInt(), BigDecimal.ROUND_HALF_UP).toString()
        }
    }

    //计算最低检出限
    fun get最低检出限(): String = when {
        newlist.size == 0 -> ""
        else -> {
            var result5 = get平均值().toDouble() * 3
            var bigDecimal = BigDecimal(result5)
            bigDecimal.setScale(DecimalDigit.toInt(), BigDecimal.ROUND_HALF_UP).toString()
        }
    }

    //初始值
    fun get初始值(): String = when {
        newlistcsz.size == 0 -> ""
        else -> {
            var sum = newlistcsz.sumByDouble { it.toDouble() }
            var bigDecimal = BigDecimal(sum / 3)
            bigDecimal.setScale(DecimalDigit.toInt(), BigDecimal.ROUND_HALF_UP).toString()
        }
    }

    //最大值
    fun get最大值(): String {
        var max = 0.0
        for (item in newlist) {
            if (item.toDouble() > max) {
                max = item.toDouble()
            }
        }
        return max.toString()
    }

    //最小值
    fun get最小值(): String {
        var min = newlist[0].toDouble()
        for (item in newlist) {
            if (item.toDouble() < min) {
                min = item.toDouble()
            }
        }
        return min.toString()
    }


    //零点漂移%
    fun get零点漂移(RangeValues: String): String {

        if (RangeValues == "") {
            return ""
        } else {
            var result = (get最大值().toDouble() - get初始值().toDouble()) / RangeValues.toDouble() * 100
            var bigDecimal = BigDecimal(result)
            return bigDecimal.setScale(DecimalDigit.toInt(), BigDecimal.ROUND_HALF_UP).toString()
        }

    }


    //量程漂移%

    fun get量程漂移(StandardValueL: String, InitialValue: String, RangeValueL: String): String {
        if (RangeValueL == "" || InitialValue == "" || StandardValueL == "") {
            return ""
        } else {
            var result = (get平均值().toDouble() - StandardValueL.toDouble() - InitialValue.toDouble()) / RangeValueL.toDouble()
            var bigDecimal = BigDecimal(result)
            return bigDecimal.setScale(DecimalDigit.toInt(), BigDecimal.ROUND_HALF_UP).toString()
        }

    }

    //相关系数r
    fun get相关系数r(): String {
        return ""
    }

    //线性方程
    fun get线性方程(): String {
        return ""
    }
}


