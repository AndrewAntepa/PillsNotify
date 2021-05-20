package com.example.pillsnotify;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.HashMap;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    Button addButton, upButton;
    Intent intent;
    ListView pillsList;

    SQLiteDatabase sdb;
    MyOpenHelper myOpenHelper;
    SimpleAdapter simpleAdapter;
    LinkedList<HashMap<String, Object>> mapPills = new LinkedList<>();

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(MainActivity.this, NotifyService.class));

        addButton = findViewById(R.id.addButton);
        upButton = findViewById(R.id.up);
        pillsList = findViewById(R.id.listview);
        intent = new Intent(MainActivity.this, AddPage.class);

        myOpenHelper = new MyOpenHelper(getApplicationContext());
        sdb = myOpenHelper.getWritableDatabase();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pillsList.smoothScrollToPosition(0);
            }
        });

        readDataBase();
    }

    public void readDataBase(){
        String[] keyFrom = {"tittle", "start", "next", "amount", "image"};
        int [] idTo = {R.id.tittleExam, R.id.startExam, R.id.nextExam, R.id.amountExam, R.id.imageExam};
        String[] keyQuery = {MyOpenHelper.COLUMN_TITLE, MyOpenHelper.COLUMN_START, MyOpenHelper.COLUMN_INTERVAL, MyOpenHelper.COLUMN_AMOUNT_TIME};
        simpleAdapter = new SimpleAdapter(getApplicationContext(), mapPills, R.layout.pills_example, keyFrom, idTo);
        pillsList.setAdapter(simpleAdapter);
        Cursor cursor = sdb.query(MyOpenHelper.TABLE_NAME, keyQuery, null, null, null, null, null);

        while (cursor.moveToNext()) {
            String tittle = cursor.getString(cursor.getColumnIndex(MyOpenHelper.COLUMN_TITLE));
            String start = cursor.getString(cursor.getColumnIndex(MyOpenHelper.COLUMN_START));
            String next = cursor.getString(cursor.getColumnIndex(MyOpenHelper.COLUMN_INTERVAL));
            String amount = cursor.getString(cursor.getColumnIndex(MyOpenHelper.COLUMN_AMOUNT_TIME));

            HashMap<String, Object> map = new HashMap<>();
            map.put("tittle", tittle);
            map.put("start", start);
            map.put("next", "через " + next + " час(ов)");
            map.put("amount", amount);
            map.put("image", R.drawable.pill_example);
            mapPills.add(map);
        }
        simpleAdapter.notifyDataSetChanged();
        cursor.close();
        myOpenHelper.close();
        sdb.close();
    }
}