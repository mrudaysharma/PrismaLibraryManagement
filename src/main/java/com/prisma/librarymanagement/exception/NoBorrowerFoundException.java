package com.prisma.librarymanagement.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NoBorrowerFoundException extends RuntimeException {
    private String message;
}
