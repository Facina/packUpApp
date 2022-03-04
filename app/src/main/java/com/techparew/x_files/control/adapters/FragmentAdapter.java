package com.techparew.x_files.control.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.techparew.x_files.view.fragment.ChatFragment;
import com.techparew.x_files.view.fragment.FavoriteFragment;
import com.techparew.x_files.view.fragment.RealEstateFragment;

public class FragmentAdapter extends FragmentPagerAdapter {

    /** Context of the app */
    private Context mContext;

    /**
     * Create a new {@link FragmentAdapter} object.
     *
     * @param context is the context of the app
     * @param fm is the fragment manager that will keep each fragment's state in the adapter
     *           across swipes.
     */
    public FragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;

    }

    /**
     * Return the {@link Fragment} that should be displayed for the given page number.
     */
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return RealEstateFragment.newInstance(0);
        } else if(position==1){
            return FavoriteFragment.newInstance(1);
        } else {
            return ChatFragment.newInstance(2);
        }

    }

    /**
     * Return the total number of pages.
     */
    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }
}
