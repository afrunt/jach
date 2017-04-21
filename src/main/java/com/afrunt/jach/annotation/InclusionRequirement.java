package com.afrunt.jach.annotation;

/**
 * @author Andrii Frunt
 */
public enum InclusionRequirement {
    /**
     * A “Mandatory” field contains information necessary to ensure the proper routing and/or posting of an ACH entry.
     * The ACH Operator will reject any entry or batch, which does not have appropriate values in a Mandatory field.
     */
    MANDATORY,

    /**
     * The omission of a “Required” field will not cause an entry reject at the ACH Operator, but may cause a reject at
     * the RDFI. For example, if the DFI Account Number field in the Entry Detail ACHRecord is omitted, the RDFI may return
     * the entry because it cannot be posted. You should include appropriate values in “Required” fields to avoid
     * processing and control problems at the RDFI.
     */
    REQUIRED,

    /**
     * The inclusion or omission of an “Optional” data field is at the discretion of the Originator. If you do include
     * optional fields, the RDFI must include them in any return.
     */
    OPTIONAL,

    /**
     * Mark the fields, that should always be blank
     */
    BLANK
}
