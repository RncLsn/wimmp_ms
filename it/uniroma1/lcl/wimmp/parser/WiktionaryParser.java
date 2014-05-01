package it.uniroma1.lcl.wimmp.parser;

import java.util.*;
import org.xml.sax.*;
import javax.xml.parsers.*;
import java.io.*;
import org.xml.sax.helpers.DefaultHandler;

public class WiktionaryParser {

/*
    public enum Function {
        PARSE("parse"),
        BUILD("build");
        
        private String func;
        
        public Function(String func) {
            this.func = func;
        }
        
        public String toString() {
            return this.func;
        }
    }
*/

    private String dataset = "";

    public WiktionaryParser(String dataset) {
        this.dataset = dataset;
    }
    
    public void parse() throws Exception {
        SAXParser parser = getSAXParser();
        InputSource source = getInputSource(dataset);
        DefaultHandler handler = new WiktionarySaxHandler(this);
        parser.parse(source, handler);
    }
    
    private SAXParser getSAXParser() throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        return factory.newSAXParser();
    }
    
    private InputSource getInputSource(String path) throws Exception {
        File file = new File(path);
        InputStream stream = new FileInputStream(file);
        InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
        InputSource source = new InputSource(reader);
        source.setEncoding("UTF-8");
        return source;
    }
    
    private ArrayList<TextProcessor> processors = new ArrayList();

    public boolean addTextProcessor(TextProcessor processor) {
        if (!processors.contains(processor)) {
            return processors.add(processor);
        }
        return false;
    }
    
    public boolean removeTextProcessor(TextProcessor processor) {
        return processors.remove(processor);
    }

    public List<TextProcessor> getTextProcessors() {
        return (List<TextProcessor>) processors.clone();
    }
}
