package it.uniroma1.lcl.wimmp.parse.morpho;

import it.uniroma1.lcl.wimmp.*;
import it.uniroma1.lcl.wimmp.parse.*;

public class MalayInterjection extends MorphoEntry {

    public MalayInterjection(String lemma) {
        super(lemma, MorphoEntry.POS.INTERJECTION, true, new EmptyRule());
    }

}
