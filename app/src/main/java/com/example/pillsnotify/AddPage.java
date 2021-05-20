package com.example.pillsnotify;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Calendar;
import java.util.TimeZone;

public class AddPage extends AppCompatActivity {
    Button add;
    EditText editText;
    DatePicker datePicker;
    Spinner interval, dose;
    TextView hour;

    MyOpenHelper myOpenHelper;
    SQLiteDatabase sdb;


    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_page);

        add = findViewById(R.id.addPill);
        editText = findViewById(R.id.editTittle);
        datePicker = findViewById(R.id.datePick);
        interval = findViewById(R.id.intervalSpinner);
        dose = findViewById(R.id.doseSpinner);
        hour = findViewById(R.id.hour);

        myOpenHelper = new MyOpenHelper(this);
        sdb = myOpenHelper.getWritableDatabase();

        interval.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position+1 == 1) hour.setText("час");
                else if(position+1 > 1 && position+1 < 5) hour.setText("часа");
                else if(position+1 > 4 && position+1 < 10) hour.setText("часов");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                hour.setText("час");
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = editText.getText().toString();
                if(!TextUtils.isEmpty(s)) {
                    String date = datePicker.getDayOfMonth() + "." + datePicker.getMonth() + "." + datePicker.getYear();

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(MyOpenHelper.COLUMN_TITLE, editText.getText().toString());
                    contentValues.put(MyOpenHelper.COLUMN_START, date);
                    contentValues.put(MyOpenHelper.COLUMN_INTERVAL, interval.getSelectedItem().toString());
                    contentValues.put(MyOpenHelper.COLUMN_AMOUNT_TIME, dose.getSelectedItem().toString());
                    sdb.insert(MyOpenHelper.TABLE_NAME, null, contentValues);
                    myOpenHelper.close();
                    sdb.close();
                    startActivity(new Intent(AddPage.this, MainActivity.class));
                } else Toast.makeText(getApplicationContext(), "Заполните поле названия лекарства", Toast.LENGTH_LONG).show();
            }
        });
    }
}