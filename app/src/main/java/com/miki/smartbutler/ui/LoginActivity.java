package com.miki.smartbutler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.miki.smartbutler.MainActivity;
import com.miki.smartbutler.R;
import com.miki.smartbutler.entity.MyUser;
import com.miki.smartbutler.utils.ShareUtil;
import com.miki.smartbutler.view.CustomDialog;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 包名:      com.miki.smartbutler.ui
 * 文件名:     LoginActivity.java
 * 创建者:     王子豪
 * 创建时间:   2018/4/27 21:35
 * 描述:      登录
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //注册按钮
    private Button btn_registered;
    //登录功能
    private EditText et_name;
    private EditText et_password;
    private Button btnLogin;
    //记住密码
    private CheckBox keep_password;
    //忘记密码
    private TextView tv_forget;
    private CustomDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        btn_registered = (Button) findViewById(R.id.btn_registered);
        btn_registered.setOnClickListener(this);
        et_name = (EditText) findViewById(R.id.et_name);
        et_password = (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        tv_forget = (TextView) findViewById(R.id.tv_forget);
        tv_forget.setOnClickListener(this);

        dialog = new CustomDialog(this, 100, 100, R.layout.dialog_loding, R.style.Theme_dialog, Gravity.CENTER, R.style.pop_anim_style);
        //屏幕外点击无效
        dialog.setCancelable(false);

        keep_password = (CheckBox) findViewById(R.id.keep_password);
        //设置选中状态
        boolean isCheck = ShareUtil.getBoolean(this, "keeppass", false);
        keep_password.setChecked(isCheck);
        //设置密码
        if (isCheck) {
            et_name.setText(ShareUtil.getString(this, "name", ""));
            et_password.setText(ShareUtil.getString(this, "password", ""));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_forget:
                startActivity(new Intent(this, ForgetPasswordActivity.class));
                break;
            case R.id.btn_registered:
                startActivity(new Intent(this, RegisteredActivity.class));
                break;
            case R.id.btnLogin:
                // 1.获取输入框的值
                String name = et_name.getText().toString();
                String password = et_password.getText().toString();
                // 2.判断是否为空
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)) {
                    // 3.登录
                    dialog.show();
                    final MyUser user = new MyUser();
                    user.setUsername(name);
                    user.setPassword(password);
                    user.login(new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            if (e == null) {
                                dialog.dismiss();
                                // 4.判断邮箱是否验证
                                if (user.getEmailVerified()) {
                                    //跳转
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                } else {
                                    Toast.makeText(LoginActivity.this, "请前往邮箱验证", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "登录失败: " + e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //假设我现在输入用户名和密码，但是我不点击登录，而是直接退出了
    @Override
    protected void onDestroy() {
        super.onDestroy();

        //保存状态
        ShareUtil.putBoolean(this, "keeppass", keep_password.isChecked());

        //是否记住密码
        if (keep_password.isChecked()) {
            //保存用户名和密码
            ShareUtil.putString(this, "name", et_name.getText().toString());
            ShareUtil.putString(this, "password", et_password.getText().toString());
        } else {
            ShareUtil.deleteShare(this, "name");
            ShareUtil.deleteShare(this, "password");
        }
    }
}
