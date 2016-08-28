package com.github.mjuopperi.diary;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class DiaryActivity extends AppCompatActivity {

    private DBManager dbManager;
    private ListView listView;
    private SimpleCursorAdapter adapter;


    final String[] from = new String[] { DBHelper.ID, DBHelper.DATE, DBHelper.TITLE, DBHelper.DESCRIPTION, DBHelper.CREATED_AT };

    final int[] to = new int[] { R.id.id, R.id.date, R.id.title, R.id.description, R.id.created_at };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        dbManager = new DBManager(this);
        dbManager.open();
        Cursor cursor = dbManager.fetch(DBHelper.ID);

        listView = (ListView) findViewById(R.id.entry_list);
        listView.setEmptyView(findViewById(R.id.empty));

        adapter = new SimpleCursorAdapter(this, R.layout.activity_view_entry, cursor, from, to, 0);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {
                TextView idTextView = (TextView) view.findViewById(R.id.id);
                TextView dateTextView = (TextView) view.findViewById(R.id.date);
                TextView titleTextView = (TextView) view.findViewById(R.id.title);
                TextView descriptionTextView = (TextView) view.findViewById(R.id.description);

                int id = Integer.parseInt(idTextView.getText().toString());
                String date = dateTextView.getText().toString();
                String title = titleTextView.getText().toString();
                String description = descriptionTextView.getText().toString();

                Intent modifyIntent = new Intent(getApplicationContext(), ModifyEntryActivity.class);
                modifyIntent.putExtra("id", id);
                modifyIntent.putExtra("date", date);
                modifyIntent.putExtra("title", title);
                modifyIntent.putExtra("description", description);

                startActivity(modifyIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public void addEntry(View view) {
        Intent intent = new Intent(this, AddEntryActivity.class);
        startActivity(intent);
    }

    public void sortByDate(MenuItem item) {
        adapter.swapCursor(dbManager.fetch(DBHelper.DATE));
    }

    public void sortByTitle(MenuItem item) {
        adapter.swapCursor(dbManager.fetch(DBHelper.TITLE));
    }

    public void sortByCreatedAt(MenuItem item) {
        adapter.swapCursor(dbManager.fetch(DBHelper.CREATED_AT));
    }
}
