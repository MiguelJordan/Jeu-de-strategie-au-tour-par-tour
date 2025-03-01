package gameplayers.util.configmanagers;

import java.util.*;
import org.xml.sax.helpers.*;
import org.xml.sax.*;


public class ConfigFileHandler extends DefaultHandler {

    private String targetedElement;
    private String targetedParent;
    private Stack<String> path;
    private List<Integer> values;

    public ConfigFileHandler (String targetedElement, String targetedParent) {
        this.targetedElement = targetedElement;
        this.targetedParent = targetedParent;
        this.path = new Stack<>();
        this.values = new ArrayList<>();
    }

    public List<Integer> getValues () {
        return this.values;
    }

    @Override
    public void startElement (String uri, String localName, String qName, Attributes attributes) {        
        this.path.push(qName);
    }

    @Override
    public void endElement (String uri, String localName, String qName) {
        this.path.pop();
    }

    @Override
    public void characters (char[] ch, int start, int length) {
        String content = new String(ch, start, length);
        String elementPath = String.join("/", path);
        if (elementPath.endsWith(this.targetedElement) && elementPath.contains(this.targetedParent)) {
            try {
                this.values.add(Integer.parseInt(content));
            } catch (Exception e) {
                System.out.println("Some targeted elements are non integer : " + content);
            }
        }
    }

}