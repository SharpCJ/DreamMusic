package com.example.sharpcj.dreammusic.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.sharpcj.dreammusic.MyApplication;

/**
 * Created by joy on 2016/6/27.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.activityList.add(this);
        setContentView(setViewId());
        findViews();
        init();
        initEvents();
        loadData();
    }

    protected abstract int setViewId();

    protected abstract void findViews();

    protected abstract void init();

    protected abstract void initEvents();

    protected abstract void loadData();

}
