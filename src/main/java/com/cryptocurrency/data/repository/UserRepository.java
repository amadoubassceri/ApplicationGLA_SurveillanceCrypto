package com.cryptocurrency.data.repository;

import com.cryptocurrency.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The UserRepository interface is a Spring Data JPA repository for managing user data.
 * UserRepository interface.
 * Author: Mouhamadou Ahibou DIALLO
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    /**
     * Find a user by its username.
     *
     * @param username the username to find the user for
     * @return a user with the given username, or an empty optional if none is found
     */
    Optional<User> findByUsername(String username);

    /**
     * Find a user by its token hash.
     *
     * @param tokenHash the token hash to find the user for
     * @return a user with the given token hash, or an empty optional if none is found
     */
    Optional<User> findByTokenHash(String tokenHash);


    /**
     * Find a user by its email.
     *
     * @param email the email to find the user for
     * @return a user with the given email, or an empty optional if none is found
     */
    Optional<User> findByEmail(String email);

    /**
     * Find a user by its password hash.
     *
     * @param passwordHash the password hash to find the user for
     * @return a list of users with the given password hash
     */
    List<User> findByPasswordHash(String passwordHash);

    /**
     * Find a user by its username and password hash.
     *
     * @param username      the username to find the user for
     * @param passwordHash the password hash to find the user for
     * @return the user with the given username and password hash
     */
    User findByUsernameAndPasswordHash(String username, String passwordHash);

    /**
     * Find a user by its email and password hash.
     *
     * @param email        the email to find the user for
     * @param passwordHash the password hash to find the user for
     * @return an optional containing the user with the given email and password hash, or an empty optional if none is found
     */
    Optional<User> findByEmailAndPasswordHash(String email, String passwordHash);

    /**
     * Find a user by its email and token hash.
     *
     * @param email the email to find the user for
     * @param tokenHash the token hash to find the user for
     * @return an optional containing the user with the given email and token hash, or an empty optional if none is found
     */
    Optional<User> findByEmailAndTokenHash(String email, String tokenHash);
}
