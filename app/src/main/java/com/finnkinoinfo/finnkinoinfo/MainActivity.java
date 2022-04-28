package com.finnkinoinfo.finnkinoinfo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {
    FinnkinoApiClient finnkinoApiClient;

    {
        try {
            finnkinoApiClient = new FinnkinoApiClient();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
    ArrayList<recyclerView> listOfEvents = new ArrayList<>();
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

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                try {
                    String searchDate= inputDate.getText().toString();
                    LocalDate date;
                    if (i!=0){
                        if (!searchDate.isEmpty()){
                           date = LocalDate.parse(searchDate, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                        }else {
                            date= LocalDate.now();
                        }

                        RecyclerView recyclerView = findViewById(R.id.movieList);
                        setuprecyclerView(finalMovieTheatres.get(i).getId(), date);
                        recyclerView_adapter adapter = new recyclerView_adapter(ct, listOfEvents, new recyclerView_adapter.ItemClickListener() {
                            @Override
                            public void onItemClick(com.finnkinoinfo.finnkinoinfo.recyclerView details) {
                                showToast(details.eventId);
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

    private void showToast(int message){
        System.out.println(message);
    }





    public Spinner dropDown_menu(ArrayList<Theatre> movieTheatres){
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
    private void setuprecyclerView(int TheatreId, LocalDate date) throws IOException, SAXException {
        System.out.println(TheatreId);
        listOfEvents.clear();
        ArrayList <Event> events = finnkinoApiClient.getSchedule(TheatreId, date);

        for (int i =0; i<events.size(); i++){
            listOfEvents.add(new recyclerView(events.get(i).getName(),events.get(i).getEventId(), events.get(i).getTime()));
        }


    }
}