package com.miki.smartbutler.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.miki.smartbutler.R;
import com.miki.smartbutler.entity.WeChatData;
import com.miki.smartbutler.utils.L;
import com.miki.smartbutler.utils.PicassoUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 包名:      com.miki.smartbutler.adapter
 * 文件名:     WeChatAdapter.java
 * 创建者:     王子豪
 * 创建时间:   2018/5/15 23:30
 * 描述:      微信精选adapter
 */

public class WeChatAdapter extends BaseAdapter {

    private Context context;
    private List<WeChatData> mList;
    private LayoutInflater inflater;
    private WeChatData data;

    public WeChatAdapter(Context context, List<WeChatData> mList) {
        this.context = context;
        this.mList = mList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.wechat_item, null);
            viewHolder.iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
            viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.tv_source = (TextView) convertView.findViewById(R.id.tv_source);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        data = mList.get(position);
        viewHolder.tv_title.setText(data.getTitle());
        Log.d("wangzh", data.getTitle());
        viewHolder.tv_source.setText(data.getSource());
        //加载图片
        if (!TextUtils.isEmpty(data.getImgUrl())) {
            PicassoUtils.loadImageViewSize(context, data.getImgUrl(), 400, 200, viewHolder.iv_img);
        }
        //Picasso.with(context).load(data.getImgUrl()).into(viewHolder.iv_img);
        return convertView;
    }

    class ViewHolder {
        private ImageView iv_img;
        private TextView tv_title;
        private TextView tv_source;
    }
}
