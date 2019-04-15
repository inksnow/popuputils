package com.inks.inkslibrary.Message;

public class SendDataBen {
    public byte[] sendData;
    //是否发送给所有，如果不是，就用下边的指定mac
    public boolean isAll;
    public String mac ;
}
