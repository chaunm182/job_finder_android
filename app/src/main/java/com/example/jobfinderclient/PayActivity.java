package com.example.jobfinderclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.webkit.WebView;

import com.example.jobfinderclient.common.Constant;
import com.example.jobfinderclient.script.JavascriptInterface;
import com.example.jobfinderclient.ui.notifications.PayWebViewClient;
import com.example.jobfinderclient.util.VnPayConfiguration;

import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class PayActivity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        webView = findViewById(R.id.wvPay);

        webView.setWebViewClient(new PayWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.addJavascriptInterface(new JavascriptInterface(this),"Android");

        try {
            webView.loadUrl(getPayLink());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    private String getPayLink() throws NoSuchAlgorithmException {
        StringBuilder payURLBuilder = new StringBuilder(VnPayConfiguration.PAY_URL).append("?");
        Map<String,String> params = new LinkedHashMap<>();
        params.put("vnp_Amount",VnPayConfiguration.AMOUNT);
        params.put("vnp_Command",VnPayConfiguration.COMMAND);

        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat formater = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = formater.format(date);
        params.put("vnp_CreateDate",dateString);

        params.put("vnp_CurrCode",VnPayConfiguration.CURR_CODE);

        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
        params.put("vnp_IpAddr",ip);

        params.put("vnp_Locale",VnPayConfiguration.LOCALE);

        SharedPreferences sharedPreferences  = getSharedPreferences(Constant.SHARED_ACCOUNT_SESSION,MODE_PRIVATE);
        String username = sharedPreferences.getString("username","");
        params.put("vnp_OrderInfo",VnPayConfiguration.ORDER_INFO_PREFIX+" "+username);
        params.put("vnp_OrderType",VnPayConfiguration.ORDER_TYPE);
        params.put("vnp_ReturnUrl",VnPayConfiguration.RETURN_URL);
        params.put("vnp_TmnCode",VnPayConfiguration.TMN_CODE);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yMdHms");
        String orderId = simpleDateFormat.format(date);
        params.put("vnp_TxnRef",orderId);
        params.put("vnp_Version",VnPayConfiguration.VERSION);
        String query = VnPayConfiguration.getQuery(params);
        return payURLBuilder.append(query).toString();

    }
}