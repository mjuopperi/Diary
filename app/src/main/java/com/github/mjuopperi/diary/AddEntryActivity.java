package com.github.mjuopperi.diary;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class AddEntryActivity extends AppCompatActivity {

    private DBManager dbManager;

    private TextInputEditText dateInput;
    private TextInputEditText titleInput;
    private EditText descriptionInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        dateInput = (TextInputEditText) findViewById(R.id.date);
        titleInput = (TextInputEditText) findViewById(R.id.title);
        descriptionInput = (EditText) findViewById(R.id.description);
        dateInput.setText(Util.formatDate(new Date()));

        dbManager = new DBManager(this);
        dbManager.open();
    }

    public void setDate(View view) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        final TextInputEditText dateField = (TextInputEditText) findViewById(R.id.date);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        dateField.setText(year + "-" + (month + 1) + "-" + day);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void saveEntry(View view) {
        Date date = Util.parseDate(dateInput.getText().toString());
        if (date == null) {
            Toast.makeText(getApplicationContext(), "Invalid date", Toast.LENGTH_SHORT).show();
        } else {
            dbManager.insert(date, titleInput.getText().toString(), descriptionInput.getText().toString());
            Intent main = new Intent(AddEntryActivity.this, DiaryActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(main);
        }
    }
}
