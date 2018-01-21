package com.example.fishg.d20chargen;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity
        implements
            MainFragment.OnFragmentInteractionListener,
            SkillFragment.OnFragmentInteractionListener,
            EquipmentFragment.OnFragmentInteractionListener,
            NavigationView.OnNavigationItemSelectedListener,
            ViewPager.OnPageChangeListener {

    Toolbar toolbar;
    MyAdapter mAdapter;
    ViewPager mPager;
    NavigationView navigationView;
    SparseIntArray fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);

        populatePageFragments();

        mAdapter = new MyAdapter(getSupportFragmentManager());

        mPager = findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        mPager.addOnPageChangeListener(this);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Checks first item in the navigation drawer initially
        navigationView.setCheckedItem(R.id.nav_main);
    }

    private void populatePageFragments() {
        fragments = new SparseIntArray();
        int position = 0;

        fragments.put(position++, R.id.nav_main);
        fragments.put(position++, R.id.nav_skill);
        fragments.put(position++, R.id.nav_equipment);
        //fragments.put(position++, R.id.nav_spells);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (mPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            int prevPage = mPager.getCurrentItem() - 1;
            mPager.setCurrentItem(prevPage);
            navigationView.setCheckedItem(getIdByPage(prevPage));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int page = getPageById(item.getItemId());

        mPager.setCurrentItem(page);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(String title) {

        getSupportActionBar().setTitle(title);
    }

    private int getPageById(int id) {
        int page = 0;
        if (fragments.indexOfValue(id) != -1) {
            page = fragments.indexOfValue(id);
        }

        return page;
    }

    private int getIdByPage(int page) {
        return fragments.get(page);
    }

    @Override
    public void onPageSelected(int position) {
        navigationView.setCheckedItem(getIdByPage(position));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    public static class MyAdapter extends FragmentPagerAdapter {

        private static int PAGE_COUNT = 3;

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {

            return PAGE_COUNT;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;

            switch(position) {
                case 0: fragment = new MainFragment(); break;
                case 1: fragment = new SkillFragment(); break;
                case 2: fragment = new EquipmentFragment(); break;
                //case 3: fragment = new SpellsFragment(); break;
            }

            return fragment;
        }
    }
}
