package com.example.flashcard.flashcard;

import java.util.*;

public class FlashCardRandom {
    private static final Random random = new Random();

    public static Operand getOperand(int bound) {
        char op = random.nextInt(2) == 0 ? '+' : '-'; // 50% for both add and minus
        return new Operand(op, random.nextInt(bound), random.nextInt(bound));
    }
}
