package com.prisma.librarymanagement.repository;

import com.prisma.librarymanagement.entity.Library;
import com.prisma.librarymanagement.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface LibraryManagementRepository {
    Optional<Library> getLibrary();
    List<String> getUserBorrowedOneBookAtleast();
    public Set<User> getUserBorrowBookOnDate(String date);
}
