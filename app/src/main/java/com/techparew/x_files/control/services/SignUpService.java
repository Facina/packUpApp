package com.techparew.x_files.control.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.techparew.x_files.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import com.techparew.x_files.control.WebServiceUtil;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignUpService extends Service {

    private boolean isRunning;
    private Thread backgroundThread;
    String email,name,imageURL,accountType,password;
    WebServiceUtil webServiceUtil;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        this.isRunning = false;
        this.backgroundThread = new Thread(myTask);
        webServiceUtil = new WebServiceUtil(this);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle args = intent.getBundleExtra(getResources().getString(R.string.intentExtra));

        email = args.get(getResources().getString(R.string.userEmail)) + "";
        name =  args.get(getResources().getString(R.string.userName)) + "";
        imageURL = args.get(getResources().getString(R.string.imageURL)) + "";
        password = args.get(getResources().getString(R.string.passwordAccount)) + "";
        accountType = args.get(getResources().getString(R.string.accountType)) + "";

        if(!this.isRunning) {
            this.isRunning = true;
            this.backgroundThread.start();
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        this.isRunning = false;
    }

    private Runnable myTask = new Runnable() {
        public void run() {
            try {
                login();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    public void login() throws IOException{
       // get and save id in shared preferences
        OkHttpClient client = new OkHttpClient();

        RequestBody body = new FormBody.Builder()
                .add(getResources().getString(R.string.userEmail), "" + email)
                .add(getResources().getString(R.string.userName), "" + name)
                .add(getResources().getString(R.string.imageURL), "" + imageURL)
                .add(getResources().getString(R.string.passwordAccount), "" + password)
                .add(getResources().getString(R.string.accountType), "" + accountType)
                .build();

        Request request = new Request.Builder()
                .url(webServiceUtil.getSignUpURL())
                .post(body)
                .build();

        Response response = client.newCall(request).execute();


        if (response.isSuccessful()){
            saveResponse(response);
            stopSelf();
        }else{
            Toast.makeText(this,"NETWORK ERROR",Toast.LENGTH_SHORT);
        }
    }

    public void saveResponse (Response response) {
        SharedPreferences.Editor sharedPreferences = getSharedPreferences(getResources().getString(R.string.userInformation),Context.MODE_PRIVATE).edit();


        try {
            String responseText = response.body().string();
            if (responseText != null && !responseText.equals("") && !response.equals("null")) {
                JSONArray js = new JSONArray(responseText);
                if (js.length() > 0) {
                    JSONObject object = js.getJSONObject(0);
                    if(object != null){
                        int idUser = object.getInt(getResources().getString(R.string.id_user));
                        int idAccount = object.getInt(getResources().getString(R.string.id_account));
                        sharedPreferences.putInt(getResources().getString(R.string.idUser),idUser)
                                .putInt(getResources().getString(R.string.idAccount),idAccount)
                                .apply();
                    }
                }
            }
        } catch (IOException e) {
            Log.e("RESPONSE EXCEPTION", e.getMessage());
        }catch (JSONException e){
        Log.e("RESPONSE EXCEPTION", e.getMessage());
        }
    }


}
