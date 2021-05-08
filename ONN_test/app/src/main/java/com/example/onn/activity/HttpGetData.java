package com.example.onn.activity;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpGetData {
    private void request() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String strUrl = ""; // 你请求的 http 链接
                    URL url = new URL(strUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET"); // 设置请求方式为 GET
                    connection.connect(); // 连接
                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) { // 请求成功
                        InputStream inputStream = connection.getInputStream(); // 得到响应流
                        JSONObject json = streamToJson(inputStream); // 从响应流中提取 JSON
                        Log.i("json", json.toString()); // 打印返回的 JSON 观察处理
                    }
                    connection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public JSONObject streamToJson(InputStream inputStream) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        String temp = "";
        StringBuilder stringBuilder = new StringBuilder();
        while ((temp = bufferedReader.readLine()) != null) {
            stringBuilder.append(temp);
        }
        JSONObject json = JSON.parseObject(stringBuilder.toString().trim());
        return json;
    }
}