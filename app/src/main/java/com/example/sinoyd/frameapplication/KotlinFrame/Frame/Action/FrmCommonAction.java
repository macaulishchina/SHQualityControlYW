package com.example.sinoyd.frameapplication.KotlinFrame.Frame.Action;


import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Uitl.HttpUtil;
import com.google.gson.JsonObject;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 作者： 王一凡
 * 创建时间： 2017/9/5
 * 版权： 江苏远大信息股份有限公司
 * 描述：程序主控制类 #处理网络请求、获取用户登陆信息#
 */
public class FrmCommonAction {
    /**
     * POST请求标志
     */
    public static final int POST = 1;
    /**
     * GET请求标志
     */
    public static final int GET = 2;
    /**
     * PUT请求标志
     */
    public static final int PUT = 3;
    /**
     * DELETE请求标志
     */
    public static final int DELETE = 4;



    /**
     * 基于HttpURLConnection的网络请求通用方法
     * @param paras 请求携带参数
     * @param method 请求方法名称
     * @param url 请求地址
     * @param requestType GET/POST/PUT
     * @return
     */
    public static JsonObject request(JsonObject paras, String method, String url, int requestType) {
        JsonObject bs = null;
        if (!url.endsWith(method)) {
            url += "/" + method;
        }
        try {
            if(requestType == POST || requestType == PUT){
                //POST、PUT请求
                String requestBody = "";
                if(paras != null){
                    requestBody = paras.toString();
                }
                bs = HttpUtil.request(url, requestBody, requestType);
            } else if(requestType == GET || requestType == DELETE) {
                //GET、DELETE请求
                if (paras != null) {
                    url += "?";
                    Set set = paras.entrySet();
                    Iterator sIterator = set.iterator();
                    while (sIterator.hasNext()) {
                        Map.Entry me = (Map.Entry) sIterator.next();
                        // 获得key
                        String key = me.getKey().toString();
                        // 根据key获得value, value也可以是JSONObject,JSONArray,使用对应的参数接收即可
                        String value = paras.get(key).getAsString();
                        if (!sIterator.hasNext()) {
                            //最后一个元素
                            url += (key + "=" + value);
                        } else {
                            url += (key + "=" + value + "&");
                        }
                    }
                }
                bs = HttpUtil.request(url, null, requestType);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return bs;
    }
}
