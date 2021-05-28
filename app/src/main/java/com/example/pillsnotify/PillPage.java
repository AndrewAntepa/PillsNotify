package com.example.pillsnotify;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PillPage extends AppCompatActivity {
    ImageView image;
    Button back, del;
    TextView tittle, start, next, amount;
    private static final String INFO = "tittleList";
    String inf, tit = "";
    SQLiteDatabase sdb;
    MyOpenHelper myOpenHelper;
    char s;
    String that = "";

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
        int i = 2;
        s = inf.charAt(inf.length()-i);
        while (s != '='){
            that += s;
            i++;
            s = inf.charAt(inf.length()-i);
        }
        for (int j = that.length()-1; j >= 0; j--) {
            tit += that.charAt(j);
        }

        String query = "SELECT * FROM "
                + MyOpenHelper.TABLE_NAME
                + " WHERE " + MyOpenHelper.COLUMN_TITLE
                + " = \"" + tit + "\";";
        Cursor cursor = sdb.rawQuery(query, null);

        cursor.moveToFirst();
        tittle.append(cursor.getString(cursor.getColumnIndex(MyOpenHelper.COLUMN_TITLE)));
        start.append(cursor.getString(cursor.getColumnIndex(MyOpenHelper.COLUMN_START)));
        next.append(cursor.getString(cursor.getColumnIndex(MyOpenHelper.COLUMN_INTERVAL)));
        amount.append(cursor.getString(cursor.getColumnIndex(MyOpenHelper.COLUMN_AMOUNT_TIME)));
        image.setImageResource(R.drawable.pill_example);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sdb.close();
                myOpenHelper.close();
                cursor.close();
                startActivity(new Intent(PillPage.this, MainActivity.class));
            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = "DELETE FROM " + MyOpenHelper.TABLE_NAME + " WHERE tittle = '" + tit + "';";
                sdb.execSQL(query);
                sdb.close();
                myOpenHelper.close();
                cursor.close();
                startActivity(new Intent(PillPage.this, MainActivity.class));
            }
        });
    }
}