package com.example.pillsnotify;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class PillPage extends AppCompatActivity {
    ImageView image;
    TextView tittle, start, next, amount;
    private static final String INFO = "tittleList";
    String inf, tit = "", st, ne, am;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pill_page);

        image = findViewById(R.id.imagePage);
        tittle = findViewById(R.id.tittlePage);
        start = findViewById(R.id.startPage);
        next = findViewById(R.id.nextPage);
        amount = findViewById(R.id.amountPage);

        inf = getIntent().getStringExtra(INFO);
        tittle.append(inf);
        next.setText(tit);
    }
}