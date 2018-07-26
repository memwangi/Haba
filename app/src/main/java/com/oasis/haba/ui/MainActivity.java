package com.oasis.haba.ui;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.oasis.haba.R;

public class MainActivity extends AppCompatActivity {

    public static FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        if(findViewById(R.id.landingPage) != null) {

            if(savedInstanceState != null){

                return;
            }

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            LoginFragment loginFragment = new LoginFragment();

            fragmentTransaction.add(R.id.landingPage,loginFragment,null);
            fragmentTransaction.commit();

        }
    }
}