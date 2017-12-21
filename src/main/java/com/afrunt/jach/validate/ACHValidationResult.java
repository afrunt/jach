package com.afrunt.jach.validate;

/**
 * @author ilyakharlamov
 *
 */
public class ACHValidationResult {
    boolean valid;

    public ACHValidationResult(boolean valid) {
        super();
        this.valid = valid;
    }

    public boolean isValid() {
        return valid;
    }
}
