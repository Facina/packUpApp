package com.techparew.x_files.control.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.techparew.x_files.control.WebServiceUtil;
import com.techparew.x_files.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FetchQuestions extends Service {

    private boolean isRunning;
    private Thread backgroundThread;
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
                .build();

        Request request = new Request.Builder()
                .url(webServiceUtil.getPreferenceQuestions())
                .post(body)
                .build();

        Response response = client.newCall(request).execute();

        if (response.isSuccessful()){
            saveResponse(response);
        }else{
            Toast.makeText(this,"NETWORK ERROR",Toast.LENGTH_SHORT);
        }
    }

    public void saveResponse (Response response) {
        SharedPreferences.Editor sharedPreferences = getSharedPreferences(getResources().getString(R.string.publicInformation), Context.MODE_PRIVATE).edit();

        try {
            assert response.body() != null;
            String responseText = response.body().string();
            if (responseText != null && !responseText.equals("") && !response.equals("null")) {
                JSONArray js = new JSONArray(responseText);
                for(int i=0;i<js.length();i++) {
                    JSONObject object = js.getJSONObject(i);
                    sharedPreferences.putInt(getResources().getString(R.string.numberOfPreferenceQuestions),js.length());
                    if(object != null){
                        int idPreferenceQuestion = object.getInt(getResources().getString(R.string.tagIdPreferenceQuestion));
                        int questionType = object.getInt(getResources().getString(R.string.tagQuestionType));
                        String question = object.getString(getResources().getString(R.string.tagQuestion));
                        sharedPreferences.putInt(getResources().getString(R.string.tagIdPreferenceQuestion)+i,idPreferenceQuestion);
                        sharedPreferences.putInt(getResources().getString(R.string.tagQuestionType)+i,questionType);
                        sharedPreferences.putString(getResources().getString(R.string.tagQuestion)+i,question);

                    }
                }
                sharedPreferences.apply();
            }
        } catch (IOException e) {
            Log.e("RESPONSE EXCEPTION", e.getMessage());
        }catch (JSONException e){
            Log.e("RESPONSE EXCEPTION", e.getMessage());
        }
    }


}
