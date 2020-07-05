package com.example.jobfinderclient.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.security.NoSuchAlgorithmException;;
import java.util.Map;
import java.util.Set;

public class VnPayConfiguration {
    public static final String PAY_URL  = "http://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    public static final String AMOUNT = "2900000"; //29000đ
    public static final String COMMAND = "pay";
    public static final String LOCALE = "vn";
    public static final String CURR_CODE = "VND";
    public static final String ORDER_INFO_PREFIX = "Nap tien VIP User";
    public static final String ORDER_TYPE = "130005"; //thanh toán hóa đơn
    public static final String RETURN_URL = "http://192.168.1.4:8080/mobile/order";
    public static final String TMN_CODE = "1BEM41NI"; //website code
    public static final String VERSION = "2.0.0";
    public static final String HASH_SECRECT = "MCELJAJEFGAXLIYQAFOPXRIRAHRUBCNO";
    public static final String SECURE_HASH_TYPE = "SHA256";

    public static String getQuery(Map<String, String> params) throws NoSuchAlgorithmException {
        StringBuilder hashData = new StringBuilder("");
        Set<String> keys = params.keySet();
        boolean isFirst = true;
        for(String paramName : keys){
            String value = params.get(paramName);
            if(isFirst){
                hashData.append(paramName).append("=").append(value);
                isFirst = false;
            }
            else{
                hashData.append("&").append(paramName).append("=").append(value);
            }
        }
        String encoded = DigestUtils.sha256Hex(HASH_SECRECT+hashData.toString());
        String query = hashData.toString()+"&vnp_SecureHashType="+
                SECURE_HASH_TYPE+"&vnp_SecureHash="+encoded.toUpperCase();
        return query;
    }

}
