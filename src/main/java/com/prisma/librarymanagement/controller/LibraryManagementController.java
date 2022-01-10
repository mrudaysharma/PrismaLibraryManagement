package com.prisma.librarymanagement.controller;

import com.prisma.librarymanagement.entity.Library;
import com.prisma.librarymanagement.service.LibraryManagementService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

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

}
