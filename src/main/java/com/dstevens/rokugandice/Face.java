package com.dstevens.rokugandice;

import java.util.Arrays;
import java.util.List;

public class Face {

    private List<Symbol> symbols;

    Face(Symbol...symbols) {
        this.symbols = Arrays.asList(symbols);
    }
    
    public boolean isReroll() {
        return symbols.contains(Symbol.REROLL);
    }
    
    public boolean isSuccess() {
        return symbols.contains(Symbol.REROLL) || symbols.contains(Symbol.SUCCESS);
    }
    
    public boolean isOpportunity() {
        return symbols.contains(Symbol.OPPORTUNITY);
    }
    
    public boolean isStrife() {
        return symbols.contains(Symbol.STRIFE);
    }

    @Override
    public String toString() {
        return "Face [symbols=" + symbols + "]";
    }
    
    
    
}
