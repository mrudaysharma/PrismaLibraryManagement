package com.prisma.librarymanagement.memorystorage;

import com.prisma.librarymanagement.entity.Book;
import com.prisma.librarymanagement.entity.Borrower;
import com.prisma.librarymanagement.entity.Library;
import com.prisma.librarymanagement.entity.User;
import com.prisma.librarymanagement.exception.OperationFailedException;
import com.prisma.librarymanagement.utils.CsvUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class LibraryMemoryStorage {

    private final String CSV_PATH = "classpath:csvdata/*.csv";
    private final String BOOKS = "books";
    private final String USER = "user";
    private final String BORROWED = "borrowed";

    @Autowired
    private ResourceLoader resourceLoader;

    private Library library;

    public Library loadLibrary() throws IOException {
        library = new Library();
        Resource[] resources = getCSVInputStream(CSV_PATH);
        if(resources==null)
        {
            throw new ResourceNotFoundException("CSV Resources Not Found");
        }

            for (Resource resource : resources) {
                if (resource.getFilename().contains(BOOKS)) {
                    List<Book> books = CsvUtils.read(Book.class, resource.getInputStream());
                    library.setBooks(books);
                } else if (resource.getFilename().contains(USER)) {
                    List<User> users = CsvUtils.read(User.class, resource.getInputStream());
                    library.setUsers(users);
                } else if (resource.getFilename().contains(BORROWED)) {
                    List<Borrower> borrowers = CsvUtils.read(Borrower.class, resource.getInputStream());
                    library.setBorrowers(borrowers);
                }

            }
            if(library==null)
            {
                throw new OperationFailedException("Failed to load CSV from resources");
            }
        return library;
    }

    private Resource[] getCSVInputStream(String csvFilePath) throws IOException {
        return ResourcePatternUtils.getResourcePatternResolver(resourceLoader).getResources(csvFilePath);
    }
}
