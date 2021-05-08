package com.example.onn.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONObject;
import com.example.onn.R;

import org.json.JSONException;

import java.io.IOException;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private EditText mLoginUser;
    private EditText mLoginPwd;
    /**
     * 登录
     */
    public String user;
    public String pwd;
    private Button mLoginBtu;
    private CheckBox rem_pw, auto_login;
    private Button btn_login;
    public String data[] = new String[2];
    private SharedPreferences sp;

    JsonUtil jsonUtil = new JsonUtil();
    /**
     * 记住密码
     */
    private CheckBox mCbMima;
    /**
     * 自动登录
     */
    private CheckBox mCbAuto;
    /**
     * 登录
     */
    private Button mBtu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logon_re);
        try {
            initView();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @SuppressLint("WrongViewCast")
    private void initView() throws IOException, JSONException {
        mLoginUser = (EditText) findViewById(R.id.login_user);
        mLoginPwd = (EditText) findViewById(R.id.login_pwd);
        mCbMima = (CheckBox) findViewById(R.id.cb_mima);
        mCbMima.setOnClickListener(this);
        mCbAuto = (CheckBox) findViewById(R.id.cb_auto);
        mCbAuto.setOnClickListener(this);
        mBtu = (Button) findViewById(R.id.login_btu1);
        mBtu.setOnClickListener(this);
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        //判断记住密码多选框的状态
        if (sp.getBoolean("ISCHECK", false)) {
            //设置默认是记录密码状态
            mCbMima.setChecked(true);
            mLoginUser.setText(sp.getString("USER_NAME", ""));
            mLoginPwd.setText(sp.getString("PASSWORD", ""));
            //判断自动登陆多选框状态
            if (sp.getBoolean("AUTO_ISCHECK", false)) {
                //设置默认是自动登录状态
                mCbAuto.setChecked(true);
                //跳转界面
                startActivity(new Intent(getApplicationContext(), Choose.class));

            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.login_btu1:
                String user = mLoginUser.getText().toString();
                String pwd = mLoginPwd.getText().toString();
                JsonUtil jsonUtil = new JsonUtil();
                JSONObject Str = jsonUtil.JsonUtil(getApplicationContext());

                Toast.makeText(this, Str.getString("user") + "  " + Str.getString("pwd"), Toast.LENGTH_SHORT).show();
                if (user.equals("") || pwd.equals("")) {
                    Toast.makeText(this, "不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                //if(user.equals(data[0])&&pwd.equals(data[1])){
                if (user.equals(Str.getString("user")) && pwd.equals(Str.getString("pwd"))) {

                    Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();

                    //登录成功和记住密码框为选中状态才保存用户信息
                    if(mCbMima.isChecked())
                    {
                        //记住用户名、密码、
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("USER_NAME", user);
                        editor.putString("PASSWORD",pwd);
                        editor.commit();
                    }
                    //登录成功和记住密码框为选中状态才保存用户信息
                    //登录成功
                    startActivity(new Intent(getApplicationContext(), Choose.class));
                } else {
                    Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.cb_mima:
                if (mCbMima.isChecked()) {

                    System.out.println("记住密码已选中");
                    sp.edit().putBoolean("ISCHECK", true).commit();

                }else {

                    System.out.println("记住密码没有选中");
                    sp.edit().putBoolean("ISCHECK", false).commit();

                }
                break;
            case R.id.cb_auto:
                if (mCbAuto.isChecked()) {
                    System.out.println("自动登录已选中");
                    sp.edit().putBoolean("AUTO_ISCHECK", true).commit();

                } else {
                    System.out.println("自动登录没有选中");
                    sp.edit().putBoolean("AUTO_ISCHECK", false).commit();
                }
                break;
        }
    }
}
