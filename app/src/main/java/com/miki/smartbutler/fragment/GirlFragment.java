package com.miki.smartbutler.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.miki.smartbutler.R;
import com.miki.smartbutler.adapter.GirlAdapter;
import com.miki.smartbutler.entity.GirlData;
import com.miki.smartbutler.utils.L;
import com.miki.smartbutler.utils.PicassoUtils;
import com.miki.smartbutler.utils.StaticClass;
import com.miki.smartbutler.view.CustomDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * 包名:      com.miki.smartbutler.fragment
 * 文件名:     GirlFragment.java
 * 创建者:     王子豪
 * 创建时间:   2018/4/18 23:16
 * 描述:      GirlFragment
 */

public class GirlFragment extends Fragment {

    private GridView mGridView;
    private List<GirlData> mList = new ArrayList<>();
    private GirlAdapter adapter;

    //提示框
    private CustomDialog dialog;
    //预览图片
    private ImageView iv_img;
    //图片地址的数据
    private List<String> mListUrl = new ArrayList<>();
    //PhotoView
    private PhotoViewAttacher mAttacher;


    /**
     * 1.监听点击事件
     * 2.提示框
     * 3.加载图片
     * 4.PhotoView
     */

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_girl, null);

        findView(view);
        return view;
    }

    private void findView(View view) {
        mGridView = (GridView) view.findViewById(R.id.mGridView);


        //初始化提示框
        dialog = new CustomDialog(getActivity(), LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,
                R.layout.dialog_girl, R.style.Theme_dialog, Gravity.CENTER, R.style.pop_anim_style);
        iv_img = (ImageView) dialog.findViewById(R.id.iv_img);


        //解析
        RxVolley.get(StaticClass.GIRL_URL, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                L.d(t);
                parsingJson(t);
            }
        });


        //监听点击事件
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //解析图片
                PicassoUtils.loadImageView(getActivity(), mListUrl.get(position), iv_img);
                //缩放
                mAttacher = new PhotoViewAttacher(iv_img);
                mAttacher.update();
                dialog.show();
            }
        });


    }

    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONArray results = jsonObject.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject json = (JSONObject) results.get(i);
                String url = json.getString("url");
                GirlData data = new GirlData();
                data.setImgUrl(url);
                mList.add(data);
                mListUrl.add(url);
            }
            adapter = new GirlAdapter(getActivity(), mList);
            mGridView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
