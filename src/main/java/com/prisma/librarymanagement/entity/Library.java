package com.prisma.librarymanagement.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
public class Library {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Book> books;
    private List<User> users;
    private List<Borrower> borrowers;
}
