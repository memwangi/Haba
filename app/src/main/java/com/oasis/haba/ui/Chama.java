package com.oasis.haba.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.oasis.haba.R;

public class Chama extends AppCompatActivity {

    private TextView mtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chama);
        mtitle = findViewById(R.id.title);

//        Intent intent = getIntent();
//        //get the data
//        Bundle extras = intent.getExtras();
//        String name_chama = extras.getString("CHAMA_NAME");
//
//        mtitle = findViewById(R.id.title);
//        mtitle.setText(name_chama);




    }
}
