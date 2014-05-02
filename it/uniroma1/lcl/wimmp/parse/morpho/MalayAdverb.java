package it.uniroma1.lcl.wimmp.parse.morpho;

import it.uniroma1.lcl.wimmp.*;
import it.uniroma1.lcl.wimmp.parse.*;

public class MalayAdverb extends MorphoEntry {

    public MalayAdverb(String lemma) {
        super(lemma, MorphoEntry.POS.ADVERB, true, new EmptyRule());
    }

}
