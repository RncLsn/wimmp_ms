package it.uniroma1.lcl.wimmp.parse.morpho;

import it.uniroma1.lcl.wimmp.*;
import it.uniroma1.lcl.wimmp.parse.EmptyRule;

public class MalayVerb extends MorphoEntry {

    public MalayVerb(String lemma) {
        super(lemma, MorphoEntry.POS.VERB, true, new EmptyRule());
    }
}
