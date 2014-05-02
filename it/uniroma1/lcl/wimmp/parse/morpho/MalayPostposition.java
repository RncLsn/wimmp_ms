package it.uniroma1.lcl.wimmp.parse.morpho;

import it.uniroma1.lcl.wimmp.*;
import it.uniroma1.lcl.wimmp.parse.*;

public class MalayPostposition extends MorphoEntry {

    public MalayPostposition(String lemma) {
        super(lemma, MorphoEntry.POS.POSTPOSITION, true, new EmptyRule());
    }

}
