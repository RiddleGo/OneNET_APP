package com.example.onn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.onn.R;

public class Choose extends AppCompatActivity implements View.OnClickListener {

    private ImageView mCode;
    private ImageView mCornliner;
    /**
     * 更多
     */
    private TextView mMoreWait;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        initView();
    }

    private void initView() {
        mCode = (ImageView) findViewById(R.id.code);
        mCode.setOnClickListener(this);
        mCornliner = (ImageView) findViewById(R.id.cornliner);
        mCornliner.setOnClickListener(this);
        mMoreWait = (TextView) findViewById(R.id.more_wait);
        mMoreWait.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.code:
                startActivity(new Intent(getApplicationContext(),Code.class));
                break;
            case R.id.cornliner:
                startActivity(new Intent(getApplicationContext(),Main2Activity.class));
                break;
            case R.id.more_wait:
                startActivity(new Intent(getApplicationContext(),More.class));
                break;
        }
    }
}
