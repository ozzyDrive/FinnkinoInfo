package com.finnkinoinfo.finnkinoinfo.finnkinoApi;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class FinnkinoApiClient {
    final static String THEATRE_AREAS_ENDPOINT = "https://www.finnkino.fi/xml/TheatreAreas/";
    final static String SCHEDULE_ENDPOINT = "https://www.finnkino.fi/xml/Schedule/";
    final static String EVENTS_ENDPOINT = "https://www.finnkino.fi/xml/Events";

    DocumentBuilder documentBuilder;

    public FinnkinoApiClient() throws ParserConfigurationException {
        documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    }

    public ArrayList<Theatre> getTheatres() throws IOException, SAXException {
        ArrayList<Theatre> theatres = new ArrayList<Theatre>();

        NodeList theatreAreas = getDocument(SCHEDULE_ENDPOINT).getElementsByTagName("TheatreArea");
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

    public ArrayList<Event> getSchedule(int theatreId, Date date, Optional<Integer> eventId) throws IOException, SAXException {
        ArrayList<Event> events = new ArrayList<Event>();

        String scheduleEndpoint = String.format(
                SCHEDULE_ENDPOINT + "?area=%d&date=%s&eventID=%s",
                theatreId,
                date != null ? date.toString() : "",
                eventId != null ? eventId.toString() : "");
        Document schedule = getDocument(scheduleEndpoint);

        NodeList shows = schedule.getElementsByTagName("Show");
        for (int index = 0; index < shows.getLength(); index++) {
            Element showNode = (Element) shows.item(index);

            Node titleNode = getFirstChildNode(showNode, "Title");
            Node ratingNode = getFirstChildNode(showNode, "Rating");
            Node productionYearNode = getFirstChildNode(showNode, "ProductionYear");
            Node lengthInMinutesNode = getFirstChildNode(showNode, "LengthInMinutes");
            Node eventIdNode = getFirstChildNode(showNode, "EventID");

            String eventsEndpoint = String.format(EVENTS_ENDPOINT + "?eventID=%d", Integer.parseInt(eventIdNode.getTextContent()));
            Document eventDocument = getDocument(eventsEndpoint);

            Node eventNode = eventDocument.getElementsByTagName("Event").item(0);
            Node synopsisNode = getFirstChildNode((Element) eventNode, "Synopsis");

            Event event = new Event();
            event.name = titleNode.getTextContent();
            event.ageRestriction = Integer.parseInt(ratingNode.getTextContent());
            event.productionYear = Integer.parseInt(productionYearNode.getTextContent());
            event.lengthInMinutes = Integer.parseInt(lengthInMinutesNode.getTextContent());
            event.description = synopsisNode.getTextContent();

            events.add(event);
        }

        return events;
    }

    public ArrayList<Event> getSchedule(int theatreId) throws IOException, SAXException {
        return getSchedule(theatreId, null, null);
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
}
