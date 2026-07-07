/**
 * Copyright (c) 2024-2028, N.I.B., www.nib-labs.io
 *
 * @author N.I.B. dev team
 */
package org.sample.online.webservices;

import lombok.Data;

@Data
public class AccountInfo {
    private int id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String dateOfBirth;
    private String address;
    private String state;
    private String country;
    private boolean active;
}

