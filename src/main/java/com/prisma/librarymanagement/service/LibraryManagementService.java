package com.prisma.librarymanagement.service;

import com.prisma.librarymanagement.entity.Library;
import com.prisma.librarymanagement.entity.User;
import com.prisma.librarymanagement.exception.BadRequestException;
import com.prisma.librarymanagement.exception.NoBorrowerFoundException;
import com.prisma.librarymanagement.exception.ResourceNotFoundException;
import com.prisma.librarymanagement.repository.LibraryManagementRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Validated
@RequiredArgsConstructor
public class LibraryManagementService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LibraryManagementService.class);
    @Autowired
    private LibraryManagementRepository repository;

    public Optional<Library> getLibrary() {
        Optional<Library> library = repository.getLibrary();
        if (library.isEmpty()) {
            LOGGER.error("No CSV file found");
            throw new ResourceNotFoundException("No CSV file found");
        }
        return library;
    }
    public List<String> getUserBorrowedOneBookAtleast() {
        List<String> userListBorrowedBook = repository.getUserBorrowedOneBookAtleast();
        if (userListBorrowedBook.isEmpty()) {
            LOGGER.error("Non of the User Borrowed Book");
            throw new NoBorrowerFoundException("Non of the User Borrowed Book");
        }
        return userListBorrowedBook;
    }
    public Set<User> getUserBorrowBookOnDate(Optional<String> date) {
        if (date.isEmpty()) {
            throw new BadRequestException("Date is Required to Fetch Borrower List");
        }
        Set<User> userListBorrowedBookOnDate = repository.getUserBorrowBookOnDate(date.get());
        if (userListBorrowedBookOnDate.isEmpty()) {
            throw new NoBorrowerFoundException("Non of the User Borrowed Book On Given Date");
        }
        return userListBorrowedBookOnDate;
    }

}
