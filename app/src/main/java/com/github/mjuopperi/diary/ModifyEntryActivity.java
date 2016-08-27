package com.github.mjuopperi.diary;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

public class ModifyEntryActivity extends AppCompatActivity {

    private DBManager dbManager;

    private TextInputEditText dateInput;
    private TextInputEditText titleInput;
    private EditText descriptionInput;

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_entry);

        dateInput = (TextInputEditText) findViewById(R.id.date);
        titleInput = (TextInputEditText) findViewById(R.id.title);
        descriptionInput = (EditText) findViewById(R.id.description);

        dateInput.setText(getIntent().getStringExtra("date"));
        titleInput.setText(getIntent().getStringExtra("title"));
        descriptionInput.setText(getIntent().getStringExtra("description"));

        id = getIntent().getIntExtra("id", 0);

        dbManager = new DBManager(this);
        dbManager.open();
    }

    public void deleteEntry(View view) {
        dbManager.delete(id);
        Intent main = new Intent(ModifyEntryActivity.this, DiaryActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(main);
    }

    public void updateEntry(View view) {
        Date date = Util.parseDate(dateInput.getText().toString());
        if (date == null) {
            Toast.makeText(getApplicationContext(), "Invalid date", Toast.LENGTH_SHORT).show();
        } else {
            dbManager.update(id, date, titleInput.getText().toString(), descriptionInput.getText().toString());
            Intent main = new Intent(ModifyEntryActivity.this, DiaryActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(main);
        }
    }
}
