package com.dstevens.rokugandice;

import static com.dstevens.rokugandice.Symbol.OPPORTUNITY;
import static com.dstevens.rokugandice.Symbol.REROLL;
import static com.dstevens.rokugandice.Symbol.STRIFE;
import static com.dstevens.rokugandice.Symbol.SUCCESS;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;

public class Die {

    private static final List<Face> SKILL_FACES = Arrays.asList(new Face(), new Face(), new Face(OPPORTUNITY),
            new Face(OPPORTUNITY), new Face(OPPORTUNITY), new Face(SUCCESS, STRIFE), new Face(SUCCESS, STRIFE),
            new Face(SUCCESS), new Face(SUCCESS), new Face(SUCCESS, OPPORTUNITY), new Face(REROLL, STRIFE),
            new Face(REROLL));
    private static final List<Face> RING_FACES = Arrays.asList(new Face(), new Face(OPPORTUNITY, STRIFE),
            new Face(OPPORTUNITY), new Face(SUCCESS, STRIFE), new Face(SUCCESS), new Face(REROLL, STRIFE));
    
    public static Die ringDie() {
        return new Die(RING_FACES);
    }
    
    public static Die skillDie() {
        return new Die(SKILL_FACES);
    }
    
    private List<Face> faces;
    private Face rolled;
    private boolean rerolled = false;


    private Die(List<Face> faces) {
        this.faces = faces;
    }

    public Face roll() {
        this.rolled = faces.get(RandomUtils.nextInt(0, this.faces.size()));
        return this.rolled;
    }

    public Face getRolled() {
        return rolled;
    }

    public Die reroll() {
        this.rerolled = true;
        Die reroll = new Die(faces);
        reroll.roll();
        return reroll;
    }

    public boolean isReroll() {
        return rolled.isReroll() && !this.rerolled;
    }
    
    public boolean isSuccess() {
        return rolled.isSuccess();
    }
    
    public boolean isOpportunity() {
        return rolled.isOpportunity();
    }
    
    public boolean isStrife() {
        return rolled.isStrife();
    }
    
    @Override
    public String toString() {
        return "Die[" + rolled + "]";
    }
}
