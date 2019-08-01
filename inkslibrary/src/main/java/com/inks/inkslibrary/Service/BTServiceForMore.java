package com.inks.inkslibrary.Service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.SimpleExpandableListAdapter;
import com.inks.inkslibrary.Utils.L;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


public class BTServiceForMore extends Service {

    private final static String TAG = BTServiceForMore.class.getSimpleName();

    //连接成功
    public final static String LINK_SUCCESS = "LINK_SUCCESS";
    //连接失败
    public final static String LINK_FAIL = "LINK_FAIL";
    //断开连接
    public final static String DIS_LINK = "DIS_LINK";
    //连接时未找打该设备，可能已被移除
    public final static String CONNECT_NOT_FOUND = "CONNECT_NOT_FOUND";
    //连接成功，但未找到该设备，可能已被移除，断开连接
    public final static String LINK_SUCCESS_NOT_FOUND = "LINK_SUCCESS_NOT_FOUND";
    //连接失败，但未找到该设备，可能已被移除，断开连接
    public final static String LINK_FAIL_NOT_FOUND = "LINK_FAIL_NOT_FOUND";


    public final static String ACTION_GATT_CONNECTED = "ACTION_GATT_CONNECTED";
    public final static String ACTION_GATT_DISCONNECTED = "ACTION_GATT_DISCONNECTED";
    public final static String ACTION_GATT_SERVICES_DISCOVERED = "ACTION_GATT_SERVICES_DISCOVERED";
    public final static String ACTION_DATA_AVAILABLE = "ACTION_DATA_AVAILABLE";
    public final static String EXTRA_DATA = "EXTRA_DATA";
    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private ArrayList<BTforServiceBean> btArrayList = new ArrayList<>();
    private ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics =
            new ArrayList<ArrayList<BluetoothGattCharacteristic>>();
    private final String LIST_NAME = "NAME";
    private final String LIST_UUID = "UUID";
    private int maxReconnection = 1;
    private int nowReconnection = 1;
    private String nowReconnectionMac = "";
    //最大连接个数
    private int maxDevice = 4;
    //超时时间
    private int timeout = 10 * 1000;


    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {

        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            L.e("onConnectionStateChange" + gatt.getDevice().getAddress() + "   status:" + status + "    newState:" + newState);
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                //连接成功
                L.e("连接成功onConnectionStateChange");

                if (nowReconnectionMac.isEmpty()) {
                    //现在没有正在连接，却收到了连接成功？
                    L.e("BUG");
                }
                boolean addFlag = false;
                for (int i = 0; i < btArrayList.size(); i++) {
                    if (btArrayList.get(i).getMac().equals(gatt.getDevice().getAddress())) {
                        btArrayList.get(i).setState(1);
                        gatt.discoverServices();
                        if(nowReconnectionMac.equals(gatt.getDevice().getAddress())){
                            handler.removeMessages(2);
                            Message message = Message.obtain();
                            message.what = 2;
                            message.obj = nowReconnectionMac;
                            handler.sendMessageDelayed(message, 2);
                        }else{
                            L.e("列表中有该设备，但是现在正在连的不是它");
                        }
                        addFlag = true;
                        break;

                    }
                }

                if(!addFlag){
                    //没有添加或已被移除
                    broadcastUpdate(LINK_SUCCESS_NOT_FOUND,gatt.getDevice().getAddress());
                    gatt.disconnect();
                    gatt.close();

                }
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                //连接失败
                boolean addFlag = false;
                for (int i = 0; i < btArrayList.size(); i++) {
                    if (btArrayList.get(i).getMac().equals(gatt.getDevice().getAddress())) {
                        btArrayList.get(i).setState(0);
                        if(nowReconnectionMac.equals(gatt.getDevice().getAddress())){
                            L.e("该地址正在连接中，等待handler判断连接状态");
                        }else{
                            L.e("设备断开连接");
                            broadcastUpdate(DIS_LINK,gatt.getDevice().getAddress());
                            gatt.disconnect();
                            gatt.close();
                        }
                        addFlag = true;
                        break;
                    }
                }
                if(!addFlag){
                    //没有添加或已被移除
                    broadcastUpdate(LINK_FAIL_NOT_FOUND,gatt.getDevice().getAddress());
                    gatt.disconnect();
                    gatt.close();

                }
            }

        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            L.e("onServicesDiscovered");
            if (status == BluetoothGatt.GATT_SUCCESS) {
                Log.e(TAG, "onServicesDiscovered received: " + status);
                //broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED,gatt.getDevice().getAddress());
                displayGattServices(gatt.getServices());
                int number = 0;
                boolean addFlag = false;
                for (int i = 0; i < btArrayList.size(); i++) {
                    if (btArrayList.get(i).getMac().equals(gatt.getDevice().getAddress())) {
                        number = i;
                        addFlag = true;
                        break;
                    }
                }
                if(addFlag){

                    if (mGattCharacteristics != null) {
                        L.i("mGattCharacteristics:" + mGattCharacteristics.size());
                        BluetoothGattCharacteristic characteristic = null;
                        boolean flag = false;
                        for (int i = 0; i < mGattCharacteristics.size() && !flag; i++) {
                            L.i("mGattCharacteristics(i):" + mGattCharacteristics.get(i).size());
                            for (int k = 0; k < mGattCharacteristics.get(i).size(); k++) {
                                characteristic = mGattCharacteristics.get(i).get(k);
                                L.i(characteristic.getUuid().toString());
                                if (SampleGattAttributes.READ.equals(characteristic.getUuid()) || SampleGattAttributes.READ2.equals(characteristic.getUuid())) {
                                    flag = true;
                                    L.i("找到读ID");
                                    break;
                                }
                            }
                        }
                        boolean writeflag = false;
                        for (int i = 0; i < mGattCharacteristics.size() && !writeflag; i++) {
                            for (int k = 0; k < mGattCharacteristics.get(i).size(); k++) {
                                if (SampleGattAttributes.WRITE.equals(mGattCharacteristics.get(i).get(k).getUuid()) || SampleGattAttributes.WRITE2.equals(mGattCharacteristics.get(i).get(k).getUuid())) {
                                    btArrayList.get(number).setWriteCharacteristic(mGattCharacteristics.get(i).get(k));
                                    writeflag = true;
                                    L.i("找到写ID");
                                    break;
                                }
                            }
                        }
                        L.e("UUID结束");
                        final int charaProp = characteristic.getProperties();

                        if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                            setCharacteristicServerNotification(gatt, characteristic, true);
                        }
                    } else {
                        L.e("mGattCharacteristics == null");
                    }
                }else{
                    L.e("UUID结束----未添加该地址或已被移除");
                }

            } else {
                Log.e(TAG, "onServicesDiscovered received: " + status);
            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt,
                                         BluetoothGattCharacteristic characteristic,
                                         int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic, gatt.getDevice().getAddress());
            }
            Log.w(TAG, "1");
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt,
                                            BluetoothGattCharacteristic characteristic) {
            broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic, gatt.getDevice().getAddress());
            Log.w(TAG, "2");
        }

    };

    private void broadcastUpdate(final String action, String mac) {
        final Intent intent = new Intent(action);
        intent.putExtra("MAC", mac);
        sendBroadcast(intent);
    }

    private void broadcastUpdate(final String action,
                                 final BluetoothGattCharacteristic characteristic, String mac) {
        final Intent intent = new Intent(action);

        final byte[] data = characteristic.getValue();
        if (data != null && data.length > 0) {
            final StringBuilder stringBuilder = new StringBuilder(data.length);
            for (byte byteChar : data)
                stringBuilder.append(String.format("%02X ", byteChar));
            intent.putExtra(EXTRA_DATA, stringBuilder.toString());
            intent.putExtra("MAC", mac);
            Log.w(TAG, stringBuilder.toString());
            sendBroadcast(intent);
        }
    }


    public boolean connectWithMac(String address) {
        boolean addFlag = false;
        int flagIndex = 0;
        for (int i = 0; i < btArrayList.size(); i++) {
            if (btArrayList.get(i).getMac().equals(address)) {
                addFlag = true;
                flagIndex = i;
                break;
            }
        }
        if (!addFlag) {
            if (btArrayList.size() < maxDevice) {
                //添加
                BTforServiceBean bTforServiceBean = new BTforServiceBean();
                bTforServiceBean.setMac(address);
                bTforServiceBean.setState(0);
                bTforServiceBean.setGatt(null);
                btArrayList.add(bTforServiceBean);
                flagIndex = btArrayList.size() - 1;
            }else{
                //已经超过个数了
                L.e("列表已上限");
                return false;
            }
        }
        if(nowReconnectionMac.isEmpty()){
            //没有正在连接的
            if (btArrayList.get(flagIndex).getState() == 0) {
                //未连接，可以去连接
                btArrayList.get(flagIndex).setState(2);
                nowReconnection = 1;
                return connect(btArrayList.get(flagIndex).getMac());
            } else {
                L.e("已连接或连接中");
                return false;
            }
        }else{
            L.e("已有其他设备正在连接中");
            return false;

        }

    }

    @SuppressLint("MissingPermission")
    private boolean connect(String address) {

        if (mBluetoothAdapter == null || address == null) {
            Log.e(TAG, "BluetoothAdapter not initialized or unspecified address.");
            return false;
        }
        final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        if (device == null) {
            Log.e(TAG, "Device not found.  Unable to connect.");
            return false;
        }

        boolean addFlag = false;
        int flagIndex = 0;
        for (int i = 0; i < btArrayList.size(); i++) {
            if (btArrayList.get(i).getMac().equals(address)) {
                addFlag = true;
                flagIndex = i;
                break;
            }
        }

        if (addFlag) {
            if (btArrayList.get(flagIndex).getGatt() != null) {
                btArrayList.get(flagIndex).getGatt().disconnect();
                handler.removeMessages(1);
                Message message = Message.obtain();
                message.what = 1;
                message.obj = address;
                handler.sendMessageDelayed(message, 3000);
                return true;
            } else {
                BluetoothGatt mBluetoothGatt = device.connectGatt(this, false, mGattCallback);
                btArrayList.get(flagIndex).setGatt(mBluetoothGatt);
                btArrayList.get(flagIndex).setName(device.getName());
                L.e(" Trying to create a new connection." + address + ">>>>第" + nowReconnection + "次连接");
                nowReconnection++;
                nowReconnectionMac = address;
                handler.removeMessages(2);
                Message message = Message.obtain();
                message.what = 2;
                message.obj = address;
                handler.sendMessageDelayed(message, timeout);
                return true;
            }
        } else {
            Log.e(TAG, "connect 未发现该设备.");
            return false;
        }
    }


    public void writeCharacteristic(String mac, byte[] bytes) {

        for (int i = 0; i < btArrayList.size(); i++) {
            if (btArrayList.get(i).getMac().equals(mac) && btArrayList.get(i).getState() == 1) {
                if (mBluetoothAdapter == null || btArrayList.get(i).getGatt() == null) {
                    Log.w(TAG, "BluetoothAdapter not initialized");
                    return;
                }
                boolean flag = false;
                try {
                    btArrayList.get(i).getWriteCharacteristic().setValue(bytes);
                    flag = btArrayList.get(i).getGatt().writeCharacteristic(btArrayList.get(i).getWriteCharacteristic());
                    Log.e(TAG, "           send flag:  " + flag);
                } catch (Exception e) {
                    L.e("writeCharacteristic失败");
                    L.e(e.toString());
                }

                if (!flag) {
                    Log.e(TAG, "send flag:" + flag);
                }

                break;
            }
        }
    }


    public void writeCharacteristic(BluetoothGattCharacteristic characteristic, BluetoothGatt gatt, byte[] bytes) {
        if (mBluetoothAdapter == null || gatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        boolean flag = false;
        try {
            characteristic.setValue(bytes);
            flag = gatt.writeCharacteristic(characteristic);
            Log.e(TAG, "           send flag:  " + flag);
        } catch (Exception e) {
            L.e("writeCharacteristic失败");
            L.e(e.toString());
        }
        if (!flag) {
            Log.e(TAG, "send flag:" + flag);
        }
    }

    public void writeAllCharacteristic(byte[] bytes) {
        for (int i = 0; i < btArrayList.size(); i++) {
            if (btArrayList.get(i).getState() == 1) {
                if (mBluetoothAdapter == null || btArrayList.get(i).getGatt() == null) {
                    Log.w(TAG, "BluetoothAdapter not initialized");
                    return;
                }
                boolean flag = false;
                try {
                    btArrayList.get(i).getWriteCharacteristic().setValue(bytes);
                    flag = btArrayList.get(i).getGatt().writeCharacteristic(btArrayList.get(i).getWriteCharacteristic());
                    Log.e(TAG, "           send flag:  " + flag);

                } catch (Exception e) {
                    L.e("writeCharacteristic失败");
                    L.e(e.toString());
                }
                if (!flag) {
                    Log.e(TAG, "send flag:" + flag);
                }
            }
        }
    }

    public ArrayList<String> getLinkBt() {
        ArrayList<String> linkBt = new ArrayList<>();
        for (int i = 0; i < btArrayList.size(); i++) {
            if (btArrayList.get(i).getState() == 1) {
                linkBt.add(btArrayList.get(i).getMac());
            }
        }
        return linkBt;
    }

    public ArrayList<String> getAllBt() {
        ArrayList<String> allBt = new ArrayList<>();
        for (int i = 0; i < btArrayList.size(); i++) {
            allBt.add(btArrayList.get(i).getMac());
        }
        return allBt;
    }

    public ArrayList<BTBean> getBTBean() {
        ArrayList<BTBean> bTBean = new ArrayList<>();
        for (int i = 0; i < btArrayList.size(); i++) {
            if (btArrayList.get(i).getState() == 1) {
                BTBean bbb = new BTBean();
                bbb.setMac(btArrayList.get(i).getMac());
                bbb.setName(btArrayList.get(i).getName());
                bTBean.add(bbb);
            }
        }
        return bTBean;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // callback(startId);
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public IBinder onBind(Intent intent) {
        L.e("服务绑定");
        return mBinder;

    }

    @Override
    public boolean onUnbind(Intent intent) {
        L.e("服务解除绑定");
        // close();
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        L.e("服务onCreate");
    }

    public void close(String mac) {
        for (int i = 0; i < btArrayList.size(); i++) {
            if (btArrayList.get(i).getMac().equals(mac)) {
                if (btArrayList.get(i).getGatt() != null) {
                    btArrayList.get(i).getGatt().close();
                    btArrayList.get(i).setGatt(null);
                }
                // btArrayList.get(i).setGatt(null);
                btArrayList.remove(i);
                break;
            }
        }
    }

    public void closeAll() {
        L.e("closeAll:");
        for (int i = 0; i < btArrayList.size(); i++) {
            if (btArrayList.get(i).getGatt() != null) {
                btArrayList.get(i).getGatt().close();
                btArrayList.get(i).setGatt(null);
            }
            // btArrayList.get(i).setGatt(null);
        }
        btArrayList.clear();
    }


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1://如果GATT不等于NULL，dis 3秒后close；
                    String mac = (String) msg.obj;
                    for (int i = 0; i < btArrayList.size(); i++) {
                        if (btArrayList.get(i).getMac().equals(mac)) {
                            btArrayList.get(i).getGatt().close();
                            btArrayList.get(i).setGatt(null);
                            connect(mac);
                            break;
                        }
                    }
                    break;
                case 2://开始连接，超时时间后可再次连接继续
                    mac = (String) msg.obj;
                    boolean addFlag = false;
                    for (int i = 0; i < btArrayList.size(); i++) {
                        if (btArrayList.get(i).getMac().equals(mac)) {
                            if (btArrayList.get(i).getState() == 1) {
                                //已连接
                                nowReconnectionMac="";
                                nowReconnection=1;
                                broadcastUpdate(LINK_SUCCESS, mac);
                            } else {
                                if (nowReconnection < maxReconnection) {
                                    //未连接，继续重连
                                    connect(mac);
                                } else {
                                    btArrayList.get(i).setState(0);
                                    btArrayList.get(i).getGatt().disconnect();
                                    nowReconnectionMac="";
                                    nowReconnection=1;
                                    //已经超过次数了,但是没连接成功
                                    broadcastUpdate(LINK_FAIL, mac);

                                }
                            }
                            addFlag = true;
                            break;
                        }
                    }
                    if (!addFlag) {
                        //没有找到该设备
                        nowReconnectionMac="";
                        nowReconnection=1;
                        broadcastUpdate(CONNECT_NOT_FOUND, mac);
                    }
                    break;
            }
        }
    };

    private final IBinder mBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        public BTServiceForMore getService() {
            L.e(" return BTServiceForMore.this;");
            return BTServiceForMore.this;
        }
    }

    public int getMaxReconnection() {
        return maxReconnection;
    }

    public void setMaxReconnection(int maxReconnection) {
        this.maxReconnection = maxReconnection;
    }

    public int getNowReconnection() {
        return nowReconnection;
    }


    public String getNowReconnectionMac() {
        return nowReconnectionMac;
    }

    public int getMaxDevice() {
        return maxDevice;
    }

    public void setMaxDevice(int maxDevice) {
        this.maxDevice = maxDevice;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public boolean initialize() {
        // For API level 18 and above, get a reference to BluetoothAdapter through
        // BluetoothManager.
        if (mBluetoothManager == null) {
            mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            if (mBluetoothManager == null) {
                Log.e(TAG, "Unable to initialize BluetoothManager.");
                return false;
            }
        }

        mBluetoothAdapter = mBluetoothManager.getAdapter();
        if (mBluetoothAdapter == null) {
            Log.e(TAG, "Unable to obtain a BluetoothAdapter.");
            return false;
        }
        return true;
    }

    private void displayGattServices(List<BluetoothGattService> gattServices) {
        if (gattServices == null) return;
        String uuid = null;
        String unknownServiceString = ("Unknown");
        String unknownCharaString = ("Unknown");
        ArrayList<HashMap<String, String>> gattServiceData = new ArrayList<HashMap<String, String>>();
        ArrayList<ArrayList<HashMap<String, String>>> gattCharacteristicData
                = new ArrayList<ArrayList<HashMap<String, String>>>();
        mGattCharacteristics = new ArrayList<ArrayList<BluetoothGattCharacteristic>>();

        // Loops through available GATT Services.
        for (BluetoothGattService gattService : gattServices) {
            HashMap<String, String> currentServiceData = new HashMap<String, String>();
            uuid = gattService.getUuid().toString();
            currentServiceData.put(
                    LIST_NAME, SampleGattAttributes.lookup(uuid, unknownServiceString));
            currentServiceData.put(LIST_UUID, uuid);
            gattServiceData.add(currentServiceData);

            ArrayList<HashMap<String, String>> gattCharacteristicGroupData =
                    new ArrayList<HashMap<String, String>>();
            List<BluetoothGattCharacteristic> gattCharacteristics =
                    gattService.getCharacteristics();
            ArrayList<BluetoothGattCharacteristic> charas =
                    new ArrayList<BluetoothGattCharacteristic>();

            // Loops through available Characteristics.
            for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
                charas.add(gattCharacteristic);
                HashMap<String, String> currentCharaData = new HashMap<String, String>();
                uuid = gattCharacteristic.getUuid().toString();
                currentCharaData.put(
                        LIST_NAME, SampleGattAttributes.lookup(uuid, unknownCharaString));
                currentCharaData.put(LIST_UUID, uuid);
                gattCharacteristicGroupData.add(currentCharaData);
            }
            mGattCharacteristics.add(charas);
            gattCharacteristicData.add(gattCharacteristicGroupData);
        }

        SimpleExpandableListAdapter gattServiceAdapter = new SimpleExpandableListAdapter(
                this,
                gattServiceData,
                android.R.layout.simple_expandable_list_item_2,
                new String[]{LIST_NAME, LIST_UUID},
                new int[]{android.R.id.text1, android.R.id.text2},
                gattCharacteristicData,
                android.R.layout.simple_expandable_list_item_2,
                new String[]{LIST_NAME, LIST_UUID},
                new int[]{android.R.id.text1, android.R.id.text2}
        );

    }


    public List<BluetoothGattService> getSupportedGattServices(String mac) {

        for (int i = 0; i < btArrayList.size(); i++) {
            if (btArrayList.get(i).getMac().equals(mac)) {
                if (btArrayList.get(i).getGatt() == null) {
                    return null;
                } else {
                    return btArrayList.get(i).getGatt().getServices();
                }
            }
        }
        return null;
    }


    public void setCharacteristicServerNotification(BluetoothGatt gatt,
                                                    BluetoothGattCharacteristic characteristic, boolean enabled) {
        if (mBluetoothAdapter == null || gatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }

        try {
            BluetoothGattDescriptor descriptor = characteristic
                    .getDescriptor(UUID
                            .fromString(SampleGattAttributes.CLIENT_CHARACTERISTIC_CONFIG));
            if (descriptor != null) {
                Log.e(TAG,
                        "set descriptor" + descriptor + "|" + descriptor.getValue());
                if (enabled)
                    descriptor
                            .setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                else
                    descriptor
                            .setValue(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
                gatt.writeDescriptor(descriptor);

                gatt.setCharacteristicNotification(characteristic, enabled);

            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }


}










/**
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            final String MAC = intent.getStringExtra("MAC");

//            //连接成功
//            public final static String LINK_SUCCESS = "LINK_SUCCESS";
//            //连接失败
//            public final static String LINK_FAIL = "LINK_FAIL";
//            //断开连接
//            public final static String DIS_LINK = "DIS_LINK";
//            //连接时未找打该设备，可能已被移除
//            public final static String CONNECT_NOT_FOUND = "CONNECT_NOT_FOUND";
//            //连接成功，但未找到该设备，可能已被移除，断开连接
//            public final static String LINK_SUCCESS_NOT_FOUND = "LINK_SUCCESS_NOT_FOUND";
//            //连接失败，但未找到该设备，可能已被移除，断开连接
//            public final static String LINK_FAIL_NOT_FOUND = "LINK_FAIL_NOT_FOUND";

            L.i(MAC + "");
            L.i(action);
            if (BTServiceForMore.LINK_SUCCESS.equals(action)) {
                L.e("BroadcastReceiver连接成功");

            } else if (BTServiceForMore.LINK_FAIL.equals(action)) {
                L.e("BroadcastReceiver连接失败");

            } else if (BTServiceForMore.DIS_LINK.equals(action)) {
                L.e("BroadcastReceiver断开连接");

            } else if (BTServiceForMore.CONNECT_NOT_FOUND.equals(action)) {
                L.e("BroadcastReceiverCONNECT_NOT_FOUND 未找到该地址");

            } else if (BTServiceForMore.LINK_SUCCESS_NOT_FOUND.equals(action)) {
                L.e("BroadcastReceiverLINK_SUCCESS_NOT_FOUND 未找到该地址");

            } else if (BTServiceForMore.LINK_FAIL_NOT_FOUND.equals(action)) {
                L.e("BroadcastReceiverLINK_FAIL_NOT_FOUND 未找到该地址");
            } else if (BTServiceForMore.ACTION_DATA_AVAILABLE.equals(action)) {
                //收到数据
                L.e("BroadcastReceiver收到数据");
            }
        }
    };





 private static IntentFilter makeGattUpdateIntentFilter() {
 final IntentFilter intentFilter = new IntentFilter();
 intentFilter.addAction(BTServiceForMore.LINK_SUCCESS);
 intentFilter.addAction(BTServiceForMore.LINK_FAIL);
 intentFilter.addAction(BTServiceForMore.DIS_LINK);
 intentFilter.addAction(BTServiceForMore.CONNECT_NOT_FOUND);
 intentFilter.addAction(BTServiceForMore.LINK_SUCCESS_NOT_FOUND);
 intentFilter.addAction(BTServiceForMore.LINK_FAIL_NOT_FOUND);
 intentFilter.addAction(BTServiceForMore.ACTION_DATA_AVAILABLE);
 return intentFilter;
 }





 BTServiceForMore btServiceForMore;
 private final ServiceConnection mServiceConnection = new ServiceConnection() {
@Override
public void onServiceConnected(ComponentName componentName, IBinder service) {
btServiceForMore = ((BTServiceForMore.LocalBinder) service).getService();

if (!btServiceForMore.initialize()) {
Log.e("MainApplication", "Unable to initialize Bluetooth");
} else {
Log.e("MainApplication", "服务绑定成功");
}
}
@Override
public void onServiceDisconnected(ComponentName componentName) {
Log.e("MainApplication", "服务解除绑定");
}
};





 */