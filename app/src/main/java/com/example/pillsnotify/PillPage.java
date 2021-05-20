package com.example.pillsnotify;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.TextView;

public class PillPage extends AppCompatActivity {
    Image image;
    TextView tittle, start, next, amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pill_page);
    }
}