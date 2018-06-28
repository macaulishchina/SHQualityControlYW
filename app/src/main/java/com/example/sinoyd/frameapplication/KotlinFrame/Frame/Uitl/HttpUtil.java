package com.example.sinoyd.frameapplication.KotlinFrame.Frame.Uitl;

import android.text.TextUtils;
import android.util.Log;

import com.example.sinoyd.frameapplication.KotlinFrame.Frame.Action.FrmCommonAction;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * 作者： 王一凡
 * 创建时间： 2017/9/5
 * 版权： 江苏远大信息股份有限公司
 * 描述： 网络请求工具类
 */
public class HttpUtil {
    private final static int CONNENT_TIMEOUT = 20000;
    private final static int READ_TIMEOUT = 20000;
    private final static String Authorization_Type = "Bearer ";//验证类型

    /**
     * @function trustAllHosts
     * @Description 信任所有主机-对于任何证书都不做检查
     */
    private static void trustAllHosts() {
        TrustManager[] arrayOfTrustManager = new TrustManager[1];
        //实现自己的信任管理器类
        arrayOfTrustManager[0] = new X509TrustManager() {
            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[0];
            }

        };
        try {
            SSLContext localSSLContext = SSLContext.getInstance("TLS");
            localSSLContext.init(null, arrayOfTrustManager, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(localSSLContext.getSocketFactory());
            return;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }
    static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    /**
     * @param httpsurl
     * @param data
     * @return
     * @function
     * @Description https post/get方法，返回值是https请求
     */
    public static JsonObject request(String httpsurl, String data, int requestType) {
        String logRequest = httpsurl;
        if (data != null) {
            logRequest += " [请求参数：]" + data;
        }
        Log.i("wyf","[请求地址：]" + logRequest);
        LogUtil.writeLogThread("[请求地址：]", logRequest);

        int responseCode = -1;
        String result = null;
        HttpURLConnection http = null;
        URL url;
        try {
            url = new URL(httpsurl);
            // 判断是http请求还是https请求
            if (url.getProtocol().toLowerCase().equals("https")) {
                trustAllHosts();
                http = (HttpsURLConnection) url.openConnection();
                ((HttpsURLConnection) http).setHostnameVerifier(DO_NOT_VERIFY);// 对所有主机都进行确认
            } else {
                http = (HttpURLConnection) url.openConnection();
            }

            http.setConnectTimeout(CONNENT_TIMEOUT);// 设置超时时间
            http.setReadTimeout(READ_TIMEOUT);
            http.setRequestProperty("User-Agent", "App-Android/0.1 "+ getUserAgent());
            //H5+项目 添加头Authorization验证
//            if(!TextUtils.isEmpty(FrmCommonAction.getUserToken())){
//                http.addRequestProperty("Authorization", Authorization_Type + FrmCommonAction.getUserToken());
//            }
            if(requestType == FrmCommonAction.GET) {
                http.setRequestMethod("GET");// 设置请求类型为get
                http.setDoInput(true);
            } else if(requestType == FrmCommonAction.DELETE) {
                http.setRequestMethod("DELETE");// 设置请求类型为delete
            } else if(requestType == FrmCommonAction.POST){
                http.setRequestMethod("POST");// 设置请求类型为post
                http.setDoInput(true);
                http.setDoOutput(true);
                http.setRequestProperty("Content-Type", "application/json");
                http.setRequestProperty("Accept", "application/json");
                DataOutputStream out = new DataOutputStream(http.getOutputStream());
                out.writeBytes(cn2Unicode(data));
                out.flush();
                out.close();
            }else if(requestType == FrmCommonAction.PUT){
                http.setRequestMethod("PUT");// 设置请求类型为put
                http.setDoInput(true);
                http.setDoOutput(true);
                http.setRequestProperty("Content-Type", "application/json");
                http.setRequestProperty("Accept", "application/json");
                DataOutputStream out = new DataOutputStream(http.getOutputStream());
                out.writeBytes(cn2Unicode(data));
                out.flush();
                out.close();
            }

            // 判断返回状态
            responseCode = http.getResponseCode();
            BufferedReader in = null;
            if (responseCode >= 200 && responseCode < 300) {
                in = new BufferedReader(new InputStreamReader(http.getInputStream()));
            } else {
                in = new BufferedReader(new InputStreamReader(http.getErrorStream()));
            }
            String temp = in.readLine();
            while (temp != null) {
                if (result != null) {
                    result += temp;
                }else {
                    result = temp;
                }
                temp = in.readLine();
            }
            in.close();
            http.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        result = convert(result);
        //拼接返回Json数据
        JsonObject returnJsonObject = null;
        try {
            returnJsonObject = new JsonObject();
            returnJsonObject.addProperty("code", responseCode);
            if (result != null) {
                JsonObject resultJsonObject = new JsonParser().parse(dealWithBs(result)).getAsJsonObject();
                returnJsonObject.add("body", resultJsonObject);
            }
            Log.i("wyf", "[返回数据：]" + returnJsonObject.toString());
            LogUtil.writeLogThread("[返回数据：]", returnJsonObject.toString());
        }catch (Exception e) {
            e.printStackTrace();
        }
        return returnJsonObject;

    }

    /**
     * post文件上传
     * @param uploadUrl
     * @param params
     * @param file
     * @return
     * @throws IOException
     */
    public static JsonObject post(String uploadUrl, Map<String, String> params, File file, String mimeType) throws IOException {
        Log.i("wyf","[附件上传地址：]" + uploadUrl);
        LogUtil.writeLogThread("[附件上传地址：]", uploadUrl);
        int responseCode = -1;
        HttpURLConnection http = null;
        URL url = new URL(uploadUrl);
        // 判断是http请求还是https请求
        if (url.getProtocol().toLowerCase().equals("https")) {
            trustAllHosts();
            http = (HttpsURLConnection) url.openConnection();
            ((HttpsURLConnection) http).setHostnameVerifier(DO_NOT_VERIFY);// 对所有主机都进行确认
        } else {
            http = (HttpURLConnection) url.openConnection();
        }

        String returnStr = null;
        String BOUNDARY = UUID.randomUUID().toString();
        String PREFIX = "--";
        String LINEND = "\r\n";
        http.setReadTimeout(READ_TIMEOUT);
        http.setConnectTimeout(CONNENT_TIMEOUT);
        http.setRequestProperty("User-Agent", "App-Android/0.1 "+ getUserAgent());
        //H5+项目 添加头Authorization验证
//        if(!TextUtils.isEmpty(FrmCommonAction.getUserToken())){
//            http.addRequestProperty("Authorization", Authorization_Type + FrmCommonAction.getUserToken());
//        }
        http.setDoInput(true);
        http.setDoOutput(true);
        http.setUseCaches(false);
        http.setRequestMethod("POST");
        http.setRequestProperty("Connection", "keep-alive");
        http.setRequestProperty("Charsert", "UTF-8");
        http.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + BOUNDARY);
        StringBuilder formStr = new StringBuilder();
        Iterator end_data = params.entrySet().iterator();

        while(end_data.hasNext()) {
            Map.Entry outStream = (Map.Entry)end_data.next();
            formStr.append(PREFIX);
            formStr.append(BOUNDARY);
            formStr.append(LINEND);
            formStr.append("Content-Disposition: form-data; name=\"" + (String)outStream.getKey() + "\"" + LINEND);
            formStr.append("Content-Type: text/plain; charset=UTF-8" + LINEND);
            formStr.append("Content-Transfer-Encoding: 8bit" + LINEND);
            formStr.append(LINEND);
            formStr.append((String)outStream.getValue());
            formStr.append(LINEND);
        }

        DataOutputStream outStream1 = new DataOutputStream(http.getOutputStream());
        outStream1.write(formStr.toString().getBytes());

//        Iterator in = fList.iterator();
//        while(in.hasNext()) {
//            File end_data1 = (File)in.next();
//            StringBuilder out = new StringBuilder();
//            out.append(PREFIX);
//            out.append(BOUNDARY);
//            out.append(LINEND);
//            out.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + end_data1.getName() + "\"" + LINEND);
//            out.append("Content-Type: application/octet-stream; charset=UTF-8" + LINEND);
//            out.append(LINEND);
//            outStream1.write(out.toString().getBytes());
//            FileInputStream buffer = new FileInputStream(end_data1);
//            byte[] n = new byte[1024];
//            boolean b = false;
//
//            int b1;
//            while((b1 = buffer.read(n)) != -1) {
//                outStream1.write(n, 0, b1);
//            }
//
//            buffer.close();
//            outStream1.write(LINEND.getBytes());
//        }

        //暂仅支持单一附件上传
        StringBuilder out = new StringBuilder();
        out.append(PREFIX);
        out.append(BOUNDARY);
        out.append(LINEND);
        out.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"" + LINEND);
        out.append("Content-Type: " + mimeType + LINEND);
        out.append(LINEND);
        outStream1.write(out.toString().getBytes());
        FileInputStream buffer = new FileInputStream(file);
        byte[] n = new byte[1024];
        int b1;
        while((b1 = buffer.read(n)) != -1) {
            outStream1.write(n, 0, b1);
        }
        buffer.close();
        outStream1.write(LINEND.getBytes());

        byte[] end_data2 = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
        outStream1.write(end_data2);
        outStream1.flush();

        // 判断返回状态
        responseCode = http.getResponseCode();
        if(responseCode == 200) {
            InputStream in1 = http.getInputStream();
            ByteArrayOutputStream out1 = new ByteArrayOutputStream();
            byte[] buffer1 = new byte[256];

            int n1;
            while((n1 = in1.read(buffer1)) >= 0) {
                out1.write(buffer1, 0, n1);
            }

            byte[] b2 = out1.toByteArray();
            in1.close();
            returnStr = new String(b2);
        }
        outStream1.close();
        http.disconnect();

        returnStr = convert(returnStr);

        //拼接返回Json数据
        JsonObject returnJsonObject = null;
        try {
            returnJsonObject = new JsonObject();
            returnJsonObject.addProperty("code", responseCode);
            if (returnStr != null) {
                JsonObject resultJsonObject = new JsonParser().parse(dealWithBs(returnStr)).getAsJsonObject();
                returnJsonObject.add("body", resultJsonObject);
            }
            Log.i("wyf", "[附件上传返回：]" + returnJsonObject.toString());
            LogUtil.writeLogThread("[附件上传返回：]", returnJsonObject.toString());
        }catch (Exception e) {
            e.printStackTrace();
        }

        return returnJsonObject;
    }

    /**
     * 处理异常返回值，防止json解析出错
     * @param bs
     * @return
     */
    private static String dealWithBs(String bs){
        if (bs.startsWith("[") && bs.endsWith("]")) {
            bs = "{\"data\":" + bs + "}";
        }
        return bs;
    }

    /**
     * 中文转化为ASCII编码，防止请求参数中出现编码错误
     * @param str
     * @return
     */
    @Deprecated
    private static String cnToUnicode(String str){
        String result="";
        for (int i = 0; i < str.length(); i++){
            int chr1 = (char) str.charAt(i);
            if((chr1>='\u0391' && chr1<='\uffe5') || (chr1>='\u0080' && chr1<='\u00FF')){
                //汉字范围 \u4e00-\u9fa5 (中文)
                result+="\\u" + ("d7".equals(Integer.toHexString(chr1))?"00d7":Integer.toHexString(chr1));
            }else{
                result+=str.charAt(i);
            }
        }
        return result;
    }

    /**
     * 中文转Unicode
     * @param str
     * @return
     */
    public static String cn2Unicode(String str) {
        str = (str == null ? "" : str);
        String tmp;
        StringBuffer sb = new StringBuffer(1000);
        char c;
        int i, j;
        sb.setLength(0);
        for (i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if((c>='\u0391' && c<='\uffe5') || (c>='\u0080' && c<='\u00FF')) {
                //汉字范围 \u0391-\uffe5 (中文,包括标点符号)
                sb.append("\\u");
                j = (c >>> 8); //取出高8位
                tmp = Integer.toHexString(j);
                if (tmp.length() == 1) {
                    sb.append("0");
                }
                sb.append(tmp);
                j = (c & 0xFF); //取出低8位
                tmp = Integer.toHexString(j);
                if (tmp.length() == 1) {
                    sb.append("0");
                }
                sb.append(tmp);
            }else{
                sb.append(c);
            }
        }
        return (new String(sb));
    }

    /**
     * ASCII编码转化为中文
     * @param utfString
     * @return
     */
    private static String convert(String utfString){
        if(utfString != null) {
            StringBuilder sb = new StringBuilder();
            int i = -1;
            int pos = 0;
            while ((i = utfString.indexOf("\\u", pos)) != -1) {
                sb.append(utfString.substring(pos, i));
                if (i + 5 < utfString.length()) {
                    pos = i + 6;
                    sb.append((char) Integer.parseInt(utfString.substring(i + 2, i + 6), 16));
                }
            }
            sb.append(utfString.substring(pos));
            return sb.toString();
        }
        return null;
    }

    /**
     * 获取http头部userAgent信息
     * @return
     */
    private static String getUserAgent() {
        String userAgent = System.getProperty("http.agent");
        StringBuffer sb = new StringBuffer();
        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

}
