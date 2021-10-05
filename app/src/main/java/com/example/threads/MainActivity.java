package com.example.threads;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tv = null;

    public void foo_v2(View v)
    {
        new MyAsyncTask().execute();
    }

    private class MyAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            longRunningWork();
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tv.setText("Before starting async task");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            tv.setText("End of async task");
        }
    }


    public void foo(View v)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //tv.setText("starting long work");
                tv.post(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText("starting long work");
                    }
                });

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText("Hello I am starting the long task");
                    }
                });


                Log.v("MYTAG", "Brefore sleeping.");
                longRunningWork();
                Log.v("MYTAG", "After sleeping.");
                //tv.setText("ended long work");

                tv.post(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText("ended long work");
                    }
                });

            }
        }).start();

    }

    void longRunningWork()
    {
        try{
            Thread.sleep(5000);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.mytext);
        tv.setText("OK");

        AnimationDrawable myanim = (AnimationDrawable) ((ImageView)findViewById(R.id.imview)).getDrawable();
        myanim.start();
    }
}