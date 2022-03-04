package com.techparew.x_files.control.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.techparew.x_files.view.fragment.QuickSetupPreferenceLocation;
import com.techparew.x_files.view.fragment.QuickSetupPreferenceQuestions;
import com.techparew.x_files.view.fragment.QuickSetupPreferenceSexOption;

public class QuickSetupPreferenceFragmentAdapter extends FragmentPagerAdapter{

        /** Context of the app */
        private Context mContext;
        QuickSetupPreferenceSexOption.OnFragmentInteractionListener genderFragmentListener;
        QuickSetupPreferenceQuestions.OnFragmentInteractionListener questionsFragmentListener;
        QuickSetupPreferenceLocation.OnFragmentInteractionListener locationFragmentListener;
        /**
         * Create a new {@link com.techparew.x_files.control.adapters.FragmentAdapter} object.
         *
         * @param context is the context of the app
         * @param fm is the fragment manager that will keep each fragment's state in the adapter
         *           across swipes.
         */
        public QuickSetupPreferenceFragmentAdapter(Context context,
                                                   FragmentManager fm,
                                                   QuickSetupPreferenceSexOption.OnFragmentInteractionListener genderListener,
                                                   QuickSetupPreferenceQuestions.OnFragmentInteractionListener questionsListener,
                                                   QuickSetupPreferenceLocation.OnFragmentInteractionListener locationListener) {
            super(fm);

            genderFragmentListener = genderListener;
            questionsFragmentListener =questionsListener;
            locationFragmentListener = locationListener;
            mContext = context;

        }

        /**
         * Return the {@link Fragment} that should be displayed for the given page number.
         */
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return QuickSetupPreferenceSexOption.newInstance(0,genderFragmentListener);
            } else if(position==1){
                return QuickSetupPreferenceSexOption.newInstance(1,genderFragmentListener);
            } else if(position==2){
                return QuickSetupPreferenceLocation.newInstance(2,locationFragmentListener);
            }else {
                return QuickSetupPreferenceQuestions.newInstance(3,questionsFragmentListener);
            }

        }

        /**
         * Return the total number of pages.
         */
        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
}


