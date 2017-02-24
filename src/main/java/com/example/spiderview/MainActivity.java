package com.example.spiderview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SpiderView view = (SpiderView) findViewById(R.id.spider);
        view.setUserData(new float[]{0.5f,0.9f,0.8f,0.9f,0.1f,0.9f});
    }
}
