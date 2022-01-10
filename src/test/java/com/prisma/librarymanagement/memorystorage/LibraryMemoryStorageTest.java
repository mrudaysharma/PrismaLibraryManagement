package com.prisma.librarymanagement.memorystorage;

import com.prisma.librarymanagement.entity.Book;
import com.prisma.librarymanagement.entity.Borrower;
import com.prisma.librarymanagement.entity.Library;
import com.prisma.librarymanagement.entity.User;
import com.prisma.librarymanagement.utils.CsvUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LibraryMemoryStorageTest {



    private LibraryMemoryStorage libraryMemoryStorage;

    private Library library;

    @BeforeEach
    public void setUp() throws IOException {
        libraryMemoryStorage = new LibraryMemoryStorage();
        library = libraryMemoryStorage.loadLibrary();
    }

    @Test
    public void whenSupplyUserCSVDataToUitls_ReturnsUserObjects()  {
        User testUser = new User();
        testUser.setName("Aexi");
        testUser.setFirstName("Liam");
        testUser.setMemberSince("01/01/2010");
        testUser.setMemberTill("");
        testUser.setGender('m');
        assertNotNull(library.getUsers());
        assertThat(library.getUsers()).contains(testUser);
    }

    @Test
    public void whenSupplyBorrowerCSVDataToUitls_ReturnsBorrowerObjects()  {
        Borrower borrowerTest = new Borrower();
        borrowerTest.setUserName("Aexi,Liam");
        borrowerTest.setBookTitle("Complete Sherlock Holmes, The - Vol II");
        borrowerTest.setBorrowedFrom("05/09/2015");
        borrowerTest.setBorrowedTo("05/31/2015");
        assertNotNull(library.getBorrowers());
        assertThat(library.getBorrowers()).contains(borrowerTest);
    }
    @Test
    public void whenSupplyBookCSVDataToUitls_ReturnsBookObjects() {
        Book bookTest = new Book();
        bookTest.setAuthor("Drucker, Peter");
        bookTest.setTitle("Age of Discontuinity, The");
        bookTest.setGenre("economics");
        bookTest.setPublisher("Random House");
        assertNotNull(library.getBooks());
        assertThat(library.getBooks()).contains(bookTest);
    }

}