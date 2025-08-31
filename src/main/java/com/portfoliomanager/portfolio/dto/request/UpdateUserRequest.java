package com.portfoliomanager.portfolio.dto.request;

import jakarta.validation.constraints.Size;

/*
 * Request DTO for updating an existing user.
 * Design decisions:
 * - Optional fields (can be null for partial updates)
 * - Email cannot be updated (business rule)
 * - Only updatable fields are included
 */
public record UpdateUserRequest(
        @Size(min = 1, max = 50, message = "Must be between 1 - 50")
        String firstName,
        @Size(min = 1, max = 50, message = "Must be between 1 - 50")
        String lastName,

        Boolean isActive

) {  /*
 * Check if the request has any non-null values to update
 */
    public boolean hasUpdates() {
        return firstName != null || lastName != null || isActive != null;
    }
}