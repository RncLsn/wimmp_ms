package it.uniroma1.lcl.wimmp.parse.morpho;

import it.uniroma1.lcl.wimmp.*;
import it.uniroma1.lcl.wimmp.parse.*;

public class MalayPreposition extends MorphoEntry {

    public MalayPreposition(String lemma) {
        super(lemma, MorphoEntry.POS.PREPOSITION, true, new EmptyRule());
    }

}
