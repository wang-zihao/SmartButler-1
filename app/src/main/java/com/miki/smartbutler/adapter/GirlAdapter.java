package com.miki.smartbutler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.miki.smartbutler.R;
import com.miki.smartbutler.entity.GirlData;
import com.miki.smartbutler.utils.PicassoUtils;

import java.util.List;

/**
 * 包名:      com.miki.smartbutler.adapter
 * 文件名:     GirlAdapter.java
 * 创建者:     王子豪
 * 创建时间:   2018/5/18 18:19
 * 描述:      妹子Adapter
 */

public class GirlAdapter extends BaseAdapter {

    private Context context;
    private List<GirlData> mList;
    private LayoutInflater inflater;
    private GirlData data;
    private WindowManager wm;
    //屏幕宽
    private int width;

    public GirlAdapter(Context context, List<GirlData> mList) {
        this.context = context;
        this.mList = mList;
        inflater = LayoutInflater.from(context);
        wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
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
            convertView = inflater.inflate(R.layout.girl_item, null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        data = mList.get(position);
        //解析图片
        String url = data.getImgUrl();
        PicassoUtils.loadImageViewSize(context, url, width / 2, 500, viewHolder.imageView);
        return convertView;
    }

    class ViewHolder {
        private ImageView imageView;
    }
}
