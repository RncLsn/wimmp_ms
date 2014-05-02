package it.uniroma1.lcl.wimmp.parse.morpho;

import it.uniroma1.lcl.wimmp.*;
import it.uniroma1.lcl.wimmp.parse.EmptyRule;

public class MalayNoun extends MorphoEntry {

    public MalayNoun(String lemma) {
        super(lemma, MorphoEntry.POS.NOUN, true, new EmptyRule());
    }

}
