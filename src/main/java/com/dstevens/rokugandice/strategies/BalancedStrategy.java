package com.dstevens.rokugandice.strategies;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.dstevens.rokugandice.Die;

public class BalancedStrategy implements CheckStrategy {

    @Override
    public List<Die> selectDice(List<Die> available, long tn, long numberToKeep) {
        List<Die> keptDice = new ArrayList<>();

        List<Die> reroll = available.stream().filter(die -> die.isReroll()).collect(Collectors.toList());
        available.removeAll(reroll);
        List<Die> rerollNoStrife = reroll.stream().filter(die -> !die.isStrife()).collect(Collectors.toList());
        reroll.removeAll(rerollNoStrife);

        List<Die> success = available.stream().filter(die -> die.isSuccess()).collect(Collectors.toList());
        available.removeAll(success);
        List<Die> successWithOpportunity = success.stream().filter(die -> die.isOpportunity())
                .collect(Collectors.toList());
        success.removeAll(successWithOpportunity);
        List<Die> successNoStrife = success.stream().filter(die -> !die.isStrife()).collect(Collectors.toList());
        success.removeAll(successNoStrife);

        List<Die> opprtunity = available.stream().filter(die -> die.isOpportunity()).collect(Collectors.toList());
        available.removeAll(opprtunity);
        List<Die> opprtunityNoStrife = opprtunity.stream().filter(die -> !die.isStrife()).collect(Collectors.toList());
        opprtunity.removeAll(opprtunityNoStrife);

        while (keptDice.size() < numberToKeep) {
            if (countSuccess(keptDice) < tn) {
                if (!rerollNoStrife.isEmpty()) {
                    keptDice.add(rerollNoStrife.remove(0));
                } else if (!reroll.isEmpty()) {
                    keptDice.add(reroll.remove(0));
                } else if (!successWithOpportunity.isEmpty()) {
                    keptDice.add(successWithOpportunity.remove(0));
                } else if (!successNoStrife.isEmpty()) {
                    keptDice.add(successNoStrife.remove(0));
                } else if (!success.isEmpty()) {
                    keptDice.add(success.remove(0));
                } else if (!opprtunityNoStrife.isEmpty()) {
                    keptDice.add(opprtunityNoStrife.remove(0));
                } else if (!opprtunity.isEmpty()) {
                    keptDice.add(opprtunity.remove(0));
                } else if (!available.isEmpty()) {
                    keptDice.add(available.remove(0));
                } else {
                    throw new IllegalStateException("Tried to keep more dice than were rolled!");
                }
            } else {
                if (!successWithOpportunity.isEmpty()) {
                    keptDice.add(successWithOpportunity.remove(0));
                } else if (!opprtunityNoStrife.isEmpty()) {
                    keptDice.add(opprtunityNoStrife.remove(0));
                } else if (!opprtunity.isEmpty()) {
                    keptDice.add(opprtunity.remove(0));
                } else if (!rerollNoStrife.isEmpty()) {
                    keptDice.add(rerollNoStrife.remove(0));
                } else if (!reroll.isEmpty()) {
                    keptDice.add(reroll.remove(0));
                } else if (!successNoStrife.isEmpty()) {
                    keptDice.add(successNoStrife.remove(0));
                } else if (!success.isEmpty()) {
                    keptDice.add(success.remove(0));
                } else if (!available.isEmpty()) {
                    keptDice.add(available.remove(0));
                } else {
                    throw new IllegalStateException("Tried to keep more dice than were rolled!");
                }
            }
        }
        return keptDice;
    }

    private long countSuccess(List<Die> keptDice) {
        return keptDice.stream().map(die -> die.getRolled()).filter(face -> face.isSuccess()).count();
    }
}
