package com.oohdev.oohreminder;

import android.content.Context;
import android.content.res.Resources;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;

import com.oohdev.oohreminder.books.BooksFragment;
import com.oohdev.oohreminder.movies.MoviesFragment;
import com.oohdev.oohreminder.music.MusicFragment;

import junit.framework.Assert;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set a Toolbar instead of ActionBar.
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        // Set up fab and tabs
        final MainActivityPagerAdapter pagerAdapter = new MainActivityPagerAdapter(getSupportFragmentManager(), this);
        mFab = findViewById(R.id.fab);
        mViewPager = findViewById(R.id.viewpager);
        mTabLayout = findViewById(R.id.tabs);
        mViewPager.setAdapter(pagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
                animateFab(tab.getPosition());
            }
        });
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pagerAdapter.getCurrentContentFragment().addElement();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        animateFab(mTabLayout.getSelectedTabPosition());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //TODO
        return true;
    }

    private void animateFab(final int position) {
        final int[] colorIntArray = {R.color.colorFabMovies, R.color.colorFabBooks, R.color.colorFabMusic};
        mFab.clearAnimation();
        ScaleAnimation shrink =  new ScaleAnimation(1f, 0.2f, 1f, 0.2f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        shrink.setDuration(150);     // animation duration in milliseconds
        shrink.setInterpolator(new DecelerateInterpolator());
        shrink.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                mFab.setBackgroundTintList(getResources().getColorStateList(colorIntArray[position]));
                // Scale up animation
                ScaleAnimation expand =  new ScaleAnimation(0.2f, 1f, 0.2f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                expand.setDuration(100);     // animation duration in milliseconds
                expand.setInterpolator(new AccelerateInterpolator());
                mFab.startAnimation(expand);
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });
        mFab.startAnimation(shrink);
    }

    /**
     *  Only ContentFragments allowed to be used in this PageAdapter.
     */
    private static class MainActivityPagerAdapter extends FragmentPagerAdapter {
        private final String[] mTabNames;
        private ContentFragment mCurrentFragment;

        public MainActivityPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            Resources res = context.getResources();
            mTabNames = res.getStringArray(R.array.navigation_tabs);
        }

        public ContentFragment getCurrentContentFragment() {
            return mCurrentFragment;
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            if (getCurrentContentFragment() != object) {
                mCurrentFragment = (ContentFragment) object;
            }
            super.setPrimaryItem(container, position, object);
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
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabNames[position];
        }
    }

}
