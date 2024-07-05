/*A DataFlair Guide*/


package com.example.orgdon;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

/*
This part of the code is called first.
Here, we initialize and call our Front Page.
*/

public class MainActivity extends AppCompatActivity {

    Button donors, bloodgroup, organs, hospitals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //This is the layout of our Front Page.
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).hide();

        //These are the four buttons that we see in our front page.
        //Each button has it's own ID in the xml part of the program, so that
        //each button can be referred to and called easily.
        donors = findViewById(R.id.recyclerviewbtn);
        bloodgroup = findViewById(R.id.frequency);
        organs = findViewById(R.id.groupList);
        hospitals = findViewById(R.id.place);

        //On clicking the respective button we go to the respective pages.
        donors.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, DonorList.class);
            startActivity(i);
            finish();
        });

        organs.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, Organs.class);
            startActivity(i);
            finish();
        });

        bloodgroup.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, GroupList.class);
            startActivity(i);
            finish();
        });

        hospitals.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, HospitalList.class);
            startActivity(i);
            finish();
        });
    }
}