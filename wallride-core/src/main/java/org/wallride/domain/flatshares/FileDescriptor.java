package org.wallride.domain.flatshares;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;



public class FileDescriptor {

    private Integer id;
    private String uuid;

    @NotEmpty
    private String originalName;

    @NotEmpty
    private String filePath;
    private String mimeType;
    private Long fileSize;

    @NotNull
    private Timestamp createdOn;

    @NotNull
    private Integer createdBy;

    @NotNull
    private Timestamp modifiedOn;

    @NotNull
    private Integer modifiedBy;
}
