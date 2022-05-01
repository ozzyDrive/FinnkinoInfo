package com.finnkinoinfo.finnkinoinfo.finnkinoApi;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * A wrapper for the official Finnkino XML API.
 */
public class FinnkinoApiClient {
    final static String THEATRE_AREAS_ENDPOINT = "https://www.finnkino.fi/xml/TheatreAreas/";
    final static String SCHEDULE_ENDPOINT = "https://www.finnkino.fi/xml/Schedule/";
    final static String EVENTS_ENDPOINT = "https://www.finnkino.fi/xml/Events";

    DocumentBuilder documentBuilder;

    /**
     *
     * @throws ParserConfigurationException
     */
    public FinnkinoApiClient() throws ParserConfigurationException {
        documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    }

    /**
     * Fetches a list of Finnkino Theatres from the API and returns an ArrayList of those.
     * @return a list of theatres
     * @throws IOException
     * @throws SAXException
     */
    public ArrayList<Theatre> getTheatres() throws IOException, SAXException {
        ArrayList<Theatre> theatres = new ArrayList<Theatre>();

        NodeList theatreAreas = getDocument(THEATRE_AREAS_ENDPOINT).getElementsByTagName("TheatreArea");
        for (int index = 0; index < theatreAreas.getLength(); index++) {
            Element theatreNode = (Element) theatreAreas.item(index);
            Node idNode = getFirstChildNode(theatreNode, "ID");
            Node nameNode = getFirstChildNode(theatreNode, "Name");

            Theatre theatre = new Theatre();
            theatre.id = Integer.parseInt(idNode.getTextContent());
            theatre.name = nameNode.getTextContent();
            theatres.add(theatre);
        }

        return theatres;
    }

    /**
     * Fetches the schedule of given theatre, on date and optional eventId. Returns an ArrayList
     * of the Events found.
     * @param theatreId     Finnkino theatre identifier to fetch (area in query parameters)
     * @param date          date of schedule to fetch (dt in query parameters)
     * @param eventId       optional Finnkino event identifier to fetch
     * @return              a list of events
     * @throws IOException
     * @throws SAXException
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Event> getSchedule(Optional<Integer> theatreId, LocalDate date, Optional<Integer> eventId) throws IOException, SAXException {
        ArrayList<Event> events = new ArrayList<>();

        String scheduleEndpoint = String.format(
                SCHEDULE_ENDPOINT + "?area=%s&dt=%s&eventID=%s",
                theatreId != null ? theatreId.get() : "",
                date != null ? date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) : "",
                eventId != null ? eventId.get() : "");
        Document schedule = getDocument(scheduleEndpoint);

        NodeList shows = schedule.getElementsByTagName("Show");
        for (int index = 0; index < shows.getLength(); index++) {
            Element showNode = (Element) shows.item(index);

            Node titleNode = getFirstChildNode(showNode, "Title");
            Node ratingNode = getFirstChildNode(showNode, "Rating");
            Node productionYearNode = getFirstChildNode(showNode, "ProductionYear");
            Node lengthInMinutesNode = getFirstChildNode(showNode, "LengthInMinutes");
            Node eventIdNode = getFirstChildNode(showNode, "EventID");
            Node eventStartTime = getFirstChildNode(showNode, "dttmShowStart");
            Node eventPlaceNode = getFirstChildNode(showNode, "Theatre");


            String eventsEndpoint = String.format(EVENTS_ENDPOINT + "?eventID=%d", Integer.parseInt(eventIdNode.getTextContent()));
            Document eventDocument = getDocument(eventsEndpoint);

            Node eventNode = eventDocument.getElementsByTagName("Event").item(0);
            Node synopsisNode = getFirstChildNode((Element) eventNode, "ShortSynopsis");
            Node smallImagePortraitNode = (Node) ((Element) eventNode).getElementsByTagName("EventSmallImagePortrait").item(0);

            Event event = new Event();
            event.name = titleNode.getTextContent();
            event.ageRestriction = ratingNode.getTextContent();
            event.productionYear = Integer.parseInt(productionYearNode.getTextContent());
            event.lengthInMinutes = Integer.parseInt(lengthInMinutesNode.getTextContent());
            event.description = synopsisNode.getTextContent();
            event.time = getTimeStamp(eventStartTime);
            event.eventId = Integer.parseInt(eventIdNode.getTextContent());
            event.thumbnail = smallImagePortraitNode.getTextContent();
            event.place= eventPlaceNode.getTextContent();
            events.add(event);
        }

        return events;
    }

    /**
     * Fetches the schedule of given theatre. Returns an ArrayList of the Events found.
     * @param theatreId     Finnkino theatre identifier to fetch (area in query parameters)
     * @return              a list of events
     * @throws IOException
     * @throws SAXException
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Event> getSchedule(Optional<Integer> theatreId) throws IOException, SAXException {
        return getSchedule(theatreId, null, null);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Event> getSchedule(Optional<Integer> theatreId, LocalDate date) throws IOException, SAXException {
        return getSchedule(theatreId, date, null);
    }

    private Document getDocument(String endpoint) throws IOException, SAXException {
        documentBuilder.reset();
        Document document = documentBuilder.parse(endpoint);
        document.getDocumentElement().normalize();
        return document;
    }

    private Node getFirstChildNode(Element element, String nodeName) {
        return element.getElementsByTagName(nodeName).item(0);
    }

    private String getTimeStamp(Node time){
        Element element = (Element) time;
        String[] parsedtime=(element.getTextContent()).split("T");
        String result=parsedtime[1].substring(0,5);
        return result;
    }

}
