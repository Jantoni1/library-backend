package jantoni1.librarybackend.security;

import jantoni1.librarybackend.model.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserContext {

    public static CustomUserDetails getUser() {
        return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
