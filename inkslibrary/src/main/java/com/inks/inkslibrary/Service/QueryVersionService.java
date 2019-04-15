package com.inks.inkslibrary.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.google.gson.Gson;
import com.inks.inkslibrary.UI.LibUpActivity;
import com.inks.inkslibrary.Utils.APKVersionCodeUtils;
import com.inks.inkslibrary.Utils.L;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class QueryVersionService extends Service {

    public final static String MGS = "InksLibrary.QueryVersionService.MGS";
    //404  网络错误
    //200  发现新版本
    //201  已经是最新版本

    private  String getInfoUri ;
    private boolean isCheck = false;
    private boolean isOpenUpActivity = false;
    private Context context;
    private final IBinder mBinder = new LocalBinder();
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        L.e("QueryVersionService:onCreate");
    }

    @Override
    public IBinder onBind(Intent intent) {
        L.e("QueryVersionService:onBind");
        return mBinder;
    }


    public class LocalBinder extends Binder {
        public QueryVersionService getService() {
            return QueryVersionService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        L.e("QueryVersionService:onStartCommand");


        return super.onStartCommand(intent, flags, startId);


    }

    public void checkUpdate(String uri ,boolean isPoen){
        this.getInfoUri = uri;
        this.isOpenUpActivity = isPoen;
        if (!isCheck) {
            isCheck = true;
            getSreviceAPPversion();
        }
    }

    private void getSreviceAPPversion() {
        OkHttpClient client = new OkHttpClient();
        final Request request;
        request = new Request.Builder().get().url(getInfoUri).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                L.e("onFailure");
                    //检查网络连接
                    Intent intent = new Intent(MGS);
                    intent.putExtra("MGS",404);
                    sendBroadcast(intent);
                isCheck = false;
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.code() == 200) {
                    Gson  gs = new Gson();
                    final String responseStr = response.body().string();
                    final QueryVersionBackBean queryVersionBackBean = gs.fromJson(responseStr, QueryVersionBackBean.class);
                    if (queryVersionBackBean.getAppVersion().equals(APKVersionCodeUtils.getVerName(context))) {
                            //广播  已是最新版本
                            Intent intent = new Intent(MGS);
                            intent.putExtra("MGS",201);
                            sendBroadcast(intent);
                    } else {

                            //广播  发现新版本
                            Intent intent1 = new Intent(MGS);
                            intent1.putExtra("MGS",200);
                            sendBroadcast(intent1);
                            //直接启动activity
                        if(isOpenUpActivity){
                            Intent intent = new Intent(context,LibUpActivity.class);
                            intent.putExtra("responseStr", responseStr);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                            startActivity(intent);
                        }

                    }
                } else {
                        //检查网络连接
                        Intent intent = new Intent(MGS);
                        intent.putExtra("MGS",404);
                        sendBroadcast(intent);
                }
                isCheck = false;
            }
        });
    }
}
