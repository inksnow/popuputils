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
import com.inks.inkslibrary.Utils.StringAndByteUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


public class BTServiceForMore extends Service {

    private final static String TAG = BTServiceForMore.class.getSimpleName();
    public final static String ACTION_GATT_CONNECTED = "com.example.bluetooth.le.ACTION_GATT_CONNECTED";
    public final static String ACTION_GATT_DISCONNECTED = "com.example.bluetooth.le.ACTION_GATT_DISCONNECTED";
    public final static String ACTION_GATT_SERVICES_DISCOVERED = "com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED";
    public final static String ACTION_DATA_AVAILABLE = "com.example.bluetooth.le.ACTION_DATA_AVAILABLE";
    public final static String EXTRA_DATA = "com.example.bluetooth.le.EXTRA_DATA";
    public final static String ALL_DATA = "com.example.bluetooth.le.ALL_DATA";

    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;

    private ArrayList<BTforServiceBean> btArrayList = new ArrayList<>();


    private ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics =
            new ArrayList<ArrayList<BluetoothGattCharacteristic>>();
    private final String LIST_NAME = "NAME";
    private final String LIST_UUID = "UUID";

    private int maxReconnection = 2;
    private int nowReconnection = 0;
    private String nowReconnectionMac = "";



    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @SuppressLint("MissingPermission")
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            String intentAction;
            L.e("onConnectionStateChange"+gatt.getDevice().getAddress()+"   status:"+status+"    newState:"+newState);
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                nowReconnection = 0;
                nowReconnectionMac="";
                intentAction = ACTION_GATT_CONNECTED;

                boolean addFlag = false;
                for (int i = 0; i < btArrayList.size(); i++) {
                    if (btArrayList.get(i).getMac().equals(gatt.getDevice().getAddress())) {
                        btArrayList.get(i).setState(1);
                        addFlag = true;
                        break;
                    }
                }
                if (!addFlag) {
                    BTforServiceBean bTforServiceBean = new BTforServiceBean();
                    bTforServiceBean.setState(1);
                    bTforServiceBean.setGatt(gatt);
                    bTforServiceBean.setName(gatt.getDevice().getName());
                    bTforServiceBean.setMac(gatt.getDevice().getAddress());
                    btArrayList.add(bTforServiceBean);
                }
                broadcastUpdate(intentAction, gatt.getDevice().getAddress(), gatt.getDevice().getName());
                Log.i(TAG, "Connected to GATT server.");
                // Attempts to discover services after successful connection.
                Log.i(TAG, "Attempting to start service discovery:" +
                        gatt.discoverServices());

            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {

                if(status!=0){
                    //  重连
                    boolean stateFlag = false;//是否正在重连
                    for (int i = 0; i < btArrayList.size(); i++) {
                        if (btArrayList.get(i).getMac().equals(gatt.getDevice().getAddress())) {
                            if(btArrayList.get(i).getState()==2){
                                //正在连接中，重连
                                stateFlag = true;
                                break;
                            }
                        }
                    }
                    if((nowReconnection<maxReconnection) && stateFlag){
                        nowReconnection ++;
                        L.e("onConnectionStateChange第"+nowReconnection+"次重连"+gatt.getDevice().getAddress());
                        nowReconnectionMac = gatt.getDevice().getAddress();
                        L.e(gatt.getDevice().getName()+"    "+nowReconnectionMac);
                        gatt.close();
                        for (int i = 0; i < btArrayList.size(); i++) {
                            if (btArrayList.get(i).getMac().equals(gatt.getDevice().getAddress())) {
                                btArrayList.get(i).setState(0);
                                btArrayList.get(i).setGatt(null);
                                btArrayList.remove(i);
                                break;
                            }
                        }
                        handler.removeMessages(100);
                        handler.sendEmptyMessageDelayed(100,5*1000);
                        return ;
                    }else{
                        //连接超过3次
                        nowReconnection = 0;
                        nowReconnectionMac="";
                        L.e("onConnectionStateChange超过3次或不需要重连"+gatt.getDevice().getAddress());
                    }
                }
                intentAction = ACTION_GATT_DISCONNECTED;
                for (int i = 0; i < btArrayList.size(); i++) {
                    if (btArrayList.get(i).getMac().equals(gatt.getDevice().getAddress())) {
                        btArrayList.get(i).setState(0);
                        btArrayList.get(i).getGatt().disconnect();
                        btArrayList.get(i).getGatt().close();
                        btArrayList.get(i).setGatt(null);
                        btArrayList.remove(i);
                        break;
                    }
                }
                L.e("连接失败");
                Log.i(TAG, "Disconnected from GATT server.");
                broadcastUpdate(intentAction, gatt.getDevice().getAddress(), gatt.getDevice().getName());
            }
        }

        @SuppressLint("MissingPermission")
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
                        btArrayList.get(i).setState(1);
                        number = i;
                        addFlag = true;
                        break;
                    }
                }
                if (!addFlag) {
                    BTforServiceBean bTforServiceBean = new BTforServiceBean();
                    bTforServiceBean.setState(1);
                    bTforServiceBean.setGatt(gatt);
                    bTforServiceBean.setName(gatt.getDevice().getName());
                    bTforServiceBean.setMac(gatt.getDevice().getAddress());
                    btArrayList.add(bTforServiceBean);
                    number = btArrayList.size() - 1;
                }


                if (mGattCharacteristics != null) {
                    L.e("mGattCharacteristics:"+mGattCharacteristics.size());
                    BluetoothGattCharacteristic characteristic = null;
                    boolean flag = false;
                    for (int i = 0; i < mGattCharacteristics.size() && !flag; i++) {
                        L.e("mGattCharacteristics(i):"+mGattCharacteristics.get(i).size());
                        for (int k = 0; k < mGattCharacteristics.get(i).size(); k++) {
                            characteristic = mGattCharacteristics.get(i).get(k);
                            L.e(characteristic.getUuid().toString());
                            if (SampleGattAttributes.READ.equals(characteristic.getUuid()) || SampleGattAttributes.READ2.equals(characteristic.getUuid())) {
                                flag = true;
                                L.e("找到读ID");
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
                                L.e("找到写ID");
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

    private void broadcastUpdate(final String action, String mac, String name) {
        final Intent intent = new Intent(action);
        intent.putExtra("MAC", mac);
        intent.putExtra("NAME", name);
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

            String dataString = stringBuilder.toString();
            byte[] dataBytes = StringAndByteUtils.stringToBytes(dataString);
            //收到的全部数据
            intent.putExtra(ALL_DATA, dataString);
            intent.putExtra("MAC", mac);
            Log.w(TAG, stringBuilder.toString());
            sendBroadcast(intent);
            //按协议收到的
//            if((dataBytes[0]&0XFF)==0X3B){
//                int dataLength =  dataBytes[1]&0XFF+(dataBytes[2]&0XFF);
//                if(dataString.length()>(dataLength*2)){//有2包数据（粘包）
//                    L.e("有2包数据（粘包）");
//                    String data1 = dataString.substring(0,dataLength*2);
//                    String data2 = dataString.substring(dataLength*2);
//                    intent.putExtra(EXTRA_DATA, data1);
//                    intent.putExtra("MAC", mac);
//                    Log.w(TAG, stringBuilder.toString());
//                    sendBroadcast(intent);
//                    intent.putExtra(EXTRA_DATA, data2);
//                    intent.putExtra("MAC", mac);
//                    Log.w(TAG, stringBuilder.toString());
//                    sendBroadcast(intent);
//                }else{
//                    //只有一包
//                    L.e("只有一包");
//                    intent.putExtra(EXTRA_DATA, stringBuilder.toString());
//                    intent.putExtra("MAC", mac);
//                    Log.w(TAG, stringBuilder.toString());
//                    sendBroadcast(intent);
//                }
//            }
        }
    }


    @SuppressLint("MissingPermission")
    public boolean connect(final String address) {

        int linkNumber = 0;
        for (int i = 0; i < btArrayList.size(); i++) {
            if (btArrayList.get(i).getState() == 1) {
                linkNumber++;
            }
        }

        if (linkNumber > 4) {
            final Intent intent = new Intent("LINK_EXCESS_FOUR");
            sendBroadcast(intent);

            return false;
        }


        if (mBluetoothAdapter == null || address == null) {
            Log.e(TAG, "BluetoothAdapter not initialized or unspecified address.");
            return false;
        }

        final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        if (device == null) {
            Log.e(TAG, "Device not found.  Unable to connect.");
            return false;
        }

        int number = 0;
        boolean addFlag = false;
        for (int i = 0; i < btArrayList.size(); i++) {
            if (btArrayList.get(i).getMac().equals(address)) {
                btArrayList.get(i).setState(2);
                number = i;
                addFlag = true;
                break;
            }
        }
        if (!addFlag) {
            BTforServiceBean bTforServiceBean = new BTforServiceBean();
            bTforServiceBean.setState(2);
            bTforServiceBean.setMac(address);
            bTforServiceBean.setGatt(null);
            btArrayList.add(bTforServiceBean);
            number = btArrayList.size() - 1;
        }
        if (btArrayList.get(number).getGatt() == null) {
            BluetoothGatt mBluetoothGatt;
            mBluetoothGatt = device.connectGatt(this, false, mGattCallback);
            btArrayList.get(number).setGatt(mBluetoothGatt);
            btArrayList.get(number).setName(device.getName());
        } else {
            if (btArrayList.get(number).getGatt().connect()) {
                btArrayList.get(number).setName(device.getName());
                return true;
            } else {
                return false;
            }
        }
        Log.e(TAG, "Trying to create a new connection.");
        return true;
    }

    public void writeCharacteristic(String mac, byte[] bytes) {
        L.e(mac + ":" + StringAndByteUtils.bytesToHexString(bytes));

        for (int i = 0; i < btArrayList.size(); i++) {
            if (btArrayList.get(i).getMac().equals(mac) && btArrayList.get(i).getState() == 1) {
                if (mBluetoothAdapter == null || btArrayList.get(i).getGatt() == null) {
                    Log.w(TAG, "BluetoothAdapter not initialized");
                    return;
                }
                boolean flag = false;
                try{
                    btArrayList.get(i).getWriteCharacteristic().setValue(bytes);
                    flag = btArrayList.get(i).getGatt().writeCharacteristic(btArrayList.get(i).getWriteCharacteristic());
                    Log.e(TAG, "           send flag:  " + flag);
                }catch (Exception e){
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
        }catch (Exception e){
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
                try{
                    L.e(btArrayList.get(i).getMac() + "_发送：" + StringAndByteUtils.bytesToHexString(bytes));
                    btArrayList.get(i).getWriteCharacteristic().setValue(bytes);
                    flag = btArrayList.get(i).getGatt().writeCharacteristic(btArrayList.get(i).getWriteCharacteristic());
                    Log.e(TAG, "           send flag:  " + flag);

                }catch (Exception e){
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
    public ArrayList<BTBean> getBTBean(){
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
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        L.e("服务解除绑定");
        // close();
        return super.onUnbind(intent);
    }

    public void removeHandler(){
        handler.removeMessages(100);
    }

    public void close(String mac) {
        handler.removeMessages(100);
        nowReconnection = 0;
        for (int i = 0; i < btArrayList.size(); i++) {
            if (btArrayList.get(i).getMac().equals(mac)) {
                if (btArrayList.get(i).getGatt() != null) {
                    btArrayList.get(i).getGatt().close();
                }
                // btArrayList.get(i).setGatt(null);
                btArrayList.remove(i);
                break;
            }
        }
    }

    public void closeAll() {
        handler.removeMessages(100);
        nowReconnection = 0;
        for (int i = 0; i < btArrayList.size(); i++) {
            if (btArrayList.get(i).getGatt() != null) {
                btArrayList.get(i).getGatt().close();
            }
            // btArrayList.get(i).setGatt(null);
        }
        btArrayList.clear();
    }


    @SuppressLint("MissingPermission")
    public boolean disconnect(String mac) {

        if (mBluetoothAdapter.getState() == BluetoothAdapter.STATE_OFF) {
            return false;
        } else {
            handler.removeMessages(100);
            nowReconnection = 0;
            for (int i = 0; i < btArrayList.size(); i++) {
                if (btArrayList.get(i).getMac().equals(mac)) {
                    if (mBluetoothAdapter == null || btArrayList.get(i).getGatt() == null) {
                        Log.w(TAG, "mBluetoothGatt=null");
                        return false;
                    }
                    btArrayList.get(i).getGatt().disconnect();
                    //  btArrayList.get(i).setState(3);
                    break;
                }
            }
            return true;
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 100:
                    L.e("开始重连："+nowReconnectionMac);
                    connect(nowReconnectionMac);
                    break;

            }
        }
    };



    private final IBinder mBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        public BTServiceForMore getService() {
            return BTServiceForMore.this;
        }
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
