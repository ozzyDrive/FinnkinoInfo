package com.finnkinoinfo.finnkinoinfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.finnkinoinfo.finnkinoinfo.data.LoginRepository;
import com.finnkinoinfo.finnkinoinfo.data.model.LoggedInUser;

import java.util.ArrayList;
import java.util.Map;

public class WatchedActivity extends AppCompatActivity {
    androidx.recyclerview.widget.RecyclerView recyclerWatched;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watched);
        TextView text = findViewById(R.id.textView);
        recyclerWatched = findViewById(R.id.recycler_watched);
        Button clearList = findViewById(R.id.emptyList);
        ArrayList<RecyclerView> listOfEvents= new ArrayList<RecyclerView>();
        LoggedInUser user = LoginRepository.getInstance(null, this).getUser();
        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyMovies"+user.getDisplayName(), Context.MODE_PRIVATE);
        Map<String,?> keys = sp.getAll();
        for (Map.Entry<String,?> entry : keys.entrySet()){
            System.out.println(entry.getKey()+":"+entry.getValue());
            listOfEvents.add(new RecyclerView(entry.getKey(), (int) entry.getValue(), "Katsottu"));
        }



        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, listOfEvents, new RecyclerViewAdapter.ItemClickListener() {
            @Override
            /**from RecyclerView clicking starts MovieActivity with selected movies EventID
             * Return void*/
            public void onItemClick(RecyclerView details) {
                Intent intent = new Intent(WatchedActivity.this, MovieActivity.class);
                intent.putExtra("EventID", details.eventId);
                startActivity(intent);
            }
        });
        recyclerWatched.setAdapter(adapter);
        recyclerWatched.setLayoutManager(new LinearLayoutManager(this));


    }
    /**Function clears watched movies list from internal memory
     * Return void
     * Attached to a button on WatchedActivity*/
    public void clearList(View v){
        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyMovies", Context.MODE_PRIVATE);
        sp.edit().clear().apply();

        Toast.makeText(WatchedActivity.this, R.string.list_emptied, Toast.LENGTH_SHORT).show();
        startActivity();
    }
    /** Starts MainActivity
     * Return void
     * used when watched movies list is cleared*/
    private void startActivity(){
        Intent main =new Intent(WatchedActivity.this, MainActivity.class);
        startActivity(main);
    }
}