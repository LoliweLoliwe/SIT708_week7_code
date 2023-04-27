package com.loliwe.lostfound;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.util.Calendar;

public class NewAdActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton radioButton;
    Button myButton, dateButton, chkBtn;
    String type, name, phone, descr, date, area;
    EditText textName, textPhone, textDescr, textDate, textArea;

    Connection con;
    String uname, pass, ip, port, database;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ad);

        radioGroup = findViewById(R.id.radioGroup);
        textName = (EditText) findViewById(R.id.editTextText3);
        textPhone = (EditText) findViewById(R.id.editTextPhone);
        textDescr = (EditText) findViewById(R.id.editTextTextMultiLine);
        textDate = (EditText) findViewById(R.id.editTextDate);
        textArea = (EditText) findViewById(R.id.editTextText4);

        dateButton = findViewById(R.id.button3);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == dateButton) {
                    final Calendar d = Calendar.getInstance();
                    int day = d.get(Calendar.DAY_OF_MONTH);
                    int month = d.get(Calendar.MONTH);
                    int year = d.get(Calendar.YEAR);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(
                            NewAdActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                            String select1 = Integer.toString(dayOfMonth);
                            String select2 = Integer.toString(month);
                            String select3 = Integer.toString(year);

                            textDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);

                        }
                    }, day, month, year);
                    datePickerDialog.show();
                }
            }
        });

        chkBtn = findViewById(R.id.button2);
        chkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent newIntent = new Intent(NewAdActivity.this, DisplayActivity.class);
               startActivity(newIntent);
            }
        });

        myButton = findViewById(R.id.button);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);

                if (radioButton.getText() != null) {

                    addRecord();
                }

            }


            /*public Connection connectionclass() {

                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                Connection connection = null;
                String ConnectionURL = null;

                try {

                } catch (Exception e) {
                    Log.i("Error ", e.getMessage());
                }
                return  connection;
            }*/
        });
    }

    public void addRecord() {

        try {
            type = radioButton.getText().toString();
            name = textName.getText().toString();
            phone = textPhone.getText().toString();
            descr = textDescr.getText().toString();
            date = textDate.getText().toString();
            area = textArea.getText().toString();
            Toast.makeText(NewAdActivity.this, "name   " + type, Toast.LENGTH_SHORT).show();

        DbManager db = new DbManager(NewAdActivity.this);
        String res = db.addRecord(type, name, phone, descr, date, area);
        Toast.makeText(NewAdActivity.this, "Added info...  " + res, Toast.LENGTH_LONG).show();

        }
        catch (Exception e){
            Toast.makeText(NewAdActivity.this, "Something was not done right", Toast.LENGTH_SHORT).show();
        }
        radioGroup.clearCheck();
        textName.setText("");
        textPhone.setText("");
        textDescr.setText("");
        textDate.setText("");
        textArea.setText("");

    }

}