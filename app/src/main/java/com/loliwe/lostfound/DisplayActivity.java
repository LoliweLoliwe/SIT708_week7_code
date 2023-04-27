package com.loliwe.lostfound;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;

import java.util.ArrayList;

public class DisplayActivity extends AppCompatActivity {

    EditText editText;
    Button btn1, btn2;
    TextView textVw;
    Toolbar toolbar;
    ListView listView;
    ArrayList<String> listItems;
    ArrayAdapter adapter;
    DbManager obj = null;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        obj = new DbManager(DisplayActivity.this);

        btn1 = findViewById(R.id.btn_update);
        btn2 = findViewById(R.id.btn_delete);

        listItems = new ArrayList<>();

        listView = findViewById(R.id.listvw2);
        toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);

        viewData();

        btn1 = findViewById(R.id.btn_back);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                String text = listView.getItemAtPosition(i).toString();

                Bundle bundle = new Bundle();
                bundle.putString("TYPE_KEY", text);
                Intent newIntent = new Intent(DisplayActivity.this, LastActivity.class);
                newIntent.putExtras(bundle);
                startActivity(newIntent);
            }
        });
    }

    private void viewData() {
        Cursor cursor = obj.viewData();

        if (cursor.getCount() == 0) {
            Toast.makeText(DisplayActivity.this, "No items to show", Toast.LENGTH_LONG).show();
        } else {
            while (cursor.moveToNext()) {
                listItems.add("[" +cursor.getString(0)+ "] " + cursor.getString(1) + " : " + cursor.getString(4) + " On " + cursor.getString(5) + ". Contact: " + cursor.getString(2) + " @ " + cursor.getString(3));
            }

            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItems);
            listView.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        MenuItem menuItem = menu.findItem(R.id.t_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setQueryHint("Type here to search....");
        //searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //    Toast.makeText(SearchActivity.this, "Not found", Toast.LENGTH_LONG).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}