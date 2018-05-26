package com.miki.smartbutler.entity;

import cn.bmob.v3.BmobUser;

/**
 * 包名:      com.miki.smartbutler.entity
 * 文件名:     MyUser.java
 * 创建者:     王子豪
 * 创建时间:   2018/4/28 21:04
 * 描述:      用户属性
 */

public class MyUser extends BmobUser{

    private int age;
    private boolean sex;
    private String desc;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
