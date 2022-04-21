package com.finnkinoinfo.finnkinoinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main);
        ArrayList movieTheatres= makeTheatres();
        EditText date = (EditText) findViewById(R.id.input_date);


        Spinner dropDown=dropDown_menu(movieTheatres);
        dropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                movieTheatre selected =(movieTheatre) movieTheatres.get(i);

                Toast.makeText(MainActivity.this, selected.getID(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


    public ArrayList makeTheatres(){
        ArrayList<movieTheatre> movieTheatres = new ArrayList<movieTheatre>();
        try {
            DocumentBuilder docB = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document xmlDoc = docB.parse(getString(R.string.url_string));
            xmlDoc.getDocumentElement().normalize();
            NodeList nList = xmlDoc.getDocumentElement().getElementsByTagName("TheatreArea");
            for (int i =0; i< nList.getLength(); i++){
                Node node = nList.item(i);
                Element element = (Element) node;
                String id = element.getElementsByTagName("ID").item(0).getTextContent();
                String name = element.getElementsByTagName("Name").item(0).getTextContent();
                movieTheatres.add(new movieTheatre(id, name));

            }

            movieTheatres.remove(17);
            movieTheatres.remove(5);
            movieTheatres.remove(2);
            movieTheatres.remove(1);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        return movieTheatres;
    }

    public Spinner dropDown_menu(ArrayList<movieTheatre> movieTheatres){
        Spinner dropDown = (Spinner) findViewById(R.id.dropDown);
        ArrayList <String> names = new ArrayList<String>();
        for (int i =0; i<movieTheatres.size(); i++){
            movieTheatre node = (movieTheatre) movieTheatres.get(i);
            names.add(node.getName());
        }

        ArrayAdapter <String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, names);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        dropDown.setAdapter(adapter);
        return dropDown;

    }
}