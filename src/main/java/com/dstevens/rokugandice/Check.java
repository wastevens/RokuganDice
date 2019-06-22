package com.dstevens.rokugandice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.dstevens.rokugandice.strategies.CheckStrategy;

public class Check {

    private List<Die> ringDice;
    private List<Die> skillDice;
    private long numberToKeep;
    private long tn;

    public Check(List<Die> ringDice, List<Die> skillDice, long numberToKeep, long tn) {
        this.ringDice = ringDice;
        this.skillDice = skillDice;
        this.numberToKeep = numberToKeep;
        this.tn = tn;
    }
    
    public CheckResult rollCheck(CheckStrategy strategy) {
        List<Die> rolledDice = new ArrayList<>();
        rolledDice.addAll(ringDice);
        rolledDice.addAll(skillDice);
        rolledDice.stream().forEach(die -> die.roll());
        
        List<Die> keptDice = strategy.selectDice(rolledDice, tn, numberToKeep);
        
        //Reroll all kept reroll dice, adding results to kept dice, until norerolls are unrolled
        while(keptDice.stream().anyMatch(die -> die.isReroll())) {
            keptDice.addAll(keptDice.stream().filter(die -> die.isReroll()).map(die -> die.reroll()).collect(Collectors.toList()));
        }
        
        return new CheckResult(keptDice, tn);
    }
}
