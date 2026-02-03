package com.example.userservice.repository;

import com.example.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for User entity.
 * Provides database access methods for user operations.
 * 
 * @author Automation Engineer
 * @version 1.0.0
 * @since 2024-01-01
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds users by name using case-insensitive partial matching.
     * Uses LIKE query with wildcards for flexible searching.
     * 
     * @param name the name or partial name to search for
     * @return list of users matching the search criteria
     */
    @Query("SELECT u FROM User u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<User> findByNameContainingIgnoreCase(@Param("name") String name);

    /**
     * Finds users by exact name match (case-insensitive).
     * 
     * @param name the exact name to search for
     * @return list of users with the exact name
     */
    List<User> findByNameIgnoreCase(String name);

    /**
     * Finds users by name and status using case-insensitive partial matching.
     * 
     * @param name the name or partial name to search for
     * @param status the status to filter by
     * @return list of users matching the search criteria
     */
    @Query("SELECT u FROM User u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%')) AND u.status = :status")
    List<User> findByNameContainingIgnoreCaseAndStatus(@Param("name") String name, @Param("status") String status);

    /**
     * Finds all active users by name using case-insensitive partial matching.
     * 
     * @param name the name or partial name to search for
     * @return list of active users matching the search criteria
     */
    @Query("SELECT u FROM User u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%')) AND u.status = 'ACTIVE'")
    List<User> findActiveUsersByName(@Param("name") String name);
}