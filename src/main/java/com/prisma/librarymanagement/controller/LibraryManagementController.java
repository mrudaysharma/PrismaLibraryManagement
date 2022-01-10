package com.prisma.librarymanagement.controller;

import com.prisma.librarymanagement.entity.Library;
import com.prisma.librarymanagement.entity.User;
import com.prisma.librarymanagement.exception.BadRequestException;
import com.prisma.librarymanagement.exception.DateFormatException;
import com.prisma.librarymanagement.service.LibraryManagementService;
import com.prisma.librarymanagement.utils.DateValidator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.text.DateFormat;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.zip.DataFormatException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/library")
public class LibraryManagementController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LibraryManagementController.class);

    @Autowired
    private LibraryManagementService libraryManagementService;

    @GetMapping("/getLibrary")
    public ResponseEntity<Library> getLibrary() {
        try {
            Optional<Library> library = libraryManagementService.getLibrary();
            if (library.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                LOGGER.info("Library Loaded " + library.toString());
                return new ResponseEntity<>(library.get(), HttpStatus.OK);
            }
        } catch (Exception e) {
            LOGGER.error("Exception: " + e.getMessage() + " Unexpected error occured while loading library content ");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/getUserAtleastBorrowOneBook")
    public ResponseEntity<List<String>> getUserAtleastBorrowOneBook() {
        try {
            List<String> users = libraryManagementService.getUserBorrowedOneBookAtleast();
            if (users.isEmpty()) {
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            } else {
                LOGGER.info("User List Borrowed Atleast One Book=====>" + users.toString());
                return new ResponseEntity(users, HttpStatus.OK);
            }
        } catch (Exception e) {
            LOGGER.error("Exception: " + e.getMessage() + " No content found in library ");
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * @param date String - format should be MM/DD/YYYY - 07/25/2019
     * @return Set of Users
     */
    @GetMapping("/getUserBorrowBookOnDate")
    public ResponseEntity<Set<User>> getUserBorrowBookOnDate(@NotBlank @RequestParam Optional<String> date) {
            if (date.isEmpty()) {
                throw new BadRequestException("Date is Required to Fetch Borrower List");
            }
            if(!date.get().matches(DateValidator.DATE_REGEX))
            {
                throw new DateFormatException("Date pattern is not MM/DD/YYYY");
            }
            Set<User> users = libraryManagementService.getUserBorrowBookOnDate(date);
            if (users.isEmpty()) {
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            } else {
                LOGGER.info("User List Borrowed Atleast One Book=====>" + users.toString());
                return new ResponseEntity(users, HttpStatus.OK);
            }
        }
    }

