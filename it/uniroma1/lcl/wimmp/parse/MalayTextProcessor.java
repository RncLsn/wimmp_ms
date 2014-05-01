package it.uniroma1.lcl.wimmp.parse;

import it.uniroma1.lcl.wimmp.*;
import it.uniroma1.lcl.wimmp.parser.TextProcessor;

import java.util.logging.Logger;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.*;
import java.util.logging.Logger;
import java.lang.reflect.*;
import java.io.*;
import org.eclipse.mylyn.wikitext.core.parser.MarkupParser;
import org.eclipse.mylyn.wikitext.mediawiki.core.MediaWikiLanguage;
import org.eclipse.mylyn.wikitext.mediawiki.core.Template;
import org.apache.commons.lang3.StringEscapeUtils;


public class MalayTextProcessor implements TextProcessor {

    private List<MorphoEntry> entries;

    private static final Pattern patternEstonian = Pattern.compile("=+\\s*[eE]stonian\\s*=+");

    private int numEstonian = 0;
    
    private MalayMorphoEntryBuilder morphoEntryBuilder = new EstonianMorphoEntryBuilder();

    @Override
    public void processText(String title, String text) {
        if(patternEstonian.matcher(text).find()) {
            numEstonian++;
            try {
                Pattern commentsEraser = Pattern.compile("<!--[\\s\\S]*-->");
                text = commentsEraser.matcher(text).replaceAll("");
                
                StringWriter out = new StringWriter();
                
                // We take advantage from the wikitext library to parse mediawiki markup language
                // into an intermediate xml format with which we build morpho entries
                MediaWikiLanguage markuplanguage = new MediaWikiLanguage();
                markuplanguage.getTemplateProviders().add(new EstonianTemplateResolver());
                MarkupParser markupParser = new MarkupParser(markuplanguage, new EstonianDocumentBuilder(out));
                markupParser.parse(text);
                String output = StringEscapeUtils.unescapeHtml4(out.toString().trim());

                entries = morphoEntryBuilder.buildMorphoEntries(title, output);
                
                for (MorphoEntry entry : entries) {
                    for (MorphoEntryListener listener : listeners) {
                        listener.morphoEntry(entry);
                    }
                }
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }        
    }
    
    public void startProcess() {
    }
    
    public void endProcess() {
        
        for (MorphoEntryListener listener : listeners) {
            listener.finish();
        }
    
        Logger log = (Logger) Configuration.getResource("logger");
        
        log.info("numEstonian: " + numEstonian);
        log.info("countAdjective: " + morphoEntryBuilder.getCountAdjective());
        log.info("countAdverb: " + morphoEntryBuilder.getCountAdverb());
        log.info("countConjunction: " + morphoEntryBuilder.getCountConjunction());
        log.info("countInterjection: " + morphoEntryBuilder.getCountInterjection());
        log.info("countNoun: " + morphoEntryBuilder.getCountNoun());
        log.info("countNumeral: " + morphoEntryBuilder.getCountNumeral());
        log.info("countPostposition: " + morphoEntryBuilder.getCountPostposition());
        log.info("countPreposition: " + morphoEntryBuilder.getCountPreposition());
        log.info("countPronoun: " + morphoEntryBuilder.getCountPronoun());
        log.info("countPropernoun: " + morphoEntryBuilder.getCountPropernoun());
        log.info("countVerb: " + morphoEntryBuilder.getCountVerb());
        log.info("countNomFormOf: " + morphoEntryBuilder.getCountNomFormOf());
        log.info("countVerbFormOf: " + morphoEntryBuilder.getCountVerbFormOf());
    }
    
    private ArrayList<MorphoEntryListener> listeners = new ArrayList();
    
    public boolean addMorphoEntryListener(MorphoEntryListener listener) {
        if(!listeners.contains(listener)) {
            return listeners.add(listener);
        }
        return false;
    } 
    
    public boolean removeMorphoEntryListener(MorphoEntryListener listener) {
        return listeners.remove(listener);
    }
    
    public List<MorphoEntryListener> getMorphoEntryListeners() {
        return (List<MorphoEntryListener>) listeners.clone();
    }
}
