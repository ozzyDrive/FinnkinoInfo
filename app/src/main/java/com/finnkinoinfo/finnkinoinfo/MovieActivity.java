package com.finnkinoinfo.finnkinoinfo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.finnkinoinfo.finnkinoinfo.finnkinoApi.Event;
import com.finnkinoinfo.finnkinoinfo.finnkinoApi.FinnkinoApiClient;
import com.koushikdutta.ion.Ion;

import org.xml.sax.SAXException;

import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

public class MovieActivity extends AppCompatActivity {
    ImageView pictureView;
    FinnkinoApiClient finnkinoApiClient;

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

        ArrayList<Event> events;

        try {
            events = finnkinoApiClient.getSchedule(null, null, java.util.Optional.of(eventId));
            Event event = events.get(0);
            Ion.with(pictureView)
                    .load(event.getThumbnail());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

    }
}
