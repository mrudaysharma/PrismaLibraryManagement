package com.prisma.librarymanagement.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OperationFailedException extends RuntimeException {
    private String message;
}
