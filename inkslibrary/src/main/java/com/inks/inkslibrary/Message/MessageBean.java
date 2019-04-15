package com.inks.inkslibrary.Message;


/**
 * Created by inks on 2018/8/3 0003.
 */

public class MessageBean {

    private int sendID;

    private int  commandID;

    private int index;
    //命令标志
    private String flag = "";
    //命令标志2   删除flag2相同的
    private String flag2 = "";
    //命令数据
    private SendDataBen datas;
    //最多发送次数
    private int repeatTimes = 1;
    //是否移除相同标志的其他消息（如果相同标志的消息正在处理，则移除自己）
    private boolean deleteSame = true;
    //是否正在处理
    private boolean processing = false;
    //是否插队（如果没有正在处理的消息，则插在第一位，如果有，则插入第二位）
    private boolean queueUp = false;

    public int getSendID() {
        return sendID;
    }

    public void setSendID(int sendID) {
        this.sendID = sendID;
    }

    public int getCommandID() {
        return commandID;
    }

    public void setCommandID(int commandID) {
        this.commandID = commandID;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getFlag2() {
        return flag2;
    }

    public void setFlag2(String flag2) {
        this.flag2 = flag2;
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

    public SendDataBen getDatas() {
        return datas;
    }

    public void setDatas(SendDataBen datas) {
        this.datas = datas;
    }
}
