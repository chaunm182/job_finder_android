package com.example.jobfinderclient.ui.notifications;

import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.RequiresApi;

public class PayWebViewClient extends WebViewClient {
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        Log.d("PayWebView", "onPageStarted: "+url);
        super.onPageStarted(view, url, favicon);
    }





}
