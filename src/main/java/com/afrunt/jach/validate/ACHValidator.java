package com.afrunt.jach.validate;

import com.afrunt.jach.domain.EntryDetail;

public class ACHValidator {

    public ACHValidationResult validate(EntryDetail entryDetail) {
        short[] digits = getDigits(entryDetail.getReceivingDfiIdentification());
        short expectedCheckSum = checksumAlgorithm(digits);
        ACHValidationResult res;
        short actualCheckDigit = entryDetail.getCheckDigit();
        if (expectedCheckSum == actualCheckDigit) {
            res = new ACHValidationResult(true);
        } else {
            res = new ACHValidationResult(false);
        }
        return res;
    }

    private static short[] getDigits(String value) {
        int len = value.length();
        short[] digits = new short[value.length()];
        for (int i = 0; i < len; i++) {
            digits[i] = Short.parseShort(value.substring(i, i + 1), 10);
        }
        return digits;
    }

    public static short checksumAlgorithm(short[] digits) {

        if (digits.length < 8) {
            throw new IllegalArgumentException("cannot calculate a check digit with less than 8 input digits");
        }
        int sum = 0;
        sum += digits[0] * 3;
        sum += digits[1] * 7;
        sum += digits[2] * 1;
        sum += digits[3] * 3;
        sum += digits[4] * 7;
        sum += digits[5] * 1;
        sum += digits[6] * 3;
        sum += digits[7] * 7;

        int nextHighestMultipleOf10 = sum;

        while (nextHighestMultipleOf10 % 10 != 0) {
            nextHighestMultipleOf10++;
        }

        int checkDigit = nextHighestMultipleOf10 - sum;

        return (short) checkDigit;
    }

}
