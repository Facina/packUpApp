package com.techparew.x_files.control;


import android.content.Context;

import com.techparew.x_files.R;

import java.net.URL;

import okhttp3.HttpUrl;


/*
 * Class to store the web address to services
 */

public class WebServiceUtil {
    Context context;

    public WebServiceUtil (Context context){
        this.context = context;
    }


    public URL getSignUpURL(){
        return HttpUrl.parse(context.getString(R.string.sign_up_url)).url();
    }

    public URL getLoginURL(){
        return HttpUrl.parse(context.getString(R.string.login_url)).url();
    }

    public URL getPreferenceQuestions(){
        return HttpUrl.parse(context.getString(R.string.preference_questions)).url();
    }


}