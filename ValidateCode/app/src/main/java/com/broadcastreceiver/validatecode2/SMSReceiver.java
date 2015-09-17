
package com.broadcastreceiver.validatecode2;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.telephony.SmsMessage;
import android.widget.EditText;

import com.jikexueyuan.validatecode.MainActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SMSReceiver extends BroadcastReceiver {

//    private static MessageListener mMessageListener;

    private String verifyCode = "";
    public static final String TAG = "SMSReceiver";
    public static final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";

    private  EditText et;


    public SMSReceiver(EditText et){
        this.et=et;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(SMS_RECEIVED_ACTION)) {

            System.out.println("短信监听者启动了！！！");

            SmsMessage[] messages = getMessagesFromIntent(intent);


            for (SmsMessage message : messages) {
                //短信发送号码
                String sender = message.getDisplayOriginatingAddress();
                //短信内容
                String content = message.getDisplayMessageBody();

                Log.i(TAG, message.getOriginatingAddress() + " : " +
                        sender + " : " +
                        content + " : " +
                        message.getTimestampMillis());



                //将短信内容写入SD卡
//                writeFile(smsContent);

                // (?<!\d)\d{6}(?!\d)
                Pattern pattern = Pattern.compile("(\\d{6})");
                Matcher matcher = pattern.matcher(content);

                if (matcher.find()) {
                    String code = matcher.group(0);

                    //设置信息
                    et.setText(code);
                }



                //过滤不需要读取的短信的发送号码
                if ("+8613450214963".equals(sender)) {
//                    mMessageListener.onReceived(content);
                    abortBroadcast();
                }
            }
        }
    }

    //回调接口
//    public interface MessageListener {
//        public void onReceived(String message);
//    }
//
//    public void setOnReceivedMessageListener(MessageListener messageListener) {
//        this.mMessageListener = messageListener;
//    }

    /**
     * 获取短信的方法
     *
     * @param intent
     * @return
     */
    public final SmsMessage[] getMessagesFromIntent(Intent intent) {
        Object[] messages = (Object[]) intent.getSerializableExtra("pdus");

        byte[][] pduObjs = new byte[messages.length][];
        for (int i = 0; i < messages.length; i++) {
            pduObjs[i] = (byte[]) messages[i];
        }
        byte[][] pdus = new byte[pduObjs.length][];
        int pduCount = pdus.length;
        SmsMessage[] msgs = new SmsMessage[pduCount];
        for (int i = 0; i < pduCount; i++) {
            pdus[i] = pduObjs[i];
            msgs[i] = SmsMessage.createFromPdu(pdus[i]);
        }
        return msgs;
    }



    //将短信内容写到SD卡上的文件里，便于将文件pull到PC，这样可方便其它如WWW/WAP平台的自动化
//    @SuppressLint("SdCardPath")
//    public void writeFile(String str) {
//        String filePath = "/mnt/sdcard/verifyCode.txt";
//        byte[] bytes = str.getBytes();
//        try {
//            File file = new File(filePath);
//            file.createNewFile();
//            FileOutputStream fos = new FileOutputStream(file);
//            fos.write(bytes);
//            fos.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
}
