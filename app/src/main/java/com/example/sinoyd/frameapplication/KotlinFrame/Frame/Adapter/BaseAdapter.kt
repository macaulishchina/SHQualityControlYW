package com.example.sinoyd.frameapplication.KotlinFrame.Frame.Adapter

import android.content.Context
import android.view.LayoutInflater

import java.util.ArrayList

/**
 * 万能的Adapter，作为各具体使用的Adapter的父类
 *
 * @param <T> 需要处理的数据的类型
</T> */
abstract class BaseAdapter<T>(context: Context, data: List<T>) : android.widget.BaseAdapter() {
    /**
     * Context
     */
    /**
     * 获取Context对象
     *
     * @return Context对象
     */
    /**
     * 设置Context
     *
     * @param context
     */
    protected var context: Context? = null
        private set(context) {
            if (context == null) {
                throw IllegalArgumentException("参数Context不允许为null！！！")
            }
            field = context
        }
    /**
     * 数据源
     */
    /**
     * 获取数据源
     *
     * @return 数据的List集合
     */
    /**
     * 设置数据源
     *
     * @param data 数据的List集合
     */
    protected var data: List<T>? = null
        private set(data) {
            var data = data
            if (data == null) {
                data = ArrayList()
            }
            field = data
        }
    /**
     * 将XML加载为View对象的工具
     */
    /**
     * 获取LayoutInflater对象
     *
     * @return LayoutInflater对象
     */
    protected var layoutInflater: LayoutInflater? = null
        private set

    init {
        setLayoutInflater()
    }

    /**
     * 设置LayoutInflater属性的值
     */
    private fun setLayoutInflater() {
        this.layoutInflater = LayoutInflater.from(this.context)
    }

    override fun getCount(): Int {
        return this.data!!.size
    }

    override fun getItem(position: Int): T {
        return data!![position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

}