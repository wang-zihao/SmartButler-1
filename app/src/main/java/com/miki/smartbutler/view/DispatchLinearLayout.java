package com.miki.smartbutler.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.LinearLayout;

/**
 * 包名:      com.miki.smartbutler.view
 * 文件名:     DispatchLinearLayout.java
 * 创建者:     王子豪
 * 创建时间:   2018/5/21 18:04
 * 描述:      事件分发
 */

public class DispatchLinearLayout extends LinearLayout {

    private DispatchKeyEventListener listener;

    public DispatchKeyEventListener getListener() {
        return listener;
    }

    public void setListener(DispatchKeyEventListener listener) {
        this.listener = listener;
    }

    public DispatchLinearLayout(Context context) {
        super(context);
    }

    public DispatchLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DispatchLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //接口
    public static interface DispatchKeyEventListener {
        boolean dispatchKeyEvent(KeyEvent event);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        //如果不为空，获取事件
        if (listener != null) {
            return listener.dispatchKeyEvent(event);
        }
        return super.dispatchKeyEvent(event);
    }
}
