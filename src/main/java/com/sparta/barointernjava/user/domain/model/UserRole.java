package com.sparta.barointernjava.user.domain.model;

public enum UserRole {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String role;

    public String getAuthority() {
        return this.role;
    }

    UserRole(String role) {
        this.role = role;
    }

    public static class Authority {

        public static final String USER = "ROLE_USER";
        public static final String ADMIN = "ROLE_ADMIN";
    }
}
