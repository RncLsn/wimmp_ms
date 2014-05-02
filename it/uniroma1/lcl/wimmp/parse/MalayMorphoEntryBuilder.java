package it.uniroma1.lcl.wimmp.parse;

import it.uniroma1.lcl.wimmp.*;
import it.uniroma1.lcl.wimmp.parse.*;
import it.uniroma1.lcl.wimmp.parse.morpho.*;
import java.util.*;
import java.util.regex.*;
import java.lang.reflect.*;
import java.lang.NoSuchMethodException;

public class MalayMorphoEntryBuilder {

    private int countAdjective = 0;
    private int countAdverb = 0;
    private int countConjunction = 0;
    private int countInterjection = 0;
    private int countNoun = 0;
    private int countNumeral = 0;
    private int countPostposition = 0;
    private int countPreposition = 0;
    private int countPronoun = 0;
    private int countPropernoun = 0;
    private int countVerb = 0;
    private int countNomFormOf = 0;
    private int countVerbFormOf = 0;
    
    private static final HashSet<String> setPos;
    
    static {
        setPos = new HashSet();
        setPos.add("adjective");
        setPos.add("adverb");
        setPos.add("conjunction");
        setPos.add("interjection");
        setPos.add("noun");
        setPos.add("numeral");
        setPos.add("postposition");
        setPos.add("preposition");
        setPos.add("pronoun");
        setPos.add("propernoun");
        setPos.add("verb");
    }
    
    private static final Pattern patternEntry = Pattern.compile("<malay>([\\s\\S]+)</malay>");
    private static final Pattern patternTag = Pattern.compile("<([^>/]+)>");
    private static final Pattern patternAdjective = Pattern.compile("<adjective>([\\s\\S]*)</adjective>");
    private static final Pattern patternAdverb = Pattern.compile("<adverb>([\\s\\S]*)</adverb>");
    private static final Pattern patternConjunction = Pattern.compile("<conjunction>([\\s\\S]*)</conjunction>");
    private static final Pattern patternInterjection = Pattern.compile("<interjection>([\\s\\S]*)</interjection>");
    private static final Pattern patternNoun = Pattern.compile("<noun>([\\s\\S]*)</noun>");
    private static final Pattern patternNumeral = Pattern.compile("<numeral>([\\s\\S]*)</numeral>");
    private static final Pattern patternPostposition = Pattern.compile("<postposition>([\\s\\S]*)</postposition>");
    private static final Pattern patternPreposition = Pattern.compile("<preposition>([\\s\\S]*)</preposition>");
    private static final Pattern patternPronoun = Pattern.compile("<pronoun>([\\s\\S]*)</pronoun>");
    private static final Pattern patternPropernoun = Pattern.compile("<proper-noun>([\\s\\S]*)</proper-noun>");
    private static final Pattern patternVerb = Pattern.compile("<verb>([\\s\\S]*)</verb>");
    
    public List<MorphoEntry> buildMorphoEntries(String title, String text) throws Exception {
        ArrayList<MorphoEntry> entries = new ArrayList();
        Matcher matcherEntry = patternEntry.matcher(text);
        if (matcherEntry.matches()) {
            String entry = matcherEntry.group(1);
            Matcher matcherTag = patternTag.matcher(entry);
            while (matcherTag.find()) {
                String tag = matcherTag.group(1).replace("-", "");
                if (setPos.contains(tag)) {    
                    String fieldName = "pattern" + capitalize(tag);
                    Pattern pattern = (Pattern)this.getClass().getDeclaredField(fieldName).get(null);
                    Matcher matcher = pattern.matcher(entry.substring(matcherTag.start()));
                    if (matcher.find()) {
                        List<MorphoEntry> builtEntries = null;
                        String methodName = tag + "Builder";
                        try {
                            Method methodBuilder = this.getClass().getDeclaredMethod(methodName, String.class, String.class);
                            methodBuilder.setAccessible(true);
                            builtEntries = (List<MorphoEntry>) methodBuilder.invoke(this, title, matcher.group(1));
                        } catch(NoSuchMethodException ex) {
                            System.out.println(ex.getMessage());
                            continue;
                        }
                        if (builtEntries != null) {
                            entries.addAll(builtEntries);
                        }
                    }
                }
                else {
                    /* debugging */
                    System.out.println("tagNotFound::"+tag);
                    if(tag.toLowerCase().contains("alternativeforms") || tag.toLowerCase().contains("templ") || tag.toLowerCase().contains("derivedterms")) {
                        System.out.println("\t"+title+"="+text);
                    }
                }
            }
        }
        return entries;
    }
    
    private static String capitalize(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    /** POS Regex **/
    
    private static final Pattern patternNomFormOf = Pattern.compile("<templ>ms-nom form of[\\s\\S]*</templ>");
    private static final Pattern patternVerbFormOf = Pattern.compile("<templ>ms-verb form of[\\s\\S]*</templ>");
    private static final Pattern patternDeclension = Pattern.compile("<declension>([\\s\\S]*)</declension>");
    private static final Pattern patternConjugation = Pattern.compile("<conjugation>([\\s\\S]*)</conjugation>");
    private static final Pattern patternTempl = Pattern.compile("<templ>([\\s\\S]*)</templ>");
    private static final Pattern patternTemplate = Pattern.compile("([^\\|\\}\\{\\<]+)(\\|([^\\|\\}\\{\\<]*))*");
    private static final Pattern patternParameters = Pattern.compile("\\|([^\\|\\}\\{\\<]+)");

    private List<MorphoEntry> adjectiveBuilder(String title, String text) {
        ArrayList<MorphoEntry> result = new ArrayList<MorphoEntry>();
        result.add(new MalayAdjective(title));
        return result;
    }

    private List<MorphoEntry> adverbBuilder(String title, String text) {
        ArrayList<MorphoEntry> result = new ArrayList<MorphoEntry>();
        result.add(new MalayAdverb(title));
        return result;
    }

    private List<MorphoEntry> conjunctionBuilder(String title, String text) {
        ArrayList<MorphoEntry> result = new ArrayList<MorphoEntry>();
        result.add(new MalayConjunction(title));
        return result;
    }
    
    private List<MorphoEntry> interjectionBuilder(String title, String text) {
        ArrayList<MorphoEntry> result = new ArrayList<MorphoEntry>();
        result.add(new MalayInterjection(title));
        return result;
    }
    
    private List<MorphoEntry> nounBuilder(String title, String text) {        
        ArrayList<MorphoEntry> result = new ArrayList<MorphoEntry>();
        result.add(new MalayNoun(title));
        return result;
    }

    private List<MorphoEntry> numeralBuilder(String title, String text) {
        ArrayList<MorphoEntry> result = new ArrayList<MorphoEntry>();
        result.add(new MalayNumeral(title));
        return result;
    }

    private List<MorphoEntry> postpositionBuilder(String title, String text) {
        ArrayList<MorphoEntry> result = new ArrayList<MorphoEntry>();
        result.add(new MalayPostposition(title));
        return result;
    }

    private List<MorphoEntry> prepositionBuilder(String title, String text) {
        ArrayList<MorphoEntry> result = new ArrayList<MorphoEntry>();
        result.add(new MalayPreposition(title));
        return result;
    }

    private List<MorphoEntry> pronounBuilder(String title, String text) {
        ArrayList<MorphoEntry> result = new ArrayList<MorphoEntry>();
        result.add(new MalayPronoun(title));
        return result;
    }

    private List<MorphoEntry> propernounBuilder(String title, String text) {
        ArrayList<MorphoEntry> result = new ArrayList<MorphoEntry>();
        result.add(new MalayPropernoun(title));
        return result;
    }

    private List<MorphoEntry> verbBuilder(String title, String text) {
        ArrayList<MorphoEntry> result = new ArrayList<MorphoEntry>();
        result.add(new MalayVerb(title));
        return result;
    }  
 
    public int getCountAdjective() { return countAdjective; }
    public int getCountAdverb() { return countAdverb; }
    public int getCountConjunction() { return countConjunction; }
    public int getCountInterjection() { return countInterjection; }
    public int getCountNoun() { return countNoun; }
    public int getCountNumeral() { return countNumeral; }
    public int getCountPostposition() { return countPostposition; }
    public int getCountPreposition() { return countPreposition; }
    public int getCountPronoun() { return countPronoun; }
    public int getCountPropernoun() { return countPropernoun; }
    public int getCountVerb() { return countVerb; }
    public int getCountNomFormOf() { return countNomFormOf; }
    public int getCountVerbFormOf() { return countVerbFormOf; }

}
