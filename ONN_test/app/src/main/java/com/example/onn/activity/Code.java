package com.example.onn.activity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.onn.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


public class Code extends AppCompatActivity {

    private TextView mNode01Val;
    private TextView mNode01Time;
    private TextView mNode02Val;
    private TextView mNode02Time;
    private TextView mNode03Val;
    private TextView mNode03Time;
    private TextView mNode04Val;
    private TextView mNode04Time;
    private TextView mNode05Val;
    private TextView mNode05Time;
    private TextView mNode06Val;
    private TextView mNode06Time;
    private SwipeRefreshLayout mSrl;
    /**
     * 更多节点
     */
    private TextView mMoreNode;

    private final int Message_TEST1 = 0x01;
    //private Gson gson;
    //"at": "2020-08-30 18:03:28.100",
    // "value": 22
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
        initView();
        setView();
    }

    protected void getData(){
        Token token = new Token();
        JsonUtil jsonUtil = new JsonUtil();
        com.alibaba.fastjson.JSONObject Str = jsonUtil.JsonUtil(getApplicationContext());
        String url1 =Str.getString("url1");
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(new JsonObjectRequest(Request.Method.GET, url1, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.d("Toast", jsonObject.toString());
                try {
                    JSONObject data = jsonObject.getJSONObject("data");
                    Log.d("Toast",data.getString("datastreams"));

                    String string1 = data.getString("datastreams");

                    Log.d("Toast",string1);

                    JSONArray dataArr = data.getJSONArray("datastreams");
                    Log.d("Toast",dataArr.getString(0));

                    JSONObject ValueTime = (JSONObject) dataArr.get(0);
                    Log.d("Toast",ValueTime.getString("datapoints"));

                    JSONArray dataArrTime = ValueTime.getJSONArray("datapoints");
                    Log.d("Toast",dataArrTime.getString(0));

                    JSONObject datapoints = (JSONObject) dataArrTime.get(0);
                    Log.d("Toast",datapoints.getString("at"));
                    Log.d("Toast",datapoints.getString("value"));

                    mNode01Val.setText(datapoints.getString("value"));
                    mNode01Time.setText(datapoints.getString("at"));

                    mNode02Val.setText(datapoints.getString("value"));
                    mNode02Time.setText(datapoints.getString("at"));

                    mNode03Val.setText(datapoints.getString("value"));
                    mNode03Time.setText(datapoints.getString("at"));

                    mNode04Val.setText(datapoints.getString("value"));
                    mNode04Time.setText(datapoints.getString("at"));

                    mNode05Val.setText(datapoints.getString("value"));
                    mNode05Time.setText(datapoints.getString("at"));

                    mNode06Val.setText(datapoints.getString("value"));
                    mNode06Time.setText(datapoints.getString("at"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }){

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public Map<String, String> getHeaders()
                    throws AuthFailureError {
                Map<String, String> mHeader = new HashMap<String, String>();
                try {
                    mHeader.put("Authorization", token.calToken());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                }
                mHeader.put("Content-Type","application/json");
                return mHeader;
            }
        });
    }

    private void setView() {

        getData();
        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉时触发
                getData();
                //获取数据  异步的
                new Thread(){
                    @Override
                    public void run() {

                        mSrl.setRefreshing(false);
                    }
                }.start();
            }
        });
}

    private void initView() {
        mNode01Val = (TextView) findViewById(R.id.node01Val);
        mNode01Time = (TextView) findViewById(R.id.node01Time);
        mNode02Val = (TextView) findViewById(R.id.node02Val);
        mNode02Time = (TextView) findViewById(R.id.node02Time);
        mNode03Val = (TextView) findViewById(R.id.node03Val);
        mNode03Time = (TextView) findViewById(R.id.node03Time);
        mNode04Val = (TextView) findViewById(R.id.node04Val);
        mNode04Time = (TextView) findViewById(R.id.node04Time);
        mNode05Val = (TextView) findViewById(R.id.node05Val);
        mNode05Time = (TextView) findViewById(R.id.node05Time);
        mNode06Val = (TextView) findViewById(R.id.node06Val);
        mNode06Time = (TextView) findViewById(R.id.node06Time);
        mMoreNode = (TextView) findViewById(R.id.more_node);
        mSrl = (SwipeRefreshLayout) findViewById(R.id.srl);
    }
}
