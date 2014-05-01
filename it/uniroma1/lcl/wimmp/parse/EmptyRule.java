package it.uniroma1.lcl.wimmp.parse;

import java.util.*;
import it.uniroma1.lcl.wimmp.*;

public class EmptyRule implements MorphoRule {
    
    public List<MorphoForm> getForms() {
        return new ArrayList();
    }
}
