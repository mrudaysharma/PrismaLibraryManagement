package com.prisma.librarymanagement.repository;

import com.prisma.librarymanagement.entity.Borrower;
import com.prisma.librarymanagement.entity.Library;
import com.prisma.librarymanagement.entity.User;
import com.prisma.librarymanagement.exception.NoBorrowerFoundException;
import com.prisma.librarymanagement.exception.ResourceNotFoundException;
import com.prisma.librarymanagement.memorystorage.LibraryMemoryStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class LibraryManagementRepositoryImpl implements LibraryManagementRepository {

    public static final String NO_CSV_FILE_FOUND = "No CSV file found";
    private static final Logger LOGGER = LoggerFactory.getLogger(LibraryManagementRepositoryImpl.class);
    private Optional<Library> library;

    @Autowired
    private LibraryMemoryStorage storage;

    @Override

    public Optional<Library> getLibrary() {
        if (library.isEmpty()) {
            LOGGER.error(NO_CSV_FILE_FOUND);
            throw new ResourceNotFoundException(NO_CSV_FILE_FOUND);
        }
        return library;
    }
    @Override
    public List<String> getUserBorrowedOneBookAtleast() {
        List<Borrower> borrowers = library.get().getBorrowers();
        List<User> users = library.get().getUsers();
        Set<String> userNames = users.stream().filter(user -> user.getFirstName() != null && user.getName() != null)
                .map(user -> String.format("%s,%s", user.getName(), user.getFirstName()))
                .collect(Collectors.toSet());
        Set<String> borrowersName = borrowers.stream().filter(borrower -> borrower.getUserName() != null)
                .map(borrower -> String.format("%s", borrower.getUserName()))
                .collect(Collectors.toSet());
        List<String> listUsersBorrowedBook = userNames.stream()
                .filter(element -> borrowersName.contains(element))
                .collect(Collectors.toList());
        if (listUsersBorrowedBook.isEmpty()) {
            throw new NoBorrowerFoundException("Non of the Listed User Borrowed Book");
        }
        return listUsersBorrowedBook;

    }

    @PostConstruct
    public void init() throws IOException {
        library = Optional.ofNullable(storage.loadLibrary());
        if (library.isEmpty()) {
            LOGGER.error(NO_CSV_FILE_FOUND);
            throw new ResourceNotFoundException(NO_CSV_FILE_FOUND);
        }
    }
}
