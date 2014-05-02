package it.uniroma1.lcl.wimmp.parse.morpho;

import it.uniroma1.lcl.wimmp.*;
import it.uniroma1.lcl.wimmp.parse.EmptyRule;

public class MalayPronoun extends MorphoEntry {
    
    public MalayPronoun(String lemma) {
        super(lemma, MorphoEntry.POS.PRONOUN, true, new EmptyRule());
    }

}
