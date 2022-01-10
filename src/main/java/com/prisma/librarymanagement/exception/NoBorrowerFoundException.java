package com.prisma.librarymanagement.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
public class NoBorrowerFoundException extends RuntimeException {
    private String message;
}
