package com.itvillage.section06.class00;

import java.util.zip.DataFormatException;

public class CharacterValidator {
    public static void isAlphabeticCharacter(char ch) throws DataFormatException {
        if (!Character.isAlphabetic(ch)) {
            throw new DataFormatException("Not Alphabetic");
        }
    }
}
