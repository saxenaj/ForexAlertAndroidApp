package com.forexalert.gcm.forexalert;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by SaxenaJ on 12/19/2015.
 */

public class RegistrationIntentService extends IntentService {

    private static final String TAG = "RegIntentService";
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        try
        {
            InstanceID instanceID = InstanceID.getInstance(getApplicationContext());

            String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            System.out.println("Token Generated Jatin-->>" + token);
            sharedPreferences.edit().putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, true).apply();

            String rate="";
            Bundle extras = intent.getExtras();
            if(extras != null) {
                rate = extras.getString("forexalert");
            }


            if(!(rate.isEmpty())  &&  sharedPreferences.getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER,false) )
            {
                System.out.println("Token finally-->>" + token);
                System.out.println("Forex finally-->>" + rate);
               if(sendRegistrationToServer(token,rate))
               {
                   //Toast.makeText(this, "Alert set for rate " + rate
                     //      , Toast.LENGTH_SHORT).show();

               }
                else
               {
                   //Toast.makeText(this, "Error Occured " + rate
                     //      , Toast.LENGTH_SHORT).show();

               }
            }


        }
        catch (Exception e)
        {
            Log.d(TAG, "Failed to complete token refresh", e);
           sharedPreferences.edit().putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false).apply();

        }

    }

    private boolean sendRegistrationToServer(String token,String rate) {
        // Add custom implementation, as needed.

        OutputStreamWriter wr;
        BufferedReader rd;
        //String baseurl="http://10.0.2.2:8080/ForexAlertServer/RegisterRequest?token="+token+"&rate="+rate;

        String baseurl="http://mytomcatapp-forexalert.rhcloud.com/RegisterRequest?token="+token+"&rate="+rate;
        //String baseurl="http://127.0.0.1:8080/ForexAlertServer/RegisterRequest?token="+token+"&rate="+rate;
        System.out.println("Base URL-->>" + baseurl);
        StringBuffer r=new StringBuffer();
        String res="";
        boolean result=false;

        try {
            URL ur = new URL(baseurl);
            URLConnection conn = ur.openConnection();
            conn.setDoOutput(true);
            wr = new OutputStreamWriter(conn.getOutputStream());
            wr.flush();

            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            result=true;

            while ((res = rd.readLine()) != null) {
                r.append(res);
                // System.out.println(res);

            }
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

    return result;

    }
}
