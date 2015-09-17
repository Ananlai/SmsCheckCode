package com.broadcastreceiver.validatecode2;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.jikexueyuan.validatecode.R;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by fangchaolai on 2015/9/17.
 * 本例：用短信接受者，实时获取短信
 * 方案1：用了2种方法，1是保存到sd卡中，一种是利用构造函数传递控件。
 */
public class MainActivity2 extends Activity {

    private String TAG = "MainActivity2";

    EditText et_validateCode2;

    private SMSReceiver mSMSBroadcastReceiver;
    private static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_main2);

        et_validateCode2=(EditText)findViewById(R.id.et_validateCode2);

//        String path="/mnt/sdcard/verifyCode.txt";
//
//        try {
//
//           String result= read(path);
//
//            System.out.println("从sd卡中获取短信：" + result);
//
//            //截取字符串
//
//            //显示验证码
//            et_validateCode2.setText(result);
//        }catch (Exception e){
//
//            e.printStackTrace();
//        }

    }



    @Override
    protected void onStart() {
        super.onStart();
        //生成广播处理
        mSMSBroadcastReceiver = new SMSReceiver(et_validateCode2);

        //实例化过滤器并设置要过滤的广播
        IntentFilter intentFilter = new IntentFilter(ACTION);
        intentFilter.setPriority(Integer.MAX_VALUE);
        //注册广播
        this.registerReceiver(mSMSBroadcastReceiver, intentFilter);

        //回调
//        mSMSBroadcastReceiver.setOnReceivedMessageListener(new SMSReceiver.MessageListener() {
//            @Override
//            public void onReceived(String message) {
//
//                System.out.println("获取的短信：" + message);
//                et_validateCode2.setText(message);
//
//            }
//        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销短信监听广播
        this.unregisterReceiver(mSMSBroadcastReceiver);
    }


    /**
     * @param str :路径名
     * @return :返回短信内容
     * @throws IOException
     */
//    public String read(String str) throws IOException {
//
//        File file = new File(str);
//        FileInputStream fis = new FileInputStream(file);
//        StringBuffer sb = new StringBuffer();
//
//        BufferedInputStream bis = new BufferedInputStream(fis);
//        BufferedReader read = new BufferedReader(new InputStreamReader(bis));
//        int c = 0;
//        while ((c = read.read()) != -1) {
//
//            sb.append((char) c);
//        }
//        read.close();
//        bis.close();
//        fis.close();
//        Log.i(TAG, sb.toString());
//        String verify = sb.toString();
//        return verify;
//    }


}
