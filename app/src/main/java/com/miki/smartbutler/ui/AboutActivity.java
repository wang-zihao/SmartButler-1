package com.miki.smartbutler.ui;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.miki.smartbutler.R;
import com.miki.smartbutler.utils.UtilTools;

import java.util.ArrayList;
import java.util.List;

/**
 * 包名:      com.miki.smartbutler.ui
 * 文件名:     AboutActivity.java
 * 创建者:     王子豪
 * 创建时间:   2018/5/24 21:38
 * 描述:      关于软件
 */

public class AboutActivity extends BaseActivity {

    private ListView mListView;
    private List<String> mList = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        //去阴影
        getSupportActionBar().setElevation(0);
        initView();
    }

    //初始化view
    private void initView() {
        mListView = (ListView) findViewById(R.id.mListView);

        mList.add("应用名：" + getString(R.string.app_name));
        mList.add("版本号：" + UtilTools.getVersion(this));
        mList.add("官网：www.wangzh.com");

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mList);
        mListView.setAdapter(adapter);
    }

}
