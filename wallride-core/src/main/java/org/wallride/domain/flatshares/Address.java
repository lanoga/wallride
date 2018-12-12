package org.wallride.domain.flatshares;


import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


public class Address {
    private Integer id;
    private String address1;
    private String address2;
    private String address3;
    private String city;
    private String zip;
    private String country;

    @NotNull
    private Timestamp createdOn;

    @NotNull
    private Integer createdBy;

    @NotNull
    private Timestamp modifiedOn;

    @NotNull
    private Integer modifiedBy;
}
