package com.example.onn.activity;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.onn.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Token extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String assembleToken(String version, String resourceName, String expirationTime, String signatureMethod, String accessKey)
            throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        StringBuilder sb = new StringBuilder();
        String res = URLEncoder.encode(resourceName, "UTF-8");
        String sig = URLEncoder.encode(generatorSignature(version, resourceName, expirationTime
                , accessKey, signatureMethod), "UTF-8");
        sb.append("version=")
                .append(version)
                .append("&res=")
                .append(res)
                .append("&et=")
                .append(expirationTime)
                .append("&method=")
                .append(signatureMethod)
                .append("&sign=")
                .append(sig);
        return sb.toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String generatorSignature(String version, String resourceName, String expirationTime, String accessKey, String signatureMethod)
            throws NoSuchAlgorithmException, InvalidKeyException {
        String encryptText = expirationTime + "\n" + signatureMethod + "\n" + resourceName + "\n" + version;
        String signature;
        byte[] bytes = HmacEncrypt(encryptText, accessKey, signatureMethod);
        signature = Base64.getEncoder().encodeToString(bytes);
        return signature;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static byte[] HmacEncrypt(String data, String key, String signatureMethod)
            throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec signinKey = null;
        signinKey = new SecretKeySpec(Base64.getDecoder().decode(key),
                "Hmac" + signatureMethod.toUpperCase());


        Mac mac = null;
        mac = Mac.getInstance("Hmac" + signatureMethod.toUpperCase());


        mac.init(signinKey);


        return mac.doFinal(data.getBytes());
    }

    public enum SignatureMethod {
        SHA1, MD5, SHA256;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public String calToken() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {


//        String version = getData().getString("version");
//        String resourceName = getData().getString("resourceName");
//        String expirationTime = System.currentTimeMillis() / 1000 + 100 * 24 * 60 * 60 + "";
//        String signatureMethod = SignatureMethod.SHA1.name().toLowerCase();
//        String accessKey = getData().getString("accessKey");
        String version = "2018-10-31";
        String resourceName = "products/你的设备号码";
        String expirationTime = System.currentTimeMillis() / 1000 + 100 * 24 * 60 * 60 + "";
        String signatureMethod = SignatureMethod.SHA1.name().toLowerCase();
        String accessKey = "你的access key";
        String token = assembleToken(version, resourceName, expirationTime, signatureMethod, accessKey);
        return token;
    }
}
