package com.example.pillsnotify;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PillPage extends AppCompatActivity {
    ImageView image;
    Button back, del;
    TextView tittle, start, next, amount;
    private static final String INFO = "tittleList";
    String inf, tit = "", st = "Начало приема ", ne = "Следующий прием ", am = "Дозировка: ", im = "";
    SQLiteDatabase sdb;
    MyOpenHelper myOpenHelper;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pill_page);

        myOpenHelper = new MyOpenHelper(getApplicationContext());
        sdb = myOpenHelper.getWritableDatabase();

        image = findViewById(R.id.imagePage);
        tittle = findViewById(R.id.tittlePage);
        start = findViewById(R.id.startPage);
        next = findViewById(R.id.nextPage);
        amount = findViewById(R.id.amountPage);
        back = findViewById(R.id.goBack);
        del = findViewById(R.id.delete);

        inf = getIntent().getStringExtra(INFO);

        for (int i = 6; i < 21; i++) {
            ne += inf.charAt(i);
        }
        next.setText(ne);

        image.setImageResource(R.drawable.pill_example);

        for (int i = 48; i < 51; i++) {
            am += inf.charAt(i);
        }
        amount.setText(am);

        for (int i = 59; i < 68; i++) {
            st += inf.charAt(i);
        }
        start.setText(st);

        for (int i = 77; i < inf.length()-1; i++) {
            tit += inf.charAt(i);
        }
        tittle.setText(tit);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PillPage.this, MainActivity.class));
            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = "DELETE FROM " + MyOpenHelper.TABLE_NAME + " WHERE tittle = '" + tit + "';";
                sdb.execSQL(query);
                startActivity(new Intent(PillPage.this, MainActivity.class));
            }
        });
    }
}