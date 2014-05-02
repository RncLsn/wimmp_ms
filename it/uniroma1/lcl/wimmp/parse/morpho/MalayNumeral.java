package it.uniroma1.lcl.wimmp.parse.morpho;

import it.uniroma1.lcl.wimmp.*;
import it.uniroma1.lcl.wimmp.parse.*;

public class MalayNumeral extends MorphoEntry {

    public MalayNumeral(String lemma) {
        super(lemma, MorphoEntry.POS.NUMERAL, true, new EmptyRule());
    }

}
