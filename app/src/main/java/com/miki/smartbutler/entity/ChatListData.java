package com.miki.smartbutler.entity;

/**
 * 包名:      com.miki.smartbutler.entity
 * 文件名:     ChatListData.java
 * 创建者:     王子豪
 * 创建时间:   2018/5/15 0:04
 * 描述:      对话列表的实体
 */

public class ChatListData {
    //类型
    private int type;
    //消息
    private String text;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
