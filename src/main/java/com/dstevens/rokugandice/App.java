package com.dstevens.rokugandice;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.dstevens.rokugandice.strategies.BalancedStrategy;
import com.dstevens.rokugandice.strategies.CheckStrategy;

public class App {

    public static void main(String a[]) {
        int numberOfChecks = 10000;
        
        long minTn = 1;
        long maxTn = 5;
        long minRingDieCount = 1;
        long maxRingDieCount = 5;
        long minSkillDieCount = 0;
        long maxSkillDieCount = 5;
        
        CheckStrategy strategy = new BalancedStrategy();
        
        System.out.println("tn,ringDice,skillDice,successPercentage,avgSuccess,avgOppurtunities,avgStrife");
        for(long tn=minTn;tn <= maxTn;tn++) {
            for(long ringDiceCount = minRingDieCount;ringDiceCount <= maxRingDieCount;ringDiceCount++) {
                for(long skillDiceCount = minSkillDieCount;skillDiceCount <= maxSkillDieCount;skillDiceCount++) {
                    List<CheckResult> results = new ArrayList<>();
                    for(int i=0;i<numberOfChecks;i++) {
                        List<Die> ringDice = getRingDice(ringDiceCount);
                        List<Die> skillDice = getSkillDice(skillDiceCount);
                        int numberToKeep = ringDice.size();
                        
                        CheckResult rollCheck = new Check(ringDice, skillDice, numberToKeep, tn).rollCheck(strategy);
                        results.add(rollCheck);
                    }
                    System.out.println(tn + "," + ringDiceCount +"," + skillDiceCount + "," + displayAverage(results));
                }
            }
            
        }
        
    }

    private static List<Die> getRingDice(long count) {
        List<Die> dice = new ArrayList<>();
        for(int j=0;j<count;j++) {
            dice.add(Die.ringDie());
        }
        return dice;
    }
    
    private static List<Die> getSkillDice(long count) {
        List<Die> dice = new ArrayList<>();
        for(int j=0;j<count;j++) {
            dice.add(Die.skillDie());
        }
        return dice;
    }

    private static String displayAverage(List<CheckResult> results) {
        long isSuccessfulCount = results.stream().filter(result -> result.isSuccess()).count();
        long successCount = results.stream().collect(Collectors.summingLong(result -> result.getSuccesses()));
        long opportunityCount = results.stream().collect(Collectors.summingLong(result -> result.getOpportunities()));
        long strifeCount = results.stream().collect(Collectors.summingLong(result -> result.getStrife()));
        
        BigDecimal denominator = new BigDecimal(results.size());
        BigDecimal successPercentage = new BigDecimal(isSuccessfulCount).divide(denominator, 3, RoundingMode.HALF_UP);
        BigDecimal avgSuccess = new BigDecimal(successCount).divide(denominator, 3, RoundingMode.HALF_UP);
        BigDecimal avgOpportunities = new BigDecimal(opportunityCount).divide(denominator, 3, RoundingMode.HALF_UP);
        BigDecimal avgStrife = new BigDecimal(strifeCount).divide(denominator, 3, RoundingMode.HALF_UP);
        
        return successPercentage.toPlainString() + "," + avgSuccess.toPlainString() + "," + avgOpportunities.toPlainString() + "," + avgStrife.toPlainString();
        
    }
    
}
