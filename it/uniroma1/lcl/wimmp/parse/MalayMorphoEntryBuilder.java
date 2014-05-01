package it.uniroma1.lcl.wimmp.parse;

import it.uniroma1.lcl.wimmp.*;
import it.uniroma1.lcl.wimmp.parse.*;
import it.uniroma1.lcl.wimmp.parse.ms.morpho.*;
import java.util.*;
import java.util.regex.*;
import java.lang.reflect.*;
import java.lang.NoSuchMethodException;

public class MalayMorphoEntryBuilder {
    /*
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
    
    private static final Pattern patternEntry = Pattern.compile("<estonian>([\\s\\S]+)</estonian>");
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
            }
        }
        return entries;
    }
    
    private static String capitalize(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
    */
    /** POS Regex **/
    /*
    //TODO: Se ci si riesce estrarre il genitivo e il partitivo dai nomi aggettivi pronomi
    
    private static final Pattern patternNomFormOf = Pattern.compile("<templ>et-nom form of[\\s\\S]*</templ>");
    private static final Pattern patternVerbFormOf = Pattern.compile("<templ>et-verb form of[\\s\\S]*</templ>");
    private static final Pattern patternDeclension = Pattern.compile("<declension>([\\s\\S]*)</declension>");
    private static final Pattern patternConjugation = Pattern.compile("<conjugation>([\\s\\S]*)</conjugation>");
    private static final Pattern patternTempl = Pattern.compile("<templ>([\\s\\S]*)</templ>");
    private static final Pattern patternTemplate = Pattern.compile("([^\\|\\}\\{\\<]+)(\\|([^\\|\\}\\{\\<]*))*");
    private static final Pattern patternParameters = Pattern.compile("\\|([^\\|\\}\\{\\<]+)");
    
    private List<MorphoEntry> adjectiveBuilder(String title, String text) {
        Matcher matcherNomFormOf = patternNomFormOf.matcher(text);
        if (!matcherNomFormOf.find()) {
            countAdjective++;
            List<EstonianDeclension> declensions = extractDeclensions(text);
            
            List<MorphoEntry> entries = new ArrayList();
            
            for(int i = 0; i < declensions.size(); i++) {
                entries.add(new EstonianAdjective(title, declensions.get(i), true));
            }
            
            return entries;
        } else {
            countNomFormOf++;
        }
        return null;
    }
    
    private List<MorphoEntry> adverbBuilder(String title, String text) {
        countAdverb++;
        List<MorphoEntry> entries = new ArrayList();
        entries.add(new EstonianAdverb(title));
        return entries;
    }
    
    private List<MorphoEntry> conjunctionBuilder(String title, String text) {
        countConjunction++;
        List<MorphoEntry> entries = new ArrayList();
        entries.add(new EstonianConjunction(title));
        return entries;
    }
    
    private List<MorphoEntry> interjectionBuilder(String title, String text) {
        countInterjection++;
        List<MorphoEntry> entries = new ArrayList();
        entries.add(new EstonianInterjection(title));
        return entries;
    }
    
    private List<MorphoEntry> nounBuilder(String title, String text) {        
        Matcher matcherNomFormOf = patternNomFormOf.matcher(text);
        if (!matcherNomFormOf.find()) {
            countNoun++;
            List<EstonianDeclension> declensions = extractDeclensions(text);
            
            List<MorphoEntry> entries = new ArrayList();
            
            for(int i = 0; i < declensions.size(); i++) {
                entries.add(new EstonianNoun(title, declensions.get(i), true));
            }
            
            return entries;
        } else {
            countNomFormOf++;
        }
        return null;
    }
    
    private List<MorphoEntry> numeralBuilder(String title, String text) {
        countNumeral++;
        List<MorphoEntry> entries = new ArrayList();
        entries.add(new EstonianNumeral(title));
        return entries;
    }
    
    private List<MorphoEntry> postpositionBuilder(String title, String text) {
        countPostposition++;
        List<MorphoEntry> entries = new ArrayList();
        entries.add(new EstonianPostposition(title));
        return entries;
    }
    
    private List<MorphoEntry> prepositionBuilder(String title, String text) {
        countPreposition++;
        List<MorphoEntry> entries = new ArrayList();
        entries.add(new EstonianPreposition(title));
        return entries;
    }
    
    private List<MorphoEntry> pronounBuilder(String title, String text) {
        Matcher matcherNomFormOf = patternNomFormOf.matcher(text);
        if (!matcherNomFormOf.find()) {
            countPronoun++;
            List<EstonianDeclension> declensions = extractDeclensions(text);
            
            List<MorphoEntry> entries = new ArrayList();
            
            for(int i = 0; i < declensions.size(); i++) {
                entries.add(new EstonianPronoun(title, declensions.get(i), true));
            }
            
            return entries;
        } else {
            countNomFormOf++;
        }
        return null;
    }
    
    private List<MorphoEntry> propernounBuilder(String title, String text) {
        countPropernoun++;
        List<MorphoEntry> entries = new ArrayList();
        entries.add(new EstonianPropernoun(title));
        return entries;
    }
    
    private List<MorphoEntry> verbBuilder(String title, String text) {
        Matcher matcherVerbFormOf = patternNomFormOf.matcher(text);
        if (!matcherVerbFormOf.find()) {
            countVerb++;
            List<EstonianConjugation> conjugations = extractConjugations(text);
            
            List<MorphoEntry> entries = new ArrayList();
            
            for(int i = 0; i < conjugations.size(); i++) {
                entries.add(new EstonianVerb(title, conjugations.get(i), true));
            }
            
            return entries;
        } else {
            countVerbFormOf++;
        }
        return null;
    }
    
    private List<EstonianDeclension> extractDeclensions(String text) {
        ArrayList<EstonianDeclension> decl = new ArrayList();
        
        Matcher matcherDeclension = patternDeclension.matcher(text);
        while (matcherDeclension.find()) {
            if (matcherDeclension.group(1) != null) {
                String templ = matcherDeclension.group(1);
                Matcher matcherTempl = patternTempl.matcher(templ);
                while(matcherTempl.find()) {
                    if (matcherTempl.group(1) != null) {
                        String template = matcherTempl.group(1);
                        Matcher matcherTemplate = patternTemplate.matcher(template);
                        if(matcherTemplate.matches()) {
                            String type = matcherTemplate.group(1);
                            Matcher matcherParameters = patternParameters.matcher(template);
                            ArrayList<String> morphemes = new ArrayList();
                            while(matcherParameters.find()) {
                                String par = matcherParameters.group(1);
                                morphemes.add(par);
                            }
                            decl.add(new EstonianDeclension(type, morphemes));
                        }
                    }
                }
            }
        }
        
        if(decl.isEmpty()) {
            decl.add(new EstonianDeclension(null, null));
        }
        
        return decl;
    }
    
    private List<EstonianConjugation> extractConjugations(String text) {
        ArrayList<EstonianConjugation> conj = new ArrayList();
        
        Matcher matcherConjugation = patternConjugation.matcher(text);
        while (matcherConjugation.find()) {
            if (matcherConjugation.group(1) != null) {
                String templ = matcherConjugation.group(1);
                Matcher matcherTempl = patternTempl.matcher(templ);
                while(matcherTempl.find()) {
                    if (matcherTempl.group(1) != null) {
                        String template = matcherTempl.group(1);
                        Matcher matcherTemplate = patternTemplate.matcher(template);
                        if(matcherTemplate.matches()) {
                            String type = matcherTemplate.group(1);
                            Matcher matcherParameters = patternParameters.matcher(template);
                            ArrayList<String> morphemes = new ArrayList();
                            while(matcherParameters.find()) {
                                String par = matcherParameters.group(1);
                                morphemes.add(par);
                            }
                            conj.add(new EstonianConjugation(type, morphemes));
                        }
                    }
                }   
            }
        }
        
        if(conj.isEmpty()) {
            conj.add(new EstonianConjugation(null, null));
        }
        
        return conj;
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
    */
}
