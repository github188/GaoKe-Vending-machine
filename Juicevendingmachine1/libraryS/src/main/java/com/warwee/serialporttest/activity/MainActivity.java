package com.warwee.serialporttest.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.warwee.serialporttest.R;
import com.warwee.serialporttest.utils.SerialPortUtil;

public class MainActivity extends Activity {

    private EditText editInput;
    private Button btnSend;
    private static TextView textShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    /**
     * 初始化UI控件
     */
    private void init() {
        editInput = (EditText) findViewById(R.id.et_main_input);
        btnSend = (Button) findViewById(R.id.btn_main_send);
        textShow = (TextView) findViewById(R.id.tv_main_show);
        SerialPortUtil.openSrialPort();
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSend();
            }
        });
    }

    /**
     * 发送串口数据
     */
    private void onSend() {
        String input = editInput.getText().toString();
        if(TextUtils.isEmpty(input)){
            editInput.setError("要发送的数据不能为空");
            return;
        }
        SerialPortUtil.sendSerialPort(input);
    }

    /**
     * 刷新UI界面
     * @param data 接收到的串口数据
     */

    public static void refreshTextView(String data){

        textShow.setText(textShow.getText().toString()+";"+data);

    }
}
