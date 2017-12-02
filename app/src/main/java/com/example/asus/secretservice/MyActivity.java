package com.example.asus.secretservice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MyActivity extends Activity implements View.OnClickListener {

   //button objects
    private Button buttonStart;
    private Button buttonStop;
    private Button buttonShow;
    private TextView showData;
    private SQLiteDatabase db;
    private Cursor c;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_my);
    openDatabase();
   //getting buttons from xml
   buttonStart = (Button) findViewById(R.id.buttonStart);
   buttonStop = (Button) findViewById(R.id.buttonStop);
   buttonShow=(Button)findViewById(R.id.buttonShow);

    //attaching onclicklistener to buttons
   buttonStart.setOnClickListener(this);
   buttonStop.setOnClickListener(this);
   buttonShow.setOnClickListener(this);


    }

    protected void openDatabase()
    {
        db=openOrCreateDatabase("SecretDB", Context.MODE_PRIVATE,null);
    }

    protected  void showRecords()
    {
        String txt=c.getString(0);
        showData.setText(txt);
    }

   @Override
   public void onClick(View view) {
    if (view == buttonStart) {
   //start the service here
        Intent service=new Intent(this,MyService.class);
        startService(service);
        view.setTag("on");

    } else if (view == buttonStop) {
    //stop the service here
        Intent service=new Intent(this,MyService.class);
        stopService(service);
    }
       else if(view == buttonShow)
    {
        Intent i=new Intent(getApplicationContext(),ListData.class);
        startActivity(i);
    }
   }
}