package com.example.orgdon;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class ANegative extends AppCompatActivity {

    RecyclerView aNegativeView;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anegative);
        setTitle("A- Donors");

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        aNegativeView = findViewById(R.id.aNegativeView);
        aNegativeView.setLayoutManager(new LinearLayoutManager(this));

        // Filtering A- Blood Group from the List
        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("donors").orderByChild("blood").equalTo("A-"), model.class)
                        .build();

        adapter = new MyAdapter(options);
        aNegativeView.setAdapter(adapter);

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onStart() {
        super.onStart();
        aNegativeView.getRecycledViewPool().clear();
        adapter.startListening();
        aNegativeView.getRecycledViewPool().clear();
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, GroupList.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, GroupList.class);
        startActivity(intent);
    }
}