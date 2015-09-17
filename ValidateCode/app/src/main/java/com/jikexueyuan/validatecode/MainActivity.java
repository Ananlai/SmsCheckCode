package com.jikexueyuan.validatecode;

import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

/**
 * android上获取短信信息主要有BroadcastReceiver方式与数据库方式，
 * 要实时的话就BroadcastReceiver比较方便.
 */
public class MainActivity extends AppCompatActivity {

    public static final int MSG_RECEIVED_CODE = 1;

    private EditText et_ValidateCode = null;

    private SmsObserver mObserver;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_RECEIVED_CODE) {

                String code = (String) msg.obj;
                System.out.println("--------------------获取到验证码了--:" + code);
                //update the UI
                et_ValidateCode.setText(code);
            }

        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        getContentResolver().unregisterContentObserver(mObserver);
    }

    //该类入口
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //输入框
        et_ValidateCode = (EditText) findViewById(R.id.et_validateCode);
        //创建短信观察者
        mObserver = new SmsObserver(MainActivity.this, mHandler);

        Uri uri = Uri.parse("content://sms");

        //注册：uir数据库的uri;    notifyForDescendents---boolean  true的话就会监听所有与此uri相关的uri。
        // false的话则是直接特殊的uri才会监听。一般都设置为true.
        getContentResolver().registerContentObserver(uri, true, mObserver);
    }


    //当用户点击menu时调用此方法。
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    //当用户点击一个菜单选项时，调用。
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
