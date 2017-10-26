package com.aircontrol.demo.utils;

/**
 * Created by Silenoff on 2016/11/24.
 * UDP发送命令处理类
 * 用于生成发送字节流
 */

public class UdpOrderUtil {
    //家庭模式发送的命令
    public final byte head = (byte) 0xaa;
    public final byte end = 0x55;
    //查询命令的包类型和返回包类型
    //查询内机个数
    public final byte type_search_number = (byte) 0x80;
    public final byte type_return_number = (byte) 0x81;
    //查询内机状态
    public final byte type_search_inside = (byte) 0x90;
    public final byte type_return_inside = (byte) 0x91;
    //设置内机状态
    public final byte type_search_set_inside = (byte) 0xA0;
    public final byte type_return_set_inside = (byte) 0xA1;
    //配置SSID、PWD
    public final byte type_config_ssid = (byte) 0xB0;
    public final byte type_return_config_ssid = (byte) 0xB1;

    public final byte[] byteBitOneOnly = new byte[]{
            0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, (byte) 0x80
    };
    private UdpOrderUtil() {
    }
    private static UdpOrderUtil instance = null;
//实例化UdpOrderUtil对象
    public static UdpOrderUtil getInstance() {
        if (instance == null) {
            instance = new UdpOrderUtil();
        }
        return instance;
    }

    /**
     * 得到需要发送的帧数据
     *
     * @param type  帧类型
     * @param datas 去除包头，去除包类型的字节流
     *              type和datas这两个组合即为需要参与校验和运算的数据
     * @return 需要发送的帧数据
     */
    public byte[] getSendOrderFrameBytes(byte type, byte[] datas) {
        int length = getFrameLength(type);//根据不同命令按规定返回不同的帧长度
        byte[] result = new byte[length];//申请一个字节数组
        /*
        * 规定首部和命令类型*/
        result[0] = head;
        result[1] = type;
        if (length < 4) {
            return null;
        } else if (length == 4) {
            result[length - 2] = result[1];  //0xaa 0x80 0x01 0x02 0x83 0x55中间两个位置交换
        } else { // length > 4 的情况
            byte[] checksum = new byte[datas.length + 1];
            checksum[0] = type;
            System.arraycopy(datas, 0, checksum, 1, datas.length);
            System.arraycopy(datas, 0, result, 2, datas.length);
            result[length - 2] = GetCheckSum(checksum);//得到校验和
        }
        result[length - 1] = end;//帧尾
        return result;//返回发送的数据帧
    }

    public byte[] getSSIDPWDBytes(String ssid, String pwd) {
        int position = 0;
        byte[] ssid_bytes = ssid.getBytes();
        byte[] pwd_bytes = pwd.getBytes();
        int ssid_bytes_length = ssid_bytes.length;
        int pwd_bytes_length = pwd_bytes.length;
        int total_length = ssid_bytes_length + pwd_bytes_length + 1 + 1 + 1 + 1 + 1 + 1; //1为包头，1为包类型，1为ssid长度，1为pwd长度，1为校验长度，1为包尾
        byte[] result = new byte[total_length];
        result[position] = head;
        position++;
        result[position] = type_config_ssid;
        position++;
        result[position] = (byte) ssid_bytes_length;
        position++;
        System.arraycopy(ssid_bytes, 0, result, position, ssid_bytes_length);
        position += ssid_bytes_length;
        result[position] = (byte) pwd_bytes_length;
        position++;
        System.arraycopy(pwd_bytes, 0, result, position, pwd_bytes_length);
        byte[] checksum = new byte[total_length - 3];
        System.arraycopy(result, 1, checksum, 0, checksum.length);
        result[total_length - 2] = GetCheckSum(checksum);
        result[total_length - 1] = end;
        return result;
    }

    /**
     * 根据类型查询帧长度
     *
     * @param type 类型
     * @return 长度
     */
    public int getFrameLength(byte type) {
        int length;
        switch (type) {
            case type_search_number:
                length = 4;
                break;
            case type_return_number:
                length = 9;
                break;
            case type_search_inside:
                length = 5;
                break;
            case type_return_inside:
                length = 14;
                break;
            case type_search_set_inside:
                length = 15;
                break;
            case type_return_set_inside:
                length = 4;
                break;
            case type_config_ssid: //长度不定
                length = -1;
                break;
            case type_return_config_ssid:
                length = 4;
                break;
            default:
                length = 0;
                break;
        }
        return length;
    }

    /**
     * 得到需要设置的内机字节流，内机编号代表着某一位
     * @param numbers 需要设置的内机编号
     * @return
     */
    public byte[] getInsideMachineSendBytes(int[] numbers) {
        int num = numbers.length;
        byte[] result = new byte[8];
        for (int i = 0; i < num; i++) {
            if (numbers[i] > 63) {
                return result;
            } else if (numbers[i] >= 56) {
                int index = numbers[i] - 56;
                result[7] = (byte) (result[7] | byteBitOneOnly[index]);
            } else if (numbers[i] >= 48) {
                int index = numbers[i] - 48;
                result[6] = (byte) (result[6] | byteBitOneOnly[index]);
            } else if (numbers[i] >= 40) {
                int index = numbers[i] - 40;
                result[5] = (byte) (result[5] | byteBitOneOnly[index]);
            } else if (numbers[i] >= 32) {
                int index = numbers[i] - 32;
                result[4] = (byte) (result[4] | byteBitOneOnly[index]);
            } else if (numbers[i] >= 24) {
                int index = numbers[i] - 24;
                result[3] = (byte) (result[3] | byteBitOneOnly[index]);
            } else if (numbers[i] >= 16) {
                int index = numbers[i] - 16;
                result[2] = (byte) (result[2] | byteBitOneOnly[index]);
            } else if (numbers[i] >= 8) {
                int index = numbers[i] - 8;
                result[1] = (byte) (result[1] | byteBitOneOnly[index]);
            } else if (numbers[i] >= 0) {
                int index = numbers[i] - 0;
                result[0] = (byte) (result[0] | byteBitOneOnly[index]);
            } else {
                return result;
            }
        }
        return result;
    }

    /**
     * 校验和是否正确
     *
     * @param byteData 需要校验的字节流
     * @param count    需要校验的字节流长度
     * @return 是否校验成功
     */
    public boolean CheckSum(byte[] byteData, int count) {
        byte sum = 0;
        int i = 0;
        for (i = 0; i < count - 1; i++) {
            sum += byteData[i] & 0xff;
        }
        if ((sum & 0xff) == (byteData[count - 1] & 0xff)) {
            return true;
        } else
            return false;
    }

    /**
     * 得到校验和
     *
     * @param byteData 需要计算校验和的字节流
     * @return
     */
    public byte GetCheckSum(byte[] byteData) {
        byte sum = 0;
        int i = 0;
        int count = byteData.length;
        for (i = 0; i < count; i++) {
            sum += byteData[i] & 0xff;
        }
        return sum;
    }

}
