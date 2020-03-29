package com.alrshididev.pigapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
public class Main2Activity extends AppCompatActivity {
    Fragment fragment;
    ActionBar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        toolbar = getSupportActionBar();
        // load the store fragment by default

        fragment = new MenuRestourantFragment();
        loadFragment(fragment);

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav);
       bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }




    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.address:

                    fragment = new AddressFragment();

                    loadFragment(fragment);


                    return true;
                case R.id.order_list:

                    fragment = new OrderItemsFragment();
                    loadFragment(fragment);


                    return true;
                case R.id.menu_restorant:

                    fragment = new MenuRestourantFragment() ;
                    loadFragment(fragment);


                    return true;
                case R.id.logout:
                    fragment = new LogoutFragment() ;
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };




    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.framelayout, fragment);
        transaction.addToBackStack(null).commit();




    }

}

