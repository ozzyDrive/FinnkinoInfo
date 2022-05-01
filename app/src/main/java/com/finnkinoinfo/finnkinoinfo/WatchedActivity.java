package com.finnkinoinfo.finnkinoinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Map;

public class WatchedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watched);
        TextView text = findViewById(R.id.textView);
        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        Map<String,?> keys = sp.getAll();
        for (Map.Entry<String,?> entry : keys.entrySet()){
            System.out.println(entry.getKey()+":"+entry.getValue());
        }


    }
}