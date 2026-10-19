package com.nexora.domain.exceptions;

public class DuplicateEmailException extends DomainException {
    public DuplicateEmailException(String message) { super(message); }
}
