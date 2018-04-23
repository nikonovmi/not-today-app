package com.oohdev.oohreminder.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;

import com.oohdev.oohreminder.R;

public class MainActivity extends AppCompatActivity implements ContentPagerAdapter.Callbacks {

    private static final String GIT_URL = "https://github.com/degivan/ifmo-android-oohreminder";
    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set a Toolbar instead of ActionBar.
        mToolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(mToolbar);

        // Set up fab and tabs
        final ContentPagerAdapter pagerAdapter = new ContentPagerAdapter(getSupportFragmentManager(), this, this);
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

        // Also required to color fab in the right color when app starts
        // (default is colorAccent)
        animateFab(mTabLayout.getSelectedTabPosition());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        openWebPage(GIT_URL);
        return true;
    }

    private void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
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

    @Override
    public void onNewFragmentAppeared(@NonNull ContentFragment contentFragment) {
        App.get().setSearchProvider(contentFragment.getSearchProvider());
    }
}
