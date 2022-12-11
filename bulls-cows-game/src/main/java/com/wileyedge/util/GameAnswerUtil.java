package com.wileyedge.util;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GameAnswerUtil {

    public static String generateAnswer() {
        Set<Integer> set = new HashSet<>(4);

        Random random = new Random();
        while (set.size() < 4) {
            int digit = random.nextInt(1, 10);
            set.add(digit);
        }

        StringBuilder builder = new StringBuilder();
        for (Integer integer : set) {
            builder.append(integer);
        }
        return builder.toString();
    }

    public static String evaluate(String guess, String answer) {

        int bull = 0;
        int cow = 0;
        String result = "";

        for (int i = 0; i < guess.length(); i++) {
            if (guess.charAt(i) == answer.charAt(i)) {
                bull++;
            } else if (answer.contains(String.valueOf(guess.charAt(i)))) {
                cow++;
            }
        }

        if (bull == 4) {
            return "Finished";
        } else {
            result = "e:" + bull + ":" + "p:" + cow;
            return result;
        }
    }
}
