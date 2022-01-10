package com.prisma.librarymanagement.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Book {
    @JsonProperty(value = "Title")
    private String title;
    @JsonProperty(value = "Author")
    private String author;
    @JsonProperty(value = "Genre")
    private String genre;
    @JsonProperty(value = "Publisher")
    private String publisher;
}
