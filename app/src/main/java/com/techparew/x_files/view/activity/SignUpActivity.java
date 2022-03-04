package com.techparew.x_files.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
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
import com.techparew.x_files.control.services.SignUpService;
import com.techparew.x_files.view.MainActivity;

public class SignUpActivity extends AppCompatActivity {

    public static int MIN_PASSWORD_LENGTH = 8;
    public static int SIGN_UP_OPTION = 0;
    public static int SIGN_IN_OPTION = 1;
    public static int GO_BACK_OPTION = 2;

    LoginButton facebookSignUpButton;
    CallbackManager callbackManager;
    Button fakeFacebookButton;
    Button fakeGoogleButton;
    Button emailSignUpButton;
    TextView signInTextView;

    LinearLayout buttonsLayout;
    LinearLayout signUpLayout;
    LinearLayout termsOfServiceLayout;
    LinearLayout guestLayout;
    LinearLayout goBackLayout;
    LinearLayout loginLayout;

    EditText nameEditText;
    EditText emailEditText;
    EditText passwordEditText;
    EditText confirmPasswordEditText;
    Button signUpButton;
    TextView errorSignUpText;

    EditText emailLoginEditText;
    EditText passwordLoginEditText;
    Button loginButton;
    TextView errorLoginText;

    TextView termsOfServiceText;

    SharedPreferences sharedPreferences;
    GoogleSignInClient googleSignInClient;
    GoogleSignInAccount googleAccount;

    ProgressBar progressBar;

    private static final int LOADER = 18;
    private LoaderManager.LoaderCallbacks<String> callbacks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity);

        initializeComponents();

        setComponentsActions();

        sharedPreferences = getSharedPreferences(getResourceString(R.string.userInformation),Context.MODE_PRIVATE);

    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() { super.onStart(); }

    @Override
    public void onBackPressed() {
        if(buttonsLayout.getVisibility() == View.GONE) {
            changeLayout(GO_BACK_OPTION);
        }else{
            super.onBackPressed();
        }
    }

    public void initializeComponents(){
        //Facebook Button
        fakeFacebookButton = findViewById(R.id.fake_facebook_button);
        facebookSignUpButton = findViewById(R.id.facebook_sign_up_button);
        setFacebookPermissions();

        //Google Button
        fakeGoogleButton = findViewById(R.id.fake_google_button);
        setGooglePermissions();

        //Email Sign Up Button
        emailSignUpButton = findViewById(R.id.mail_button);

        //Layouts
        buttonsLayout = findViewById(R.id.buttons_linear_layout);
        signUpLayout = findViewById(R.id.email_sign_up_linear_layout);
        termsOfServiceLayout = findViewById(R.id.termsOfServiceLayout);
        guestLayout = findViewById(R.id.layout_broswe_as_guest);
        goBackLayout = findViewById(R.id.layout_go_back);
        loginLayout = findViewById(R.id.login_linear_layout);

        //Email Sign Up
        nameEditText = findViewById(R.id.name_edit_text);
        emailEditText = findViewById(R.id.email_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        confirmPasswordEditText = findViewById(R.id.confirm_password_edit_text);
        errorSignUpText = findViewById(R.id.sign_up_error_text);
        signUpButton = findViewById(R.id.sign_up_button);

        //Login
        signInTextView = findViewById(R.id.sign_in_text);
        loginButton = findViewById(R.id.login_button);
        emailLoginEditText = findViewById(R.id.email_edit_text_login);
        passwordLoginEditText = findViewById(R.id.password_edit_text_login);
        errorLoginText = findViewById(R.id.login_error_text);
        progressBar = findViewById(R.id.progress_bar);

        termsOfServiceText = findViewById(R.id.termsOfServiceText);

        googleAccount = null;

    }

    //Additional Facebook Info Permission
    public void setFacebookPermissions(){
        facebookSignUpButton.setReadPermissions(getResourceString(R.string.email));
    }

    //Additional Google Permission
    public void setGooglePermissions(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    //Set Actions of Layout Components
    public void setComponentsActions(){
        //Facebook Fake Button Click
        fakeFacebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { facebookSignUpButton.performClick(); } });

        //Facebook Sign Up Button Action
        facebookSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { signUpFacebook(); } });

        //Google  Button Click
        fakeGoogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 101);
            } });

        //Email Sign Up Button Click
        emailSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { changeLayout(SIGN_UP_OPTION);} });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { signUpEmail();} });


        //Browse As Guest Click
        guestLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { browseAsGuest(); } });

        //Email Sign In Text Click
        signInTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { changeLayout(SIGN_IN_OPTION);} });

        // Email Login
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { loginEmail();} });

        //Go Back Action
        goBackLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { changeLayout(GO_BACK_OPTION);} });

        //Terms of Service
        termsOfServiceText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openTermsOfService();} });
    }


    public void signUpFacebook(){
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                sharedPreferences.edit()
                        .putBoolean(getResourceString(R.string.signedIn),true)
                        .putString(getResourceString(R.string.accountType),getResourceString(R.string.facebook))
                        .apply();

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {

                            @Override
                            public void onCompleted(final JSONObject object, GraphResponse response) {
                                try {
                                    String email = object.getString(getResourceString(R.string.email));
                                    String name = object.getString(getResourceString(R.string.name));

                                    String imageURL = "";
                                    JSONObject obj = object.getJSONObject(getResourceString(R.string.picture));
                                    if(obj != null && obj.getJSONObject(getResourceString(R.string.data)) != null) {
                                       imageURL = obj.getJSONObject(getResourceString(R.string.data))
                                                .getString(getResourceString(R.string.url));
                                    }

                                    startSignUpService(email,name,imageURL,"");
                                }catch (JSONException e){
                                    Log.e("FetchInfoError",e.getMessage());
                                }

                            }

                        });
                Bundle parameters = new Bundle();
                parameters.putString(getResourceString(R.string.facebook_fields), getResourceString(R.string.facebook_fields_list));
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                //Facebook Login Cancelled
            }

            @Override
            public void onError(FacebookException exception) {
                //Facebook Login Error
                Toast.makeText(SignUpActivity.this,"Facebook Sign In Error", Toast.LENGTH_SHORT);
                Log.e("Error",exception.getMessage());
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            //FACEBOOK
            if (callbackManager != null) {
                callbackManager.onActivityResult(requestCode, resultCode, data);
            } else {
                //GOOGLE
                if(requestCode == 101){
                    try {
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        googleAccount = task.getResult(ApiException.class);
                        signUpGoogle();
                    } catch (ApiException e) {
                        Log.w("Google Sign In Error", "signInResult:failed code=" + e.getStatusCode());
                    }
                }

            }
        }

    }



    public void signUpGoogle(){
        sharedPreferences.edit()
                .putBoolean(getResourceString(R.string.signedIn),true)
                .putString(getResourceString(R.string.accountType),getResourceString(R.string.google))
                .apply();

        String email = googleAccount.getEmail();
        String name = googleAccount.getDisplayName();
        String imageURL = "";
        Uri url = googleAccount.getPhotoUrl();

        if(url != null){
            imageURL = url.toString();
        }

        startSignUpService(email,name,imageURL,"");

        //googleSignInClient.signOut();
    }


    public void signUpEmail(){
        if(checkInformationSignUp()) {

            sharedPreferences.edit()
                    .putBoolean(getResourceString(R.string.signedIn), true)
                    .putString(getResourceString(R.string.accountType), getResourceString(R.string.emailAccount))
                    .apply();

            String name = nameEditText.getText().toString();
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            startSignUpService(email,name,"",password);

        }

    }

    public void browseAsGuest(){
        sharedPreferences.edit()
                .putBoolean(getResourceString(R.string.signedIn), true)
                .putString(getResourceString(R.string.accountType), getResourceString(R.string.guest))
                .apply();

        String name = getResourceString(R.string.guestName);
        String email = getResourceString(R.string.guestEmail);
        startSignUpService(email,name,"","");
        openNextPage(null);
    }

    public void loginEmail(){
        if(checkInformationsLogin()) {
            login();
        }
    }

    public boolean checkInformationsLogin(){
        errorLoginText.setText("");

        if(!isEmailValid(emailLoginEditText.getText().toString())){
            errorLoginText.setText(getResourceString(R.string.emailNotValid));
            return false;
        }


        if(passwordLoginEditText.getText().toString().length() < MIN_PASSWORD_LENGTH){
            errorLoginText.setText(getResourceString(R.string.invalidPassword));
            return false;
        }

        return true;
    }

    public boolean checkInformationSignUp(){
        errorSignUpText.setText("");

        if(nameEditText.getText().toString().equals("")){
            errorSignUpText.setText(getResourceString(R.string.nameEmpty));
            return false;
        }

        if(!isEmailValid(emailEditText.getText().toString())){
            errorSignUpText.setText(getResourceString(R.string.emailNotValid));
            return false;
        }

        if(!confirmPasswordEditText.getText().toString().equals(passwordEditText.getText().toString())){
            errorSignUpText.setText(getResourceString(R.string.passwordDifferent));
            return false;
        }

        if(passwordEditText.getText().toString().length() < MIN_PASSWORD_LENGTH){
            errorSignUpText.setText(getResourceString(R.string.passwordLengthError));
            return false;
        }

        return true;
    }

    public boolean isEmailValid(String email){
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public void changeLayout(int option){
        if(option == GO_BACK_OPTION){
            buttonsLayout.setVisibility(View.VISIBLE);
            termsOfServiceLayout.setVisibility(View.VISIBLE);
            signUpLayout.setVisibility(View.GONE);
            goBackLayout.setVisibility(View.GONE);
            guestLayout.setVisibility(View.VISIBLE);
            loginLayout.setVisibility(View.GONE);

        } else{
            buttonsLayout.setVisibility(View.GONE);
            termsOfServiceLayout.setVisibility(View.GONE);
            goBackLayout.setVisibility(View.VISIBLE);
            guestLayout.setVisibility(View.GONE);

            if (option == SIGN_UP_OPTION){
                signUpLayout.setVisibility(View.VISIBLE);
                loginLayout.setVisibility(View.GONE);
            }else if (option == SIGN_IN_OPTION){
                signUpLayout.setVisibility(View.GONE);
                loginLayout.setVisibility(View.VISIBLE);
            }
        }

    }

    public void openTermsOfService(){
        Toast.makeText(this,"Not Implemented Yet", Toast.LENGTH_SHORT);
    }

    public void startSignUpService(String email, String name, String imageURL, String password) {
        String accountType = sharedPreferences.getString(getResourceString(R.string.accountType),null);

        Intent intent = new Intent(SignUpActivity.this, SignUpService.class);
        Bundle args = new Bundle();
        args.putString(getResourceString(R.string.userEmail),email);
        args.putString(getResourceString(R.string.userName),name);
        args.putString(getResourceString(R.string.imageURL),imageURL);
        args.putString(getResourceString(R.string.passwordAccount),password);
        args.putString(getResourceString(R.string.accountType),accountType);
        intent.putExtra(getResourceString(R.string.intentExtra),args);
        startService(intent);
        openNextPage(googleAccount);
    }

    public void login(){
            progressBar.setVisibility(View.VISIBLE);
            loginButton.setVisibility(View.GONE);

            callbacks = new LoaderManager.LoaderCallbacks<String>() {
                @Override
                public Loader<String> onCreateLoader(int id, final Bundle args) {
                    return new AsyncTaskLoader<String>(getApplicationContext()) {

                        WebServiceUtil webServiceUtil = new WebServiceUtil(SignUpActivity.this);
                        String email = emailLoginEditText.getText().toString();
                        String password = passwordLoginEditText.getText().toString();

                        @Override
                        protected void onStartLoading(){
                            forceLoad();
                        }

                        @Override
                        public String loadInBackground() {

                            try {

                                OkHttpClient client = new OkHttpClient();

                                RequestBody body = new FormBody.Builder()
                                        .add(getResourceString(R.string.userEmail) + "",email)
                                        .add(getResourceString(R.string.passwordAccount) + "",password)
                                        .build();

                                Request request = new Request.Builder()
                                        .url(webServiceUtil.getLoginURL())
                                        .post(body)
                                        .build();


                                Response response = client.newCall(request).execute();
                                String responseString = response.body().string();
                                if (response.isSuccessful()){
                                    return responseString;
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                return null;
                            }
                            return null;
                        }
                    };
                }

                @Override
                public void onLoadFinished(Loader<String> loader, String responseString) {
                    progressBar.setVisibility(View.GONE);
                    loginButton.setVisibility(View.VISIBLE);

                    if(responseString !=null) {
                        try {
                            JSONArray jsonArray = new JSONArray(responseString);
                            if(jsonArray.length() > 0) {
                                JSONObject object = jsonArray.getJSONObject(0);
                                if (object != null) {
                                    String errorMessage = object.getString(getResourceString(R.string.errorMessage));
                                    if (errorMessage.length() > 0) {
                                        errorLoginText.setText(errorMessage);
                                    } else {
                                        int idUser = object.getInt(getResources().getString(R.string.id_user));
                                        int idAccount = object.getInt(getResources().getString(R.string.id_account));

                                        sharedPreferences.edit()
                                                .putBoolean(getResourceString(R.string.signedIn), true)
                                                .putString(getResourceString(R.string.accountType), getResourceString(R.string.emailAccount))
                                                .putInt(getResources().getString(R.string.idAccount),idAccount)
                                                .putInt(getResources().getString(R.string.idUser),idUser)
                                                .apply();
                                        loginButton.setVisibility(View.GONE);
                                        openNextPage(null);
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else{
                        Log.e("CONNECTION_FAILURE","Falha na Conexão");
                    }
                }

                @Override
                public void onLoaderReset(Loader<String> loader) {

                }
            };

            LoaderManager loaderManager = getSupportLoaderManager();
            Loader<String> loader = loaderManager.getLoader(LOADER);
            loaderManager.restartLoader(LOADER, null, callbacks).forceLoad();
        }

    String getResourceString(int string){
        return getResources().getString(string);

    }


   private void openNextPage(GoogleSignInAccount googleSignInAccount) {
        Log.e("Logged in"," sucessful");
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("googleSignInAccount", googleSignInAccount);
        startActivity(intent);
        finish();
    }

}
