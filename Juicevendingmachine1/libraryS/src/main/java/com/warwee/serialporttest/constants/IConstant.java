package com.warwee.serialporttest.constants;

/**
 * Created by Administrator on 2017/6/6.
 */

public interface IConstant {

    String PORT = "ttyS2";//串口号
    int BAUDRATE = 9600;//波特率

    int dataBits=8;//数据位
    int stopBits=1;//停止位
    char paritys='N';//偶校验位

}
