package org.wallride.domain.flatshares;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

public class Property {
    private Integer id;

    @NotNull
    private Address address;

    @NotEmpty
    private String title;

    private String description;
    private Integer size;

    @NotNull
    private Float rental;

    private String referenceNumber;

    private String url;

    private FileDescriptor mainImage;
    private List<FileDescriptor> images;

    private String email;
    private String phone;


    @NotNull
    private Timestamp createdOn;

    @NotNull
    private Integer createdBy;

    @NotNull
    private Timestamp modifiedOn;

    @NotNull
    private Integer modifiedBy;
}