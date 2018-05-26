package com.miki.smartbutler.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * 包名:      com.miki.smartbutler.utils
 * 文件名:     UtilTools.java
 * 创建者:     王子豪
 * 创建时间:   2018/4/11 23:38
 * 描述:      工具统一类
 */

public class UtilTools {
    //设置字体
    public static void setFont(Context context, TextView textView) {

        Typeface fontType = Typeface.createFromAsset(context.getAssets(), "fonts/FONT.TTF");
        textView.setTypeface(fontType);
    }

    //保存图片到ShareUtil
    public static void putImageToShare(Context context, ImageView imageView) {
        //保存
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        //第一步：将Bitmap压缩成字节数组输出流
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        //第二步：利用base64将我们的字节数组输入流转化为String
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String imgString = new String(Base64.encode(byteArray, Base64.DEFAULT));
        //第三步，保存
        ShareUtil.putString(context, "image_title", imgString);
    }

    public static void getImageFromShare(Context context, ImageView imageView) {
        // 1.拿到String
        String imgString = ShareUtil.getString(context, "image_title", "");
        if (imgString != null) {
            // 2.利用Base64将我们的字节数组转化成String
            byte[] byteArray = Base64.decode(imgString, Base64.DEFAULT);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArray);
            // 3.生成bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(byteArrayInputStream);
            imageView.setImageBitmap(bitmap);
        }
    }

    //获取版本号
    public static String getVersion(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "未知";
        }
    }
}
