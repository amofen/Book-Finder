package com.example.bookfinder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void start(View view){
        //TODO start the scan activity
        Toast.makeText(this,"Scan  is supposed to start now !",Toast.LENGTH_SHORT).show();
    }
}
