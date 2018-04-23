package com.oohdev.oohreminder.ui;


import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.oohdev.oohreminder.R;

import junit.framework.Assert;

class ContentPagerAdapter extends FragmentPagerAdapter {
    private final static int NUMBER_OF_TABS = 3;
    private final String[] mTabNames;
    private final Callbacks mCallbacks;
    private ContentFragment mCurrentFragment;

    public ContentPagerAdapter(FragmentManager fm, Context context, Callbacks callbacks) {
        super(fm);
        Resources res = context.getResources();
        mTabNames = res.getStringArray(R.array.navigation_tabs);
        mCallbacks = callbacks;
        Assert.assertEquals(NUMBER_OF_TABS, mTabNames.length);
    }

    ContentFragment getCurrentContentFragment() {
        return mCurrentFragment;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (getCurrentContentFragment() != object) {
            mCurrentFragment = (ContentFragment) object;
        }
        super.setPrimaryItem(container, position, object);
        mCallbacks.onNewFragmentAppeared(mCurrentFragment);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return MoviesFragment.newInstance();
            case 1:
                return BooksFragment.newInstance();
            case 2:
                return MusicFragment.newInstance();
            default:
                Assert.fail("Invalid item position in MainActivityPagerAdapter");
        }
        return null;
    }

    @Override
    public int getCount() {
        return NUMBER_OF_TABS;
    }

    @Override
    @NonNull
    public CharSequence getPageTitle(int position) {
        return mTabNames[position];
    }

    interface Callbacks {
        void onNewFragmentAppeared(@NonNull ContentFragment contentFragment);
    }
}
