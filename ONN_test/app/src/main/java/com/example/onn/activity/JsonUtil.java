package com.example.onn.activity;

import android.content.Context;
import android.content.res.AssetManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class JsonUtil {


        public static JSONObject JsonUtil(Context context) {
            //将json数据变成字符串
            StringBuilder stringBuilder = new StringBuilder();
            try {
                //获取assets资源管理器
                AssetManager assetManager = context.getAssets();
                //通过管理器打开文件并读取
                BufferedReader bf = new BufferedReader(new InputStreamReader(
                        assetManager.open("app_config.json")));
                String line;
                while ((line = bf.readLine()) != null) {
                    stringBuilder.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            //将读出的字符串转换成JSONobject
            JSONObject jsonObject = JSON.parseObject(stringBuilder.toString());
            //获取JSONObject中的数组数据
            return jsonObject;
        }
}
