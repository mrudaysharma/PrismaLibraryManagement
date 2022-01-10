package com.prisma.librarymanagement;

import com.prisma.librarymanagement.controller.LibraryManagementController;
import com.prisma.librarymanagement.entity.Book;
import com.prisma.librarymanagement.entity.Borrower;
import com.prisma.librarymanagement.entity.Library;
import com.prisma.librarymanagement.entity.User;
import com.prisma.librarymanagement.memorystorage.LibraryMemoryStorage;
import com.prisma.librarymanagement.repository.LibraryManagementRepositoryImpl;
import com.prisma.librarymanagement.service.LibraryManagementService;
import com.prisma.librarymanagement.utils.CsvUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LibraryManagementController.class)
class LibrarymanagementApplicationTest {

    private static final String API_BASE_PATH_V1 = "/library";


    @TestConfiguration
    static class LibraryManagementServiceTestContextConfiguration {
        @Bean
        public LibraryManagementService libraryManagementService() {
            return new LibraryManagementService();
        }
    }

    @Autowired
    private LibraryManagementService libraryManagementService;

    @MockBean
    private LibraryManagementRepositoryImpl libraryManagementRepository;

    @Autowired
    private MockMvc mockMvc;

    private List<User> users;
    private List<Borrower> borrowers;
    private List<Book> books;
    private Library library;

    private LibraryMemoryStorage libraryMemoryStorage;


    @BeforeEach
    public void setUp() throws IOException {
        libraryMemoryStorage = new LibraryMemoryStorage();
        library = libraryMemoryStorage.loadLibrary();
    }

    @Test
    public void whenItemAdd_thenLibraryManagementHasItem() throws Exception {
        User testUser = new User();
        testUser.setName("Aexi");
        testUser.setFirstName("Liam");
        testUser.setMemberSince("01/01/2010");
        testUser.setMemberTill("");
        testUser.setGender('m');
        assertNotNull(library.getUsers());
        when(libraryManagementRepository.getLibrary()).thenReturn(Optional.ofNullable(library));
        mockMvc.perform(get(API_BASE_PATH_V1 + "/getLibrary")).andExpect(status().isOk());
        assertThat(libraryManagementService.getLibrary().isPresent()).isTrue();
        assertThat(libraryManagementService.getLibrary().get().getUsers()).contains(testUser);
    }
}