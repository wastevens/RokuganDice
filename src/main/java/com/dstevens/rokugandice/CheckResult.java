package com.dstevens.rokugandice;

import java.util.List;

public class CheckResult {

    private List<Die> keptDice;
    private long tn;
    private long successes;
    private long opportunities;
    private long strife;

    public CheckResult(List<Die> keptDice, long tn) {
        this.keptDice = keptDice;
        this.tn = tn;
        
        this.successes = this.keptDice.stream().filter(die -> die.isSuccess()).count();
        this.opportunities = this.keptDice.stream().filter(die -> die.isOpportunity()).count();
        this.strife = this.keptDice.stream().filter(die -> die.isStrife()).count();
    }
    
    public boolean displayResult() {
        System.out.println("Results");
        System.out.println("Beat TN? " + (successes >= tn));
        System.out.println("Successes: " + successes);
        System.out.println("Opportunities: " + opportunities);
        System.out.println("Strife: " + strife);
        
        return successes >= tn;
    }

    public long getSuccesses() {
        return successes;
    }

    public long getOpportunities() {
        return opportunities;
    }

    public long getStrife() {
        return strife;
    }
    
    public boolean isSuccess() {
        return (successes >= tn);
    }
    
    
}
