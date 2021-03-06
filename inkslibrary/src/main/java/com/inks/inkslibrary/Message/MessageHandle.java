package com.inks.inkslibrary.Message;


import android.os.Handler;
import android.os.Message;


import com.inks.inkslibrary.Utils.L;

import java.util.ArrayList;


public class MessageHandle {

    private boolean atWork = false;
    private Handler handler;
    private int what;
    private int failWhat;
    private ArrayList<MessageBean> handleArrayList = new ArrayList<>();
    private MessageBean lastC;

    Message m;

    public MessageHandle(Handler handler, int what, int failWhat) {
        this.handler = handler;
        this.what = what;
        this.failWhat = failWhat;
    }

    public void addMessage(MessageBean messageBean) {
        boolean flag = true;//是否需要添加
        if (messageBean.isDeleteSame()) {//删除flag相同的
            for (int i = 0; i < handleArrayList.size(); i++) {
                if (handleArrayList.get(i).getFlag2().equals(messageBean.getFlag2())) {
                    if(i ==0){
                        if(handleArrayList.get(0).isProcessing()){//正在运行，不删除
                            flag = false;
                        }else{
                            handleArrayList.remove(i);
                            i--;
                        }
                    }else{
                        handleArrayList.remove(i);
                        i--;
                    }

                }
            }
        }
        if(flag){
            if (messageBean.isQueueUp()) {//插队
                if (handleArrayList.size() > 0 && handleArrayList.get(0).isProcessing()) {
                    handleArrayList.add(1, messageBean);
                } else {
                    handleArrayList.add(0, messageBean);
                }
            }else{
                handleArrayList.add(messageBean);
            }
        }
        //加入完成，发送
        sendMessage();
    }


    private void sendMessage() {
        if (!atWork) {
            if (handleArrayList.size() > 0) {
                atWork = true;
                m = handler.obtainMessage();
                m.what = what;
                m.obj = handleArrayList.get(0);
                lastC =  handleArrayList.get(0);
                handler.sendMessage(m);
                handleArrayList.get(0).setProcessing(true);
                handleArrayList.get(0).setRepeatTimes(handleArrayList.get(0).getRepeatTimes() - 1);
                L.e("还有次数：" + handleArrayList.get(0).getRepeatTimes());
//                L.e(handleArrayList.get(0).getFlag() + ":" + StringAndByteUtils.printHexByte(handleArrayList.get(0).getDatas()));
                if (handleArrayList.get(0).getRepeatTimes() < 1) {
                    handleArrayList.remove(0);
                }
            }
        }
    }


    //收到回信，开始发送下一次消息
    public void activityReply(boolean b) {
        if (b) {
            //如果收到回复，则取消该消息的重发（如果还有重发次数）
            if (handleArrayList.size() > 0 && handleArrayList.get(0).isProcessing()) {
                handleArrayList.remove(0);
            }
        }else{//失败的
            if (handleArrayList.size() > 0 && !handleArrayList.get(0).isProcessing()) {
                //这条命令没有正在处理，还没有开始处理，这失败的命令已经是最后一次处理了
                sendFailMessage();
            }else if(handleArrayList.size() == 0){
                //没有命令了，失败的已经处理完
                sendFailMessage();
            }
        }
        atWork = false;
        sendMessage();

    }


    private void sendFailMessage() {
        m = handler.obtainMessage();
        m.what = failWhat;
        m.obj =lastC;
        handler.sendMessage(m);
    }

    public void removeAll(){
        handleArrayList.clear();
        atWork = false;
    }



}
