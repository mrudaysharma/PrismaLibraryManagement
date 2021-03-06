package com.prisma.librarymanagement;

import com.prisma.librarymanagement.controller.LibraryManagementController;
import com.prisma.librarymanagement.entity.Book;
import com.prisma.librarymanagement.entity.Borrower;
import com.prisma.librarymanagement.entity.Library;
import com.prisma.librarymanagement.entity.User;
import com.prisma.librarymanagement.exception.BadRequestException;
import com.prisma.librarymanagement.exception.DateFormatException;
import com.prisma.librarymanagement.memorystorage.LibraryMemoryStorage;
import com.prisma.librarymanagement.repository.LibraryManagementRepositoryImpl;
import com.prisma.librarymanagement.service.LibraryManagementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.io.IOException;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    public void whenRequestLibrary_thenLibraryHasItemUserBorrowerBook() throws Exception {
        User testUser = new User();
        testUser.setName("Aexi");
        testUser.setFirstName("Liam");
        testUser.setMemberSince("01/01/2010");
        testUser.setMemberTill("");
        testUser.setGender('m');

        Borrower borrowerTest = new Borrower();
        borrowerTest.setUserName("Aexi,Liam");
        borrowerTest.setBookTitle("Complete Sherlock Holmes, The - Vol II");
        borrowerTest.setBorrowedFrom("05/09/2015");
        borrowerTest.setBorrowedTo("05/31/2015");

        Book bookTest = new Book();
        bookTest.setAuthor("Drucker, Peter");
        bookTest.setTitle("Age of Discontuinity, The");
        bookTest.setGenre("economics");
        bookTest.setPublisher("Random House");

        assertNotNull(library.getUsers());
        when(libraryManagementRepository.getLibrary()).thenReturn(Optional.ofNullable(library));
        mockMvc.perform(get(API_BASE_PATH_V1 + "/getLibrary")).andExpect(status().isOk());
        assertThat(libraryManagementService.getLibrary().isPresent()).isTrue();
        assertThat(libraryManagementService.getLibrary().get().getUsers()).contains(testUser);
        assertThat(libraryManagementService.getLibrary().get().getBorrowers()).contains(borrowerTest);
        assertThat(libraryManagementService.getLibrary().get().getBooks()).contains(bookTest);
    }

    @Test
    public void whenRequestLibraryBorrower_thenResponseUserBorrowedAtlestOneBook() throws Exception {
        List<String> listOfUserBorrowedOneBookAtlast = new ArrayList<>(List.of("Chish,Elijah, Jayi,William, Zhungwang,Ava, Barret-Kingsley,Emma, Augusta,Olivia, Odum,Oliver, Zhungwang,Noah, Jumummaaq,James, Oomxii,Sophia, Ghaada,Charlotte, Aexi,Liam"));

        assertNotNull(library.getUsers());
        when(libraryManagementRepository.getUserBorrowedOneBookAtleast()).thenReturn(listOfUserBorrowedOneBookAtlast);
        mockMvc.perform(get(API_BASE_PATH_V1 + "/getUserAtleastBorrowOneBook")).andExpect(status().isOk());
        assertThat(libraryManagementService.getUserBorrowedOneBookAtleast()).isNotEmpty();
        assertThat(libraryManagementService.getUserBorrowedOneBookAtleast()).isEqualTo(listOfUserBorrowedOneBookAtlast);
    }
    @Test
    public void whenRequestLibraryBorrower_thenResponseUserBorrowedBookOnDate() throws Exception {

        String date = "06/23/2020";
        User testUser = new User();
        testUser.setName("Aexi");
        testUser.setFirstName("Liam");
        testUser.setMemberSince("01/01/2010");
        testUser.setMemberTill("");
        testUser.setGender('m');
        Set<User> userList = new HashSet<>();
        userList.add(testUser);

        assertNotNull(library.getUsers());
        when(libraryManagementRepository.getUserBorrowBookOnDate(date)).thenReturn(userList);
        mockMvc.perform(get(API_BASE_PATH_V1 + "/getUserBorrowBookOnDate?date=06/23/2020")).andExpect(status().isOk());
        assertThat(libraryManagementService.getUserBorrowBookOnDate(Optional.ofNullable(date))).isNotEmpty();
        assertThat(libraryManagementService.getUserBorrowBookOnDate(Optional.ofNullable(date))).isEqualTo(userList);
    }
    @Test
    public void whenWrongDateFormat_thenResponseBadRequestException() throws Exception {

        String date = "23.06.2020";
        User testUser = new User();
        testUser.setName("Aexi");
        testUser.setFirstName("Liam");
        testUser.setMemberSince("01/01/2010");
        testUser.setMemberTill("");
        testUser.setGender('m');
        Set<User> userList = new HashSet<>();
        userList.add(testUser);

        assertNotNull(library.getUsers());
        when(libraryManagementRepository.getUserBorrowBookOnDate(date)).thenThrow(DateFormatException.class);
        mockMvc.perform(get(API_BASE_PATH_V1 + "/getUserBorrowBookOnDate?date="+date))
        .andExpect(status().isBadRequest())
        .andExpect(result -> assertTrue(result.getResolvedException() instanceof DateFormatException));
    }
}