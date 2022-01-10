package com.prisma.librarymanagement.repository;

import com.prisma.librarymanagement.entity.Library;
import com.prisma.librarymanagement.exception.ResourceNotFoundException;
import com.prisma.librarymanagement.memorystorage.LibraryMemoryStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Optional;

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

    @PostConstruct
    public void init() throws IOException {
        library = Optional.ofNullable(storage.loadLibrary());
        if (library.isEmpty()) {
            LOGGER.error(NO_CSV_FILE_FOUND);
            throw new ResourceNotFoundException(NO_CSV_FILE_FOUND);
        }
    }
}
