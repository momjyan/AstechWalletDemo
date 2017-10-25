package com.example.sean.registrationactivity_lesson15.App;

import android.app.Application;
import android.net.SSLCertificateSocketFactory;
import android.net.SSLSessionCache;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.lang.ref.ReferenceQueue;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by Sean on 8/18/2017.
 */

public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();

    private RequestQueue requestQueue;
    private static AppController instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static synchronized AppController getInstance() {
        return instance;
    }


    private RequestQueue mRequestQueue;
    private HurlStack mStack;

    public RequestQueue getRequestQueue(){

        SSLSocketFactoryExtended factory = null;

        try {
            factory = new SSLSocketFactoryExtended();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }


        final SSLSocketFactoryExtended finalFactory = factory;

        mStack = new HurlStack() {
            @Override
            protected HttpURLConnection createConnection(URL url) throws IOException {
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) super.createConnection(url);
                try {
                    httpsURLConnection.setSSLSocketFactory(finalFactory);
                    httpsURLConnection.setRequestProperty("charset", "utf-8");

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return httpsURLConnection;
            }

        };


        if(requestQueue==null){
            requestQueue = Volley.newRequestQueue(getApplicationContext(),mStack);
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request request,String tag){
        request.setTag(TextUtils.isEmpty(tag)?TAG:tag);
        getRequestQueue().add(request);
    }


    public <T> void addToRequestQueue(Request request){
        request.setTag(TAG);
        getRequestQueue().add(request);
    }
}

