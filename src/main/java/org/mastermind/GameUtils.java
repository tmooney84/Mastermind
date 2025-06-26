package org.mastermind;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import static org.mastermind.GameState.*;

public class GameUtils {
    public static boolean isValidCode(String code)
    {
        if(code.length() != 4){
            return false;
        }
        Set<Character> seenDigits = new HashSet<>();
        int length = code.length();
        for(int i = 0; i < length; i++)
        {
            char c = code.charAt(i);

            if(c < '0' || c > '8'){
                return false;
            }

            if(seenDigits.contains(c)){
                return false;
            }
            seenDigits.add(c);
        }
        return true;
    }

public static String randomCode()
{
    ArrayList<Integer> digits = new ArrayList<>();

    for(int i = 0; i <= 8; i++)
    {
        digits.add(i);
    }

    //same seed for replaying game state
    Collections.shuffle(digits);

    StringBuilder code = new StringBuilder();
    int NUM_PIECES = GameState.getNumPieces();
    for(int i = 0; i < NUM_PIECES; i++)
    {
        code.append(digits.get(i));
    }

    return code.toString();
}

public static boolean isValidNum(String strNum)
{
    if(strNum == null)
    {
       return false;
    }
    try{
        int i = Integer.parseInt(strNum);
    }catch (NumberFormatException nfe){
        return false;
    }
    return true;
}
}
