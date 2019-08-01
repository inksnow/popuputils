package com.inks.inkslibrary.Message;


/**
 * Created by inks on 2018/8/3 0003.
 */

public class MessageBean {

    private int sendID1;
    private int sendID2;
    private int index;
    //命令标志
    private String flag1 = "";
    //命令标志2
    private String flag2 = "";
    //命令数据
    private byte[] datas;
    //最多发送次数
    private int repeatTimes = 1;
    //是否移除相同标志的其他消息（如果相同标志的消息正在处理，则移除自己）
    private boolean deleteSame = true;
    //是否正在处理
    private boolean processing = false;
    //是否插队（如果没有正在处理的消息，则插在第一位，如果有，则插入第二位）
    private boolean queueUp = false;
    //是否发送给所有，如果不是，就用指定mac
    private boolean isAll = true;
    private String mac;

    public int getSendID1() {
        return sendID1;
    }

    public void setSendID1(int sendID1) {
        this.sendID1 = sendID1;
    }

    public int getSendID2() {
        return sendID2;
    }

    public void setSendID2(int sendID2) {
        this.sendID2 = sendID2;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getFlag1() {
        return flag1;
    }

    public void setFlag1(String flag1) {
        this.flag1 = flag1;
    }

    public String getFlag2() {
        return flag2;
    }

    public void setFlag2(String flag2) {
        this.flag2 = flag2;
    }

    public byte[] getDatas() {
        return datas;
    }

    public void setDatas(byte[] datas) {
        this.datas = datas;
    }

    public int getRepeatTimes() {
        return repeatTimes;
    }

    public void setRepeatTimes(int repeatTimes) {
        this.repeatTimes = repeatTimes;
    }

    public boolean isDeleteSame() {
        return deleteSame;
    }

    public void setDeleteSame(boolean deleteSame) {
        this.deleteSame = deleteSame;
    }

    public boolean isProcessing() {
        return processing;
    }

    public void setProcessing(boolean processing) {
        this.processing = processing;
    }

    public boolean isQueueUp() {
        return queueUp;
    }

    public void setQueueUp(boolean queueUp) {
        this.queueUp = queueUp;
    }

    public boolean isAll() {
        return isAll;
    }

    public void setAll(boolean all) {
        isAll = all;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

}