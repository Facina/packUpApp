package com.techparew.x_files.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.techparew.x_files.control.adapters.FragmentAdapter;
import com.techparew.x_files.control.services.FetchQuestions;
import com.techparew.x_files.R;
import com.techparew.x_files.view.activity.QuickSetupActivity;
import com.techparew.x_files.view.activity.SignUpActivity;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    TabLayout tabLayout;
    FragmentAdapter fragmentAdapter;
    ViewPager viewPager;
    EditText editTextSearchBar;
    ImageView imageViewMenu;
    ImageView imageViewFilter;
    DrawerLayout drawer;

    /*
    *  Activity lifecycle
    * */


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

        if(drawer!=null)drawer.closeDrawer(GravityCompat.START);
        if(viewPager!=null)viewPager.requestFocus();
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
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout_main);

        if(checkLogIn()){
            //Display views on screen
            screenSetup(getApplicationContext());
            //Quick Setup after first login

            quickSetup();

            Intent intent = new Intent(this, FetchQuestions.class);
            startService(intent);
        }





    }






    // Creating viewPager instance and Tab Layout
    void screenSetup(Context context){



        drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer,null , 0, 0);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        imageViewMenu = (ImageView) findViewById(R.id.imageViewSearchMenu);
        imageViewFilter = (ImageView) findViewById(R.id.imageViewFilter);

        imageViewMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(drawer.isDrawerOpen(GravityCompat.START)){
                    drawer.closeDrawer(GravityCompat.START);
                }else drawer.openDrawer(GravityCompat.START);
            }
        });

        imageViewFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterClicked(view);
            }
        });

        fragmentAdapter = new FragmentAdapter(context,getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.viewPagerFragments);
        editTextSearchBar = (EditText) findViewById(R.id.editTextSearchBar);
        editTextSearchBar.clearFocus();
        viewPager.setAdapter(fragmentAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //Real Estate Page

                //Log.e("Data = ","position= "+ position+ " positionOffset= " + positionOffset+" positionOffSetPixels= "+ positionOffsetPixels);
                if(position == 0 && positionOffset==0){
                    editTextSearchBar.setHint(R.string.searchBarRealEstate);


                } else  if(position == 1 && positionOffset==0){
                    editTextSearchBar.setHint(R.string.searchBarRealEstate);


                } else if(position==2 && positionOffset==0){//Chat Page
                    editTextSearchBar.setHint(R.string.searchBarChat);

                }

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabLayout = (TabLayout) findViewById(R.id.categoryTabLayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home_black_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_star_black_24dp);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_chat_black_24dp);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if(tab.getPosition()==0){
                    int color = ContextCompat.getColor(getApplicationContext(), R.color.colorAccent);
                    tab.getIcon().setColorFilter(color, PorterDuff.Mode.SRC_IN);
                } else if(tab.getPosition()==1){

                    int color = ContextCompat.getColor(getApplicationContext(),R.color.yellow);
                    tab.getIcon().setColorFilter(color, PorterDuff.Mode.SRC_IN);
                }else {
                    int color = ContextCompat.getColor(getApplicationContext(), R.color.colorAccent);
                    tab.getIcon().setColorFilter(color, PorterDuff.Mode.SRC_IN);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int color = ContextCompat.getColor(getApplicationContext(),R.color.black);
                tab.getIcon().setColorFilter(color, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabLayout.getTabAt(0).select();
        tabLayout.getTabAt(0).getIcon().setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.colorAccent),PorterDuff.Mode.SRC_IN);



    }

    void menuClicked(View view){
        Toast.makeText(getApplicationContext(),R.string.notYetImplemented,Toast.LENGTH_LONG).show();
    }

    void filterClicked(View view){
        Toast.makeText(getApplicationContext(),R.string.notYetImplemented,Toast.LENGTH_LONG).show();

    }

    void quickSetup(){
        Intent quickSetupIntent = new Intent(this,QuickSetupActivity.class);
        //quickSetupIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(quickSetupIntent);

    }


    private boolean checkLogIn(){
        //facebook activation
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        sharedPreferences = getApplicationContext().getSharedPreferences(getResources().getString(R.string.userInformation), Context.MODE_PRIVATE);


        //check if user is already signed in
        if (!sharedPreferences.getBoolean(getResources().getString(R.string.signedIn), false)) {
            openSignUpScreen();
        }else return true;
        return false;
    }


    public void openSignUpScreen(){
        Intent intent = new Intent(this,SignUpActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
