package jantoni1.librarybackend.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BookException extends Exception {

    @AllArgsConstructor
    public enum Reason {

        NOT_FOUND("No book matching given ISBN was found.");

        String reason;

        }

     Reason reason;
}
