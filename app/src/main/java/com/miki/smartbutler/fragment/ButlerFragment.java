package com.miki.smartbutler.fragment;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.miki.smartbutler.R;
import com.miki.smartbutler.adapter.ChatListAdapter;
import com.miki.smartbutler.entity.ChatListData;
import com.miki.smartbutler.utils.L;
import com.miki.smartbutler.utils.ShareUtil;
import com.miki.smartbutler.utils.StaticClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 包名:      com.miki.smartbutler.fragment
 * 文件名:     ButlerFragment.java
 * 创建者:     王子豪
 * 创建时间:   2018/4/18 23:10
 * 描述:      ButlerFragment
 */
public class ButlerFragment extends Fragment implements View.OnClickListener {

    private ListView mChatListView;
    //输入框
    private EditText et_text;
    //发送按钮
    private Button btn_send;
    //数据源
    private List<ChatListData> mList = new ArrayList<>();
    private ChatListAdapter adapter;
    private SpeechSynthesizer mTts;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_butler, null);
        findView(view);
        return view;
    }

    private void findView(View view) {

        mTts = SpeechSynthesizer.createSynthesizer(getActivity(), null);
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");  //设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "50");   //设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "80");   //设置音量
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);  //设置云端
        //mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./sdcard/iflytek.pcm");  //保存合成音频


        mChatListView = (ListView) view.findViewById(R.id.mChatListView);

        et_text = (EditText) view.findViewById(R.id.et_text);
        btn_send = (Button) view.findViewById(R.id.btn_send);
        btn_send.setOnClickListener(this);

        adapter = new ChatListAdapter(getActivity(), mList);
        mChatListView.setAdapter(adapter);

        addLeftItem("你好,我是小优");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                /**
                 *  1.获取输入框的内容
                 *  2.判断是否为空
                 *  3.判断长度不能大于30
                 *  4.清空输入框
                 *  5.添加输入的内容到right item
                 *  6.发送给机器人请求返回内容
                 *  7.拿到机器人的返回值添加在left item
                 */
                //1.获取输入框的内容
                String text = et_text.getText().toString();
                //2.判断是否为空
                if (!TextUtils.isEmpty(text)) {
                    //3.判断长度不能大于30
                    if (text.length() > 30) {
                        Toast.makeText(getActivity(), "输入长度超出限制", Toast.LENGTH_SHORT).show();
                    } else {
                        //4.清空输入框
                        et_text.setText("");
                        //5.添加输入的内容到right item
                        addRightItem(text);
                        //6.发送给机器人请求返回内容
                        String url = "http://op.juhe.cn/robot/index?info=" + text + "&key=" + StaticClass.CHAT_LIST_KEY;
                        RxVolley.get(url, new HttpCallback() {
                            @Override
                            public void onSuccess(String t) {
                                //Toast.makeText(getActivity(), t, Toast.LENGTH_SHORT).show();
                                L.d(t);
                                parsingJson(t);
                            }
                        });
                    }
                } else {
                    Toast.makeText(getActivity(), "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //解析Json
    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            //拿到返回值
            String text = jsonResult.getString("text");
            //7.拿到机器人的返回值添加在left item
            addLeftItem(text);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addLeftItem(String text) {
        //说话
        Boolean isSpeak = ShareUtil.getBoolean(getActivity(), "isSpeak", false);
        if (isSpeak) {
            startSpeak(text);
        }

        ChatListData data = new ChatListData();
        data.setType(ChatListAdapter.VALUE_LEFT_TEXT);
        data.setText(text);
        mList.add(data);
        //刷新adapter
        adapter.notifyDataSetChanged();
        //滚动到底部
        mChatListView.setSelection(mChatListView.getBottom());
    }

    private void addRightItem(String text) {
        ChatListData data = new ChatListData();
        data.setType(ChatListAdapter.VALUE_RIGHT_TEXT);
        data.setText(text);
        mList.add(data);
        //刷新adapter
        adapter.notifyDataSetChanged();
        //滚动到底部
        mChatListView.setSelection(mChatListView.getBottom());
    }

    //开始说话
    private void startSpeak(String text) {
        mTts.startSpeaking(text, mListener);
    }

    //合成监听器
    private SynthesizerListener mListener = new SynthesizerListener() {
        @Override
        public void onSpeakBegin() {

        }

        @Override
        public void onBufferProgress(int i, int i1, int i2, String s) {

        }

        @Override
        public void onSpeakPaused() {

        }

        @Override
        public void onSpeakResumed() {

        }

        @Override
        public void onSpeakProgress(int i, int i1, int i2) {

        }

        @Override
        public void onCompleted(SpeechError speechError) {

        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    };

}
