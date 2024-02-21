package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
    }

    public void onCardViewButtonClick(View view) {
        Intent intent = new Intent(this, CreateTask.class);
        startActivity(intent);
    }
    public void onCardViewButtonClick2(View view) {
        setContentView(R.layout.notifications);
    }
    public void onCardViewButtonClick3(View view) {
        Intent intent = new Intent(this, ViewTask.class);
        startActivity(intent);
    }
}

