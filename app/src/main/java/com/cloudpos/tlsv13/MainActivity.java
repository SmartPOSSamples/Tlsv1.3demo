package com.cloudpos.tlsv13;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.conscrypt.Conscrypt;

import java.net.URL;
import java.security.Provider;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

public class MainActivity extends AppCompatActivity {
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvResult = findViewById(R.id.tv_result);
    }

    public void testv13(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                tlsv13("https://www.vpslala.com/");
            }
        }).start();
    }

    /**
     * url : tlsv1.3
     * https://www.google.com/
     * https://www.vpslala.com/
     * ...
     */

    /**
     * app/build.gradle/  dependencies
     * implementation 'org.conscrypt:conscrypt-android:2.5.2'
     *
     * @param url
     */
    private void tlsv13(String url) {
        setUI("TLSv1.3 testing");
        Provider conscrypt = Conscrypt.newProvider();
        try {
            SSLContext context = SSLContext.getInstance("TLSv1.3", conscrypt);
            context.init(null/*keyManagers*/, null /*new CtsTrustManager[] {trustManager}*/, null);
            SSLSocketFactory factory = context.getSocketFactory();
            URL sslURL = new URL(url);
            HttpsURLConnection con = (HttpsURLConnection) sslURL.openConnection();
            con.setSSLSocketFactory(factory);
            String responseMessage = con.getResponseMessage();
            Log.d("TAG", "responseMessage = " + responseMessage);
            setUI("TLSv1.3 test result = " + responseMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setUI(String str) {
        runOnUiThread(() -> tvResult.setText(str));
    }

}