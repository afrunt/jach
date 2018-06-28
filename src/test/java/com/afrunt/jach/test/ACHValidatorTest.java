package com.afrunt.jach.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.afrunt.jach.domain.EntryDetail;
import com.afrunt.jach.domain.detail.POSEntryDetail;
import com.afrunt.jach.validate.ACHValidationResult;
import com.afrunt.jach.validate.ACHValidator;

/**
 *
 * @author ilyakharlamov
 *
 */
public class ACHValidatorTest {
    private ACHValidator validator;

    @Before
    public void setUp() {
        validator = new ACHValidator();
    }

    @Test
    public void test_valid_routing_number_should_produce_no_validation_errors() {
        EntryDetail entryDetail = new POSEntryDetail();
        entryDetail.setReceivingDfiIdentification("12345678");
        entryDetail.setCheckDigit((short) 0);
        ACHValidationResult res = validator.validate(entryDetail);
        assertTrue(res.isValid());
    }

    @Test
    public void test_invalid_routing_number_should_produce_no_validation_errors() {
        EntryDetail entryDetail = new POSEntryDetail();
        entryDetail.setReceivingDfiIdentification("12345678");
        entryDetail.setCheckDigit((short) 1);
        ACHValidationResult res = validator.validate(entryDetail);
        assertFalse(res.isValid());
    }

}
