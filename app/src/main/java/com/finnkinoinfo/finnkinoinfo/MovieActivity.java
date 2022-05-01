package com.finnkinoinfo.finnkinoinfo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.finnkinoinfo.finnkinoinfo.finnkinoApi.Event;
import com.finnkinoinfo.finnkinoinfo.finnkinoApi.FinnkinoApiClient;
import com.koushikdutta.ion.Ion;

import org.xml.sax.SAXException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.xml.parsers.ParserConfigurationException;

public class MovieActivity extends AppCompatActivity {
    ImageView pictureView;
    FinnkinoApiClient finnkinoApiClient;
    SharedPreferences sp;
    {
        try {
            finnkinoApiClient = new FinnkinoApiClient();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        Bundle extras = getIntent().getExtras();
        int eventId = extras.getInt("EventID");
        pictureView = findViewById(R.id.pictureView);
        TextView movieName = findViewById(R.id.movie_name_textView);
        TextView description = findViewById(R.id.description_textView);
        RecyclerView recyclerView = findViewById(R.id.seeInPlaces);
        Button watchedMovie = findViewById(R.id.watched_Button);
        sp=getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);

        ArrayList<Event> events;
        Context ct =this;
        try {
            events = finnkinoApiClient.getSchedule(null, null, java.util.Optional.of(eventId));
            Event event = events.get(0);
            Ion.with(pictureView)
                    .load(event.getThumbnail());
            ArrayList<recyclerView> listOfEvents=setuprecyclerView(events);
            String details = event.getName()+"\nPituus: "+event.getLengthInMinutes()+" minuuttia.\nJulkaisuvuosi: "+event.getProductionYear()+"\nIkäraja: "+event.getAgeRestriction();
            movieName.setText(details);

            description.setText(event.getDescription());
            recyclerView_adapter adapter = new recyclerView_adapter(ct, listOfEvents);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(ct));


            watchedMovie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    SharedPreferences.Editor editor = sp.edit();
                    editor.putInt(event.getName(), eventId);
                    editor.commit();
                    Toast.makeText(MovieActivity.this, "Information added", Toast.LENGTH_SHORT).show();
                }
            });


        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }


    }
    private ArrayList <recyclerView> setuprecyclerView(ArrayList <Event> events) throws IOException, SAXException {
        ArrayList <recyclerView> listOfEvents=new ArrayList<recyclerView>();
        for (int i =0; i<events.size(); i++){
            listOfEvents.add(new recyclerView(events.get(i).getPlace(),events.get(i).getEventId(), events.get(i).getTime()));
        }
        return listOfEvents;
    }

}

