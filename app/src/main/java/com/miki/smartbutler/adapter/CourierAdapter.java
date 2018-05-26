package com.miki.smartbutler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.miki.smartbutler.R;
import com.miki.smartbutler.entity.CourierData;

import java.util.List;

/**
 * 包名:      com.miki.smartbutler.adapter
 * 文件名:     CourierAdapter.java
 * 创建者:     王子豪
 * 创建时间:   2018/5/8 18:09
 * 描述:      快递adapter
 */

public class CourierAdapter extends BaseAdapter {

    private Context context;
    private List<CourierData> mList;
    private LayoutInflater inflater;
    private CourierData data;

    public CourierAdapter(Context context, List<CourierData> mList) {
        this.context = context;
        this.mList = mList;
        inflater = LayoutInflater.from(context);
        //或者用下面这个
        //inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        //第一次加载
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.layout_courier_item, null);
            viewHolder.tv_remark = (TextView) convertView.findViewById(R.id.tv_remark);
            viewHolder.tv_zone = (TextView) convertView.findViewById(R.id.tv_zone);
            viewHolder.tv_datetime = (TextView) convertView.findViewById(R.id.tv_datetime);
            //设置缓存
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        data = mList.get(position);
        viewHolder.tv_remark.setText(data.getRemark());
        viewHolder.tv_zone.setText(data.getZone());
        viewHolder.tv_datetime.setText(data.getDatetime());
        return convertView;
    }

    class ViewHolder {
        TextView tv_remark;
        TextView tv_zone;
        TextView tv_datetime;
    }
}
