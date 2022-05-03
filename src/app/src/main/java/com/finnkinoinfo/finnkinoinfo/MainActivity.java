package com.finnkinoinfo.finnkinoinfo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.finnkinoinfo.finnkinoinfo.finnkinoApi.Event;
import com.finnkinoinfo.finnkinoinfo.finnkinoApi.FinnkinoApiClient;
import com.finnkinoinfo.finnkinoinfo.finnkinoApi.Theatre;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Optional;

import javax.xml.parsers.ParserConfigurationException;
/** makes finnkinoApiClient so it can be used trough out the class*/
public class MainActivity extends AppCompatActivity {
    FinnkinoApiClient finnkinoApiClient;
    {
        try {
            finnkinoApiClient = new FinnkinoApiClient();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
    ArrayList<RecyclerView> listOfEvents = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main);
        ArrayList <Theatre> movieTheatres=null;
        try {
            movieTheatres = finnkinoApiClient.getTheatres();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        EditText inputDate = (EditText) findViewById(R.id.input_date);


        if (movieTheatres!=null){
            Spinner dropDown=dropDown_menu(movieTheatres);

            Context ct = this;
            ArrayList<Theatre> finalMovieTheatres = movieTheatres;
            dropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            /*** onCreate makes main screen. onItemSelected takes placeID and search Events there today if date is not given*/
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    String searchDate= inputDate.getText().toString();
                    LocalDate date;
                    if (i!=0){
                        if (!searchDate.isEmpty()){
                            try {
                                date = LocalDate.parse(searchDate, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                            } catch (DateTimeParseException e){
                                Toast.makeText(ct, R.string.date_toast, Toast.LENGTH_SHORT).show();
                                return;
                            }

                        }else {
                            date = LocalDate.now();
                        }

                        androidx.recyclerview.widget.RecyclerView recyclerView = findViewById(R.id.movieList);
                        setuprecyclerView(Optional.of(finalMovieTheatres.get(i).getId()), date);
                        RecyclerViewAdapter adapter = new RecyclerViewAdapter(ct, listOfEvents, new RecyclerViewAdapter.ItemClickListener() {
                            @Override
                            /**From RecyclerView onItem click starts MovieActivity and sends eventId to new activity */
                            public void onItemClick(RecyclerView details) {
                                openMovieActivity(details.eventId);
                            }
                        });
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(ct));}
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        }
    }

    /** Makes DropDown menu from theatres
     * @param movieTheatres ArrayList of movietheatres and puts them to spinner
     * @return Spinner spinner of theatres*/
    private Spinner dropDown_menu(ArrayList<Theatre> movieTheatres){
        Spinner dropDown = (Spinner) findViewById(R.id.dropDown);
        ArrayList <String> names = new ArrayList<String>();
        for (int i =0; i<movieTheatres.size(); i++){
            Theatre node = (Theatre) movieTheatres.get(i);
            names.add(node.getName());
        }

        ArrayAdapter <String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, names);
        adapter.setDropDownViewResource(R.layout.spinner_item2);
        dropDown.setAdapter(adapter);
        return dropDown;

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    /** Setups RecyclerView when place is selected from dropdown
     * @param TheatreId used to get certain theatres events
     * @param date Used to get certain events on this date(this day if user hasn't given date)
     * Return Void*/
    private void setuprecyclerView(Optional<Integer> TheatreId, LocalDate date) throws IOException, SAXException {

        listOfEvents.clear();
        ArrayList <Event> events = finnkinoApiClient.getSchedule(TheatreId, date);

        for (int i =0; i<events.size(); i++){
            listOfEvents.add(new RecyclerView(events.get(i).getName(),events.get(i).getEventId(), events.get(i).getTime()));
        }


    }
    /** Starts MovieActivity and sends EventID to new activity
     * @param eventId Takes certain events ID number and sends it to MovieActivity
     * Return void*/
    private void openMovieActivity( int eventId){
        Intent intent = new Intent(this, MovieActivity.class);
        intent.putExtra("EventID", eventId);
        startActivity(intent);
    }
    /** on button click starts WaatchedActivity
     * Return void*/
    public void onClick( View v){
        Intent intent = new Intent(this, WatchedActivity.class);
        startActivity(intent);
    }
}