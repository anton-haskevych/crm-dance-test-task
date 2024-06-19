package com.example.demo.security.user;

/**
 * Projection for {@link JwtUser}
 */
public interface JwtUserInfo {
    Long getId();

    String getFirstName();

    String getLastName();

    String getEmail();
}