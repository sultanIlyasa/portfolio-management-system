package com.portfoliomanager.portfolio.repository;

import com.portfoliomanager.portfolio.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /*
     * Find user by email address (business key lookup)
     */
    Optional<User> findByEmail(String email);

    /*
     * Check if user exists by email (efficient existence check)
     */
    boolean existByEmail(String email);

    /*
     * Find all active users with pagination
     */
    Page<User> findIsActiveTrue(Pageable pageable);

    /*
     * Find users by name pattern (case-insensitive search)
     */
    @Query("SELECT u FROM User u WHERE " +
            "LOWER(CONCAT(u.firstName, ' ', u.lastName)) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<User> findByFullNameContainingIgnoreCase(@Param("name") String name);

    /*
     * Count active users (business metric)
     */
    long countByIsActiveTrue();

    /*
     * Find users created within date range
     */
    @Query("SELECT u FROM User u WHERE u.createdAt BETWEEN :startDate AND :endDate")
    List<User> findUsersCreatedBetween(
            @Param("startDate") java.time.LocalDateTime startDate,
            @Param("endDate") java.time.LocalDateTime endDate
    );

    /*
     * Custom query to find users with portfolios (will be useful later)
     */
    @Query("SELECT DISTINCT u FROM User u WHERE EXISTS " +
            "(SELECT 1 FROM Portfolio p WHERE p.userId = u.id)")
    List<User> findUsersWithPortfolios();
}