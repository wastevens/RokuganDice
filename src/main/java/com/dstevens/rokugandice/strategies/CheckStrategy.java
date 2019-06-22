package com.dstevens.rokugandice.strategies;

import java.util.List;

import com.dstevens.rokugandice.Die;

public interface CheckStrategy {

    List<Die> selectDice(List<Die> available, long tn, long numberToKeep);
    
}
