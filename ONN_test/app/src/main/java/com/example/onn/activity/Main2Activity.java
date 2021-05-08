package com.example.onn.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.onn.R;

public class Main2Activity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
        setView();
    }


    public void initWebView(){

        mWebView.setWebViewClient(new WebViewClient());//设置可操作
        //常用方法
        mWebView.goBack();//返回上一级
        mWebView.canGoBack();//返回是否可以返回上一级
        mWebView.goForward();//前进
        mWebView.canGoForward();//判断是否可以前进
        //初始化
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);  //支持js
        settings.setDomStorageEnabled(true);
        settings.setUseWideViewPort(true);  //将图片调整到适合webview的大小
        settings.setSupportZoom(true);  //支持缩放
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重      新布局
//        settings.supportMultipleWindows();  //多窗口
//        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);  //关闭webview中缓存
//        settings.setAllowFileAccess(true);  //设置可以访问文件
//        settings.setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点
       settings.setBuiltInZoomControls(true); //设置支持缩放
        settings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
       settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        settings.setLoadsImagesAutomatically(true);  //支持自动加载图片
//        settings.setSavePassword(true);//关闭WebView的自动保存密码功能

    }
    private void setView() {
        initWebView();
        JsonUtil jsonUtil = new JsonUtil();
        com.alibaba.fastjson.JSONObject Str = jsonUtil.JsonUtil(getApplicationContext());
        String url =Str.getString("url");
        mWebView.loadUrl(url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK&&mWebView.canGoBack()){
            mWebView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode,event);

    }

    private void initView() {
        mWebView = (WebView) findViewById(R.id.webView);
    }
}
