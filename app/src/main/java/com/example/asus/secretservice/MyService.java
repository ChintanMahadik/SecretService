package com.example.asus.secretservice;

import android.Manifest;
import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Environment;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Date;

public class MyService extends Service implements OnTouchListener {

    private String TAG = this.getClass().getSimpleName();
    // window manager
    private WindowManager mWindowManager;
    private LinearLayout touchLayout;
    private EditText editText;
    static int width,height;
    private Button save;
    private Button cancel;
    private SQLiteDatabase db;


    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        touchLayout = new LinearLayout(this);
        LayoutParams lp = new LayoutParams(10,100);
        touchLayout.setLayoutParams(lp);
        touchLayout.setBackgroundColor(Color.GRAY);
        touchLayout.setOrientation(LinearLayout.VERTICAL);
        touchLayout.setOnTouchListener(this);
        width=60;
        height=60;
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        WindowManager.LayoutParams mParams = new WindowManager.LayoutParams(width,height, WindowManager.LayoutParams.TYPE_TOAST,WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
        mParams.gravity = Gravity.LEFT | Gravity.TOP;
        Log.i(TAG, "add View");
        mWindowManager.addView(touchLayout, mParams);

        Notification n=new Notification();
        startForeground(10,n);

    }

    protected void createDatabase()
    {
        db=openOrCreateDatabase("SecretDB",Context.MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS data(text VARCHAR);");
    }
    protected void insertIntoDB()
    {
        String data=editText.getText().toString().trim();
        if(data.equals(""))
        {
            Toast.makeText(getApplicationContext(),"No DATA Entered",Toast.LENGTH_SHORT).show();
        }
        else
        {
            String query="INSERT INTO data(text) VALUES('"+data+"'); ";
            db.execSQL(query);
            Toast.makeText(getApplicationContext(),"Data Inserted into DataBase",Toast.LENGTH_SHORT).show();

        }
    }
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(motionEvent.getAction()==MotionEvent.ACTION_DOWN)
        {
            createDatabase();
            if(motionEvent.getAction()==MotionEvent.ACTION_DOWN) {
                Log.i(TAG, "Action:" + motionEvent.getAction() + "\t X:" + motionEvent.getRawX() + "\t Y:" + motionEvent.getRawY());
                System.out.println("Screen touched");
               // Toast.makeText(getApplicationContext(), "Screen Touched", Toast.LENGTH_SHORT).show();

                if(width==60 && width==60)
                {
                    width=510;height=510;
                    touchLayout.setBackgroundColor(Color.CYAN);
                    WindowManager.LayoutParams mParams = new WindowManager.LayoutParams(width,height, WindowManager.LayoutParams.TYPE_TOAST, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, PixelFormat.TRANSLUCENT);
                    //mParams.gravity = Gravity.LEFT | Gravity.TOP;

                    editText=new EditText(this);
                    editText.setWidth(400);
                    editText.setHeight(310);
                    editText.setHintTextColor(Color.GRAY);
                    editText.setHint("Enter your text...");
                    editText.setTextColor(Color.BLACK);
                    editText.setOnClickListener(keyboard);
                    editText.setGravity(Gravity.TOP);

                    //editText.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
                    //////////////////////
                    save=new Button(this);
                    save.setText("Save");
                    save.setOnClickListener(savetext);

                    ////////////////////////
                    cancel=new Button(this);
                    cancel.setText("Cancel");
                    cancel.setOnClickListener(cancelview);

                    touchLayout.addView(editText);
                    touchLayout.addView(save);
                    touchLayout.addView(cancel);

                    mWindowManager.updateViewLayout(touchLayout,mParams);
                }
            }
        }
        return true;
    }

    EditText.OnClickListener keyboard =new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //Toast.makeText(getApplicationContext(), "Edit Text Touched", Toast.LENGTH_SHORT).show();
            InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText,InputMethodManager.SHOW_IMPLICIT);
        }
    };

    Button.OnClickListener savetext=new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            insertIntoDB();
            editText.setText("");
        }
    };



    Button.OnClickListener cancelview =new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            touchLayout.removeView(editText);
            touchLayout.removeView(save);
            touchLayout.removeView(cancel);
            width=60;height=60;
            touchLayout.setBackgroundColor(Color.GREEN);
            WindowManager.LayoutParams mParams = new WindowManager.LayoutParams(width,height, WindowManager.LayoutParams.TYPE_TOAST, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
            mParams.gravity = Gravity.LEFT | Gravity.TOP;
            mWindowManager.updateViewLayout(touchLayout,mParams);
        }
    };
    @Override
    public void onDestroy() {
        if(mWindowManager!=null)
        {
            if(touchLayout!=null)
            {
                mWindowManager.removeView(touchLayout);
            }
        }
        super.onDestroy();
    }
}