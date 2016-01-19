package weather.manish.com.weatherforecast;

import android.widget.ImageView;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import weather.manish.com.weatherforecast.model.LatLong;

/**
 * Created by Manish on 11/21/2015.
 */
public class Util {
    private static final char degreeUnit = 0x00B0;
    public static LatLong getLatLong(String xmlSource) throws Exception{
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(xmlSource));
        Document doc = db.parse(is);

        NodeList latNode = doc.getElementsByTagName("lat");
        Element latElement = (Element) latNode.item(0);
        String lat = getCharacterDataFromElement(latElement);

        NodeList longNode = doc.getElementsByTagName("lng");
        Element longElement = (Element) latNode.item(0);
        String lng = getCharacterDataFromElement(longElement);

        LatLong data = new LatLong();
        data.setLat(Float.parseFloat(lat));
        data.setLng(Float.parseFloat(lng));
        return data;
    }

    public static String getCharacterDataFromElement(Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return "";
    }

    public static String getPrecipitation(Float precipitationValue)
    {
        if(precipitationValue >= 0 && precipitationValue < 0.1)
            return "None";
        else
            return  "Hey!";
    }

    public  static HashMap<String, String> getUnits(Character degree)
    {
        HashMap<String,String> units = new HashMap<String,String>();
        switch (degree)
        {
            case 'F':
                units.put("temp",degreeUnit + "F");
                units.put("wind", "mph");
                units.put("dew",degreeUnit + "F");
                units.put("visibility","mi");
                units.put("pressure","mb");
                break;
            case 'C':
                units.put("temp",degreeUnit + "C");
                units.put("wind", "m/s");
                units.put("dew",degreeUnit + "C");
                units.put("visibility","km");
                units.put("pressure","hPa");
                break;
        }
        return units;
    }

    public static void getIcon(String icon, ImageView sumary)
    {
        switch (icon) {
            case "clear-day":
                sumary.setImageResource(R.drawable.clear);
                break;
            case "clear-night":
                sumary.setImageResource(R.drawable.clear_night);
                break;
            case "rain":
                sumary.setImageResource(R.drawable.rain);
                break;
            case "snow":
                sumary.setImageResource(R.drawable.snow);
                break;
            case "sleet":
                sumary.setImageResource(R.drawable.sleet);
                break;
            case "wind":
                sumary.setImageResource(R.drawable.wind);
                break;
            case "fog":
                sumary.setImageResource(R.drawable.fog);
                break;
            case "cloudy":
                sumary.setImageResource(R.drawable.cloudy);
                break;
            case "partly-cloudy-day":
                sumary.setImageResource(R.drawable.cloud_day);
                break;
            case "partly-cloudy-night":
                sumary.setImageResource(R.drawable.cloud_night);
                break;
        }
    }

    public static String getPublicIcon(String icon)
    {
        String url = "http://cs-server.usc.edu:45678/hw/hw8/images/";
        switch (icon) {
            case "clear-day":
            case "clear-night":
                url += icon.replace('-','_');
                break;
            case "rain":
            case "snow":
            case "sleet":
            case "wind":
            case "fog":
            case "cloudy":
                url += icon;
            case "partly-cloudy-day":
                url += "cloud_day";
                break;
            case "partly-cloudy-night":
                url += "cloud_night";
                break;
        }

        return url + ".png";
    }
}
