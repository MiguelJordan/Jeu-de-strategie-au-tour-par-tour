package gameplayers.util.configmanagers;

import java.util.*;
import javax.xml.parsers.*;


public class ConfigManager {

    private static final SAXParserFactory PARSER_FACTORY = SAXParserFactory.newInstance();

    private String configFilePath;

    public ConfigManager (String filePath) {
        this.configFilePath = filePath;
    }

    public List<Integer> getValues (String targetedElement, String targetedParent) {
        try {
            SAXParser parser = PARSER_FACTORY.newSAXParser();
            ConfigFileHandler handler = new ConfigFileHandler(targetedElement, targetedParent);
            parser.parse(this.configFilePath, handler);
            return handler.getValues();
        } catch (Exception e) {
            System.out.println("path : " + configFilePath + " element : " + targetedElement + " parent : " + targetedParent);
            System.out.println("Could not get values from parser : " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public int getValue (String targetedElement, String targetedParent) {
        List<Integer> values = this.getValues(targetedElement, targetedParent);
        if (values.size() > 0) {
            return values.get(0);
        }
        return 0;
    }


}