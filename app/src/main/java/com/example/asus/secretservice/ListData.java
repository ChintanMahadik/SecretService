
package com.example.asus.secretservice;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListData extends Activity {

    Cursor cursor;
    SQLiteListAdapter ListAdapter ;
    SQLiteDatabase db;
    ArrayList<String> text = new ArrayList<String>();

    ListView LISTVIEW;
    Button delete;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listdata);

        LISTVIEW = (ListView) findViewById(R.id.listView);
        delete=(Button)findViewById(R.id.delete);
        delete.setBackgroundColor(Color.RED);
        delete.setOnClickListener(delete_list);

    }

    Button.OnClickListener delete_list=new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            db=openOrCreateDatabase("SecretDB", Context.MODE_PRIVATE,null);
            String query="DELETE FROM data;";
            db.execSQL(query);
            Intent i=new Intent(getApplicationContext(),MyActivity.class);
            Toast.makeText(getApplicationContext(),"All Records are Deleted",Toast.LENGTH_SHORT).show();
            startActivity(i);
        }
    };

    @Override
    protected void onResume() {

        ShowSQLiteDBdata() ;

        super.onResume();
    }

    private void ShowSQLiteDBdata() {

        db=openOrCreateDatabase("SecretDB", Context.MODE_PRIVATE,null);

        cursor = db.rawQuery("SELECT * FROM data", null);

        text.clear();

        if (cursor.moveToLast()) {
            do {
                text.add(cursor.getString(cursor.getColumnIndex("text")));

            } while (cursor.moveToPrevious());
        }

        ListAdapter = new SQLiteListAdapter(ListData.this,
                text
        );

        LISTVIEW.setAdapter(ListAdapter);

        cursor.close();
    }
}
