package com.miki.smartbutler.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.miki.smartbutler.R;
import com.miki.smartbutler.adapter.WeChatAdapter;
import com.miki.smartbutler.entity.WeChatData;
import com.miki.smartbutler.ui.WebViewActivity;
import com.miki.smartbutler.utils.L;
import com.miki.smartbutler.utils.StaticClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 包名:      com.miki.smartbutler.fragment
 * 文件名:     WeChatFragment.java
 * 创建者:     王子豪
 * 创建时间:   2018/4/18 23:19
 * 描述:      TODO
 */

public class WeChatFragment extends Fragment {

    private ListView mListView;
    private List<WeChatData> mList = new ArrayList<>();

    //标题
    private List<String> mListTitle = new ArrayList<>();
    //地址
    private List<String> mListUrl = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wechat, null);
        findView(view);
        return view;
    }

    //初始化view
    private void findView(View view) {
        mListView = (ListView) view.findViewById(R.id.mListView);

        //请求接口
        String url = "http://v.juhe.cn/weixin/query?key=" + StaticClass.WECHAT_KEY;
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                //Toast.makeText(getActivity(), t, Toast.LENGTH_SHORT).show();
                L.d(t);
                parsingJson(t);
            }
        });

        WeChatAdapter adapter = new WeChatAdapter(getActivity(), mList);
        mListView.setAdapter(adapter);

        //点击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                L.d("position" + position);
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                //intent两种方法传值   BUNDLE  INTENT
                intent.putExtra("title", mListTitle.get(position));
                intent.putExtra("url", mListUrl.get(position));
                startActivity(intent);
            }
        });
    }

    //解析Json
    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject result = jsonObject.getJSONObject("result");
            JSONArray jsonArray = result.getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = (JSONObject) jsonArray.get(i);
                WeChatData data = new WeChatData();
                String title = json.getString("title");
                String url = json.getString("url");
                data.setImgUrl(json.getString("firstImg"));
                data.setTitle(title);
                data.setSource(json.getString("source"));
                data.setNewsUrl(url);
                mList.add(data);

                mListTitle.add(title);
                mListUrl.add(url);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
