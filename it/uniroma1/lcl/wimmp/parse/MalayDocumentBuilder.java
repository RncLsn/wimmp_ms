package it.uniroma1.lcl.wimmp.parse;

import org.eclipse.mylyn.wikitext.core.parser.builder.AbstractXmlDocumentBuilder;
import java.io.Writer;
import org.eclipse.mylyn.wikitext.core.parser.*;
import java.util.Stack;
import java.util.regex.*;
import org.apache.commons.lang3.StringEscapeUtils;

public class MalayDocumentBuilder extends AbstractXmlDocumentBuilder {
    
    public MalayDocumentBuilder(Writer out) {
        super(out);
    }
    
    //private Stack<String> tagStack = new Stack();
    private Stack<Integer> levStack = new Stack();
    private boolean heading = false;
    private int etLevel = -1;
    
    private String text = "";
    private int lastLevel = 0;
    
    private Pattern patternTemplate = Pattern.compile("(<templ>[\\s\\S]*</templ>)");
    
    @Override
	public void beginHeading(int level, Attributes attributes) {	
        if (etLevel >= 0 && level <= etLevel) {
            etLevel = -1;
        }
        
        if((etLevel < 0) || (etLevel >= 0 && level > etLevel)) {
            heading = true;
        }
        lastLevel = level;
	}
    
    @Override
	public void endHeading() {
        if (heading) {
            String tag = normalizeTag(text);
            if (etLevel < 0 && tag.equals("estonian")) {
                etLevel = lastLevel;
                writer.writeStartElement(tag);
                levStack.push(lastLevel);
                //tagStack.push(tag);
                //System.out.println("<" + tag + ">");
            } else if(etLevel >= 0 && lastLevel > etLevel) {
                writePreviousElements();
                writer.writeStartElement(tag);
                //System.out.println("<" + tag + ">");
                levStack.push(lastLevel);
                //tagStack.push(tag);
            }
            heading = false;
        }
	}
	
	@Override
	public void characters(String text) {
        this.text = text;
        if(!heading && etLevel >= 0) {
            Matcher m = patternTemplate.matcher(text);
	        if (m.find()) {
	            for (int i = 0; i < m.groupCount(); i++) {
	                writer.writeCharacters(m.group(i));
	            }
	        }
        }
	}
	
	@Override
	public void link(Attributes attributes, String hrefOrHashName, String text) {
	    this.text = text;
	    if(!heading && etLevel >= 0) {
            Matcher m = patternTemplate.matcher(text);
	        if (m.find()) {
	            for (int i = 0; i < m.groupCount(); i++) {
	                writer.writeCharacters(m.group(i));
	            }
	        }
        }
	}
	
	private String normalizeTag(String tag) {
	    Pattern p = Pattern.compile("\\s+");
	    Matcher m = p.matcher(tag.trim().toLowerCase());
	    return m.replaceAll("-");
	}
	
	private void writePreviousElements() {
	    while (!levStack.empty() && lastLevel <= levStack.peek()) {
            writer.writeEndElement();
            int lev = levStack.pop();
            //String tag = tagStack.pop();
            //System.out.println(lev);
            //System.out.println("</" + tag + ">");
        }
	}
	
	@Override
    public void beginDocument() { }
    
    @Override
    public void endDocument() {
        while (!levStack.empty()) {
            writer.writeEndElement();
            int lev = levStack.pop();
            //String tag = tagStack.pop();
            //System.out.println(lev);
            //System.out.println("</" + tag + ">");
        }
    }
    
    @Override
	public void entityReference(String entity) { }
    
    @Override
	public void acronym(String text, String definition) { }
    
    @Override
	public void beginBlock(BlockType type, Attributes attributes) { }
    
    @Override
	public void endBlock() {  }

    @Override
	public void beginSpan(SpanType type, Attributes attributes) { }
	
	@Override
	public void endSpan() { }
	
	@Override
	public void image(Attributes attributes, String url) { }
	
    @Override
	public void imageLink(Attributes linkAttributes, Attributes imageAttributes, String href, String imageUrl) { }
	
	@Override
	public void lineBreak() { }
	
	@Override
	public void charactersUnescaped(String literal) { }

}
