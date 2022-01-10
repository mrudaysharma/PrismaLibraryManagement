package com.prisma.librarymanagement.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Borrower {
    @JsonProperty(value = "Borrower")
    private String userName;
    @JsonProperty(value = "Book")
    private String bookTitle;
    @JsonProperty(value = "borrowed from")
    private String borrowedFrom;
    @JsonProperty(value = "borrowed to")
    private String borrowedTo;
}

