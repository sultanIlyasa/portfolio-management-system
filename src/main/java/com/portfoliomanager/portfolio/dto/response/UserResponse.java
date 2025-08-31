package com.portfoliomanager.portfolio.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

/**
 * Response DTO for User data.
 * <p>
 * Design decisions:
 * - Only expose necessary fields to API consumers
 * - Use @JsonProperty for custom JSON field names
 * - Format dates consistently
 * - Include computed fields (fullName)
 */
public record UserResponse(Long id,
                           String email,
                           @JsonProperty("first_name")
                           String firstName,
                           @JsonProperty("last_name")
                           String lastName,
                           @JsonProperty("full_name")
                           String fullName,
                           @JsonProperty("is_active")
                           Boolean isActive,
                           @JsonProperty("created_at")
                           LocalDateTime createdAt,
                           @JsonProperty("updated_at")
                           LocalDateTime updatedAt
) {

    /*
     * Factory method to create UserResponse from User entity
     */

    public static UserResponse from(com.portfoliomanager.portfolio.model.User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getFullname(),
                user.getIsActive(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
