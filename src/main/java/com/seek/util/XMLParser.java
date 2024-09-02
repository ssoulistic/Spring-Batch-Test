package com.seek.util;

import com.seek.model.BusStation;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.InputSource;

public class XMLParser {

    public static List<BusStation> parseBusStations(String xmlData) {
        List<BusStation> busStations = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(xmlData));
            Document doc = builder.parse(is);

            // XML의 루트 엘리먼트 가져오기
            Element root = doc.getDocumentElement();

            // itemList 요소를 찾아 파싱하기
            NodeList nodeList = root.getElementsByTagName("itemList");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                BusStation station = new BusStation();

                station.setArsId(getTagValue("arsId", element));
                station.setDist(Double.parseDouble(getTagValue("dist", element)));
                station.setGpsX(Double.parseDouble(getTagValue("gpsX", element)));
                station.setGpsY(Double.parseDouble(getTagValue("gpsY", element)));
                station.setStationId(getTagValue("stationId", element));
                station.setStationNm(getTagValue("stationNm", element));
                station.setStationTp(Integer.parseInt(getTagValue("stationTp", element)));

                busStations.add(station);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return busStations;
    }

    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }
}
