package it.uniroma1.lcl.wimmp.parser;

import java.util.List;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

class WiktionarySaxHandler extends DefaultHandler {

    private WiktionaryParser parser;
    
    WiktionarySaxHandler(WiktionaryParser parser) {
        this.parser = parser;
    }
    
    private int countRecords = 0;
    
    int getCountRecords() {
        return countRecords;
    }
    
    private StringBuffer buffer;
    private String title = "";
    private String text = "";
    
    private enum HandlerState {
        DOCUMENT_START,
        PAGE_START,
        TITLE_START,
        TITLE,
        TITLE_END,
        TEXT_START,
        TEXT,
        TEXT_END,
        PAGE_END,
        DOCUMENT_END
    };
    
    private HandlerState state = HandlerState.DOCUMENT_START;
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch(qName){
            case "page":
                if (state == HandlerState.PAGE_END || state == HandlerState.DOCUMENT_START)
                    state = HandlerState.PAGE_START;
                break;
            case "title":
                if (state == HandlerState.PAGE_START)
                    state = HandlerState.TITLE_START;
                break;
            case "text":
                if (state == HandlerState.TITLE_END)
                    state = HandlerState.TEXT_START;
                break;
        }
    }
    
    
    
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch(qName){
            case "page":
                if (state == HandlerState.TEXT_END)
                    state = HandlerState.PAGE_END;
                break;
            case "title":
                if (state == HandlerState.TITLE)
                    state = HandlerState.TITLE_END;
                    title = buffer.toString();
                break;
            case "text":
                if (state == HandlerState.TEXT)
                    state = HandlerState.TEXT_END;
                    
                    text = buffer.toString();
                    
                    List<TextProcessor> processors = parser.getTextProcessors();
                    for (TextProcessor processor : processors) {
                        processor.processText(title, text);
                    }
                    
                    countRecords++;
                    title = "";
                    text = "";
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (state == HandlerState.TITLE_START) {
            state = HandlerState.TITLE;
            String c = String.copyValueOf(ch, start, length).trim();
            buffer = new StringBuffer(c);
            
        } else if (state == HandlerState.TEXT_START) {
            state = HandlerState.TEXT;
            String c = String.copyValueOf(ch, start, length);
            buffer = new StringBuffer(c);
            
        } else if(state == HandlerState.TITLE || state == HandlerState.TEXT) {
            String c = String.copyValueOf(ch, start, length);
            buffer.append(c);
        }
    }
    
    @Override
    public void startDocument() {
        List<TextProcessor> processors = parser.getTextProcessors();
        for (TextProcessor processor : processors) {
            processor.startProcess();
        }
    }
    
    @Override
    public void endDocument() {
        state = HandlerState.DOCUMENT_END;
        List<TextProcessor> processors = parser.getTextProcessors();
        for (TextProcessor processor : processors) {
            processor.endProcess();
        }
    }
}
