package com.dstevens.rokugandice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    
    public CheckResult rollCheck() {
        List<Die> keptDice = new ArrayList<>();
        List<Die> rolledDice = new ArrayList<>();

        rolledDice.addAll(ringDice);
        rolledDice.addAll(skillDice);
        rolledDice.stream().forEach(die -> die.roll());
        
        List<Die> reroll = rolledDice.stream().filter(die -> die.isReroll()).collect(Collectors.toList());
        rolledDice.removeAll(reroll);
        List<Die> rerollNoStrife = reroll.stream().filter(die -> !die.isStrife()).collect(Collectors.toList());
        reroll.removeAll(rerollNoStrife);
        
        List<Die> success = rolledDice.stream().filter(die -> die.isSuccess()).collect(Collectors.toList());
        rolledDice.removeAll(success);
        List<Die> successWithOpportunity = success.stream().filter(die -> die.isOpportunity()).collect(Collectors.toList());
        success.removeAll(successWithOpportunity);
        List<Die> successNoStrife = success.stream().filter(die -> !die.isStrife()).collect(Collectors.toList());
        success.removeAll(successNoStrife);
        
        List<Die> opprtunity = rolledDice.stream().filter(die -> die.isOpportunity()).collect(Collectors.toList());
        rolledDice.removeAll(opprtunity);
        List<Die> opprtunityNoStrife = opprtunity.stream().filter(die -> !die.isStrife()).collect(Collectors.toList());
        opprtunity.removeAll(opprtunityNoStrife);
        
        while(keptDice.size() < numberToKeep) {
            if(countSuccess(keptDice) < tn) {
                if(!rerollNoStrife.isEmpty()) {
                    keptDice.add(rerollNoStrife.remove(0));
                } else if(!reroll.isEmpty()) {
                    keptDice.add(reroll.remove(0));
                } else if(!successWithOpportunity.isEmpty()) {
                    keptDice.add(successWithOpportunity.remove(0));
                } else if(!successNoStrife.isEmpty()) {
                    keptDice.add(successNoStrife.remove(0));
                } else if(!success.isEmpty()) {
                    keptDice.add(success.remove(0));
                } else if(!opprtunityNoStrife.isEmpty()) {
                    keptDice.add(opprtunityNoStrife.remove(0));
                } else if(!opprtunity.isEmpty()) {
                    keptDice.add(opprtunity.remove(0));
                } else if(!rolledDice.isEmpty()){
                    keptDice.add(rolledDice.remove(0));
                } else {
                    throw new IllegalStateException("Tried to keep more dice than were rolled!");
                }
            } else {
                if(!successWithOpportunity.isEmpty()) {
                    keptDice.add(successWithOpportunity.remove(0));
                } else if(!opprtunityNoStrife.isEmpty()) {
                    keptDice.add(opprtunityNoStrife.remove(0));
                } else if(!opprtunity.isEmpty()) {
                    keptDice.add(opprtunity.remove(0));
                } else if(!rerollNoStrife.isEmpty()) {
                    keptDice.add(rerollNoStrife.remove(0));
                } else if(!reroll.isEmpty()) {
                    keptDice.add(reroll.remove(0));
                } else if(!successNoStrife.isEmpty()) {
                    keptDice.add(successNoStrife.remove(0));
                } else if(!success.isEmpty()) {
                    keptDice.add(success.remove(0));
                } else if(!rolledDice.isEmpty()){
                    keptDice.add(rolledDice.remove(0));
                } else {
                    throw new IllegalStateException("Tried to keep more dice than were rolled!");
                }             
            }
        }
        
        //Reroll all kept reroll dice, adding results to kept dice, until norerolls are unrolled
        while(keptDice.stream().anyMatch(die -> die.isReroll())) {
            keptDice.addAll(keptDice.stream().filter(die -> die.isReroll()).map(die -> die.reroll()).collect(Collectors.toList()));
        }
        
        return new CheckResult(keptDice, tn);
    }

    private long countSuccess(List<Die> keptDice) {
        return keptDice.stream().map(die -> die.getRolled()).filter(face -> face.isSuccess()).count();
    }
    
}
