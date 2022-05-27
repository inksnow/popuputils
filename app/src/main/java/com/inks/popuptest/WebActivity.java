package com.inks.popuptest;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.inks.inkslibrary.Utils.L;

public class WebActivity extends AppCompatActivity {
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

         webView = findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);

        String url = "file:///android_asset/dist/index.html";

        String url2 ="https://www.baidu.com/";

        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                L.e("shouldOverrideUrlLoading:" + url);
                try {
                    super.shouldOverrideUrlLoading(view, url);
                } catch (Exception e) {
                    Log.e("xxxx", e.getMessage());
                    return false;
                }
                String scheme = Uri.parse(url).getScheme();
                if (scheme != null) {
                    scheme = scheme.toLowerCase();
                }
                if ("http".equalsIgnoreCase(scheme) || "https".equalsIgnoreCase(scheme)) {
                    webView.loadUrl(url);
                    L.e("loadUrl:" + url);
                }
                // 已经处理该链接请求

                return true;
            }
        });
    }


    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        }else{
            super.onBackPressed();

        }
    }
}