package com.oasis.haba.ui;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.oasis.haba.R;
import com.oasis.haba.ui.FormCreateChama;

public class CreateChamaActivity extends AppCompatActivity {

    public static FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createchama);

        fragmentManager = getSupportFragmentManager();

        if(findViewById(R.id.fragment_container) != null) {

            if(savedInstanceState != null){
                return;
            }

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            FormCreateChama chamaformFragment = new FormCreateChama();

            fragmentTransaction.add(R.id.fragment_container,chamaformFragment,null);
            fragmentTransaction.commit();
        }



    }
}
