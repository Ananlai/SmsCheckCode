package com.jikexueyuan.validatecode;

import android.content.Context;
import android.database.ContentObserver;

import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 短信观察者
 */
public class SmsObserver extends ContentObserver {

    private Context mContext;
    private Handler mHandler;

    //ContentObserver:构造函数需要一个handler,为ContentObserver内部使用了一个实现Runnable接口的内部类
    // NotificationRunnable，来实现数据库内容的变化,需要使用handler去post消息.

    public SmsObserver(Context context, Handler handler) {
        super(handler);
        mContext = context;
        mHandler = handler;
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);

        Log.e("DEBUG", "SMS has changed!");


        String code = "";

        if (uri.toString().equals("content://sms/raw")) {
            return;
        }
        Log.e("DEBUG", uri.toString());

        Uri inboxUri = Uri.parse("content://sms/inbox");
        // 读取收件箱中指定号码的短信
        Cursor c = mContext.getContentResolver().query(inboxUri, null, null, null, "date desc");



        //// 如果短信为未读模式
        if (c != null) {

            if (c.moveToFirst()) {
                String address = c.getString(c.getColumnIndex("address"));
                String body = c.getString(c.getColumnIndex("body"));

                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

                //设定号码：155-5521-5554 //15612218031
                if (!address.equals("15010829168")) {
                    return;
                }

                Log.e("DEBUG", "发件人为：" + address + " " + "短信内容为：" + body);

                //短信内容匹配
                Pattern pattern = Pattern.compile("(\\d{6})");
                Matcher matcher = pattern.matcher(body);

                if (matcher.find()) {
                    code = matcher.group(0);
                    Log.e("DEBUG", "code is " + code);

                    //发消息
                    mHandler.obtainMessage(MainActivity.MSG_RECEIVED_CODE, code).sendToTarget();
                }

            }
            c.close();
        }else{
            System.out.println("cursor为空");
        }

    }
}
