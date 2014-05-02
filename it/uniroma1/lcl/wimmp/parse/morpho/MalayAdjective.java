package it.uniroma1.lcl.wimmp.parse.morpho;

import it.uniroma1.lcl.wimmp.*;
import it.uniroma1.lcl.wimmp.parse.EmptyRule;

public class MalayAdjective extends MorphoEntry {
    
    public MalayAdjective(String lemma) {
        super(lemma, MorphoEntry.POS.ADJECTIVE, true, new EmptyRule());
    }

}
