package com.portfoliomanager.portfolio.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Request DTO for creating a new user.
 * Using Java 17 record for immutability and conciseness.
 * Validation annotations ensure data quality at API boundary.
 */
public record CreateUserRequest(@Email(message = "Email must be valid!")
                                @NotBlank(message = "Email is required")
                                @Size(max = 100, message = "Email must exceed 100 characters")
                                String email,
                                @NotBlank(message = "First name is required")
                                @Size(min = 1, max = 50, message = "Firstname must between 1-50 char")
                                @Column(name = "first_name", nullable = false, length = 50)
                                String firstName,
                                @NotBlank(message = "Last name is required")
                                @Size(min = 1, max = 50, message = "Last name must be between 1 and 50 characters")
                                String lastName
) {
    /**
     * Business validation method
     */
    public boolean hasValidNames() {
        return firstName != null && lastName != null &&
                !firstName.trim().isEmpty() && !lastName.trim().isEmpty();
    }

}