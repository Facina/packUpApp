package com.techparew.x_files.view.activity;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.techparew.x_files.control.adapters.QuickSetupPreferenceFragmentAdapter;
import com.techparew.x_files.control.NonSwipeableViewPager;
import com.techparew.x_files.R;
import com.techparew.x_files.view.fragment.QuickSetupPreferenceLocation;
import com.techparew.x_files.view.fragment.QuickSetupPreferenceQuestions;
import com.techparew.x_files.view.fragment.QuickSetupPreferenceSexOption;

public class PreferenceQuickSetup extends AppCompatActivity implements QuickSetupPreferenceSexOption.OnFragmentInteractionListener, QuickSetupPreferenceQuestions.OnFragmentInteractionListener,QuickSetupPreferenceLocation.OnFragmentInteractionListener{

    QuickSetupPreferenceFragmentAdapter fragmentAdapter;
    NonSwipeableViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference_quick_setup);

        fragmentAdapter = new QuickSetupPreferenceFragmentAdapter(this, getSupportFragmentManager(),this,this,this);

        viewPager = (NonSwipeableViewPager) findViewById(R.id.quickSetupPreferenceViewPager);
        viewPager.setAdapter(fragmentAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(viewPager, true);

    }

    @Override
    public void onFragmentInteraction(int position) {
        viewPager.setCurrentItem(position);
    }
}
