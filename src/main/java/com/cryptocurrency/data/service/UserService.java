package com.cryptocurrency.data.service;

import com.cryptocurrency.data.model.User;
import com.cryptocurrency.data.repository.UserRepository;

import com.cryptocurrency.data.security.EncodedPassword;
import com.cryptocurrency.data.security.EncodedToken;
import com.cryptocurrency.data.utils.GenerateToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * The service for User objects.
 * Author: Amadou BASS
 */
@Service
public class UserService {

    /**
     * The repository for User objects.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Retrieve all users from the repository.
     *
     * @return a list of all User objects.
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Find a user by its username.
     *
     * @param username the username to find the user for
     * @return a User object with the given username, or an empty optional if none is found
     */
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    /**
     * Find a user by its email.
     *
     * @param email the email to find the user for
     * @return a User object with the given email, or an empty optional if none is found
     */
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    /**
     * Find a user by its id.
     *
     * @param id the id to find the user for
     * @return a User object with the given id, or null if none is found
     */
    public User findById(Long id) {
        Optional<User> result = userRepository.findById(id);
        return result.orElse(null);
    }

    /**
     * Find a user by its token hash.
     *
     * @param tokenHash the token hash to find the user for
     * @return a User object with the given token hash
     * @throws RuntimeException if no user is found with the given token hash
     */
    public User findByTokenHash(String tokenHash) {
        return userRepository.findByTokenHash(tokenHash)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    /**
     * Find users by their password hash.
     *
     * @param passwordHash the password hash to search for
     * @return a list of users with the given password hash
     */
    public List<User> findByPasswordHash(String passwordHash) {
        return userRepository.findByPasswordHash(passwordHash);
    }

    /**
     * Find a user by its username and password hash.
     *
     * @param username      the username to find the user for
     * @param passwordHash the password hash to find the user for
     * @return a User object with the given username and password hash, or null if none is found
     */
    public User findByUsernameAndPasswordHash(String username, String passwordHash) {
        return userRepository.findByUsernameAndPasswordHash(username, passwordHash);
    }

    /**
     * Saves a User object in the repository.
     *
     * @param user the User object to save
     * @return the saved User object
     */
    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * Deletes a user by its id.
     *
     * @param id the id of the user to be deleted
     */
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Creates a new user, encoding their password and generating a token.
     *
     * @param user the User object to be created
     * @return the created User object
     */
    public User createUser(User user) {
        boolean verifyPassword = VerifyPasswordMatchesService.isValidPassword(user.getPasswordHash());
        if (!verifyPassword) {
            throw new IllegalArgumentException("Le mot de passe doit comporter au moins 8 caractères et comprendre des lettres, des majuscules, des chiffres et des caractères spéciaux.");
        }

        String encodedPassword = EncodedPassword.encode(user.getPasswordHash());
        user.setPasswordHash(encodedPassword);
        System.out.println("getPasswordHash: ");

        String token = GenerateToken.generateToken();
        System.out.println("token: ");
        String encodedToken = EncodedToken.encode(token);
        user.setTokenHash(encodedToken);
        System.out.println("tokenHash: ");

        user.setStatut("normal");
        System.out.println("user created: ");
        User savedUser = this.save(user);

        if (savedUser == null) {
            throw new IllegalArgumentException("L'utilisateur n'a pas pu être sauvegardé.");
        }

        return savedUser;
    }

    /**
     * Checks if a user with the given email already exists in the database.
     *
     * @param email the email to search for
     * @return true if a user with the given email is found, false otherwise
     */
    public boolean emailExists(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    /**
     * Checks if a user with the given username already exists in the database.
     *
     * @param username the username to search for
     * @return true if a user with the given username is found, false otherwise
     */
    public boolean userNameExists(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.isPresent();
    }

    /**
     * Updates a user with the given details.
     *
     * @param id          the id of the user to be updated
     * @param userDetails the User object containing the updated details
     * @return the updated User object
     */
    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        System.out.println("user first step for update: ");

        boolean verifyPassword = VerifyPasswordMatchesService.isValidPassword(userDetails.getPasswordHash());
        if (!verifyPassword) {
            throw new IllegalArgumentException("Le mot de passe doit comporter au moins 8 caractères et comprendre des lettres, des majuscules, des chiffres et des caractères spéciaux.");
        }
        
        System.out.println("password user for update: ");

        user.setUsername(userDetails.getUsername());
        System.out.println("username updating user: ");
        user.setEmail(userDetails.getEmail());
        System.out.println("email updating user: ");

        String encodedPassword = EncodedPassword.encode(userDetails.getPasswordHash());
        //String newToken = EncodedToken.encode(userDetails.getTokenHash());
        user.setPasswordHash(encodedPassword);
        //user.setTokenHash(newToken);
        System.out.println("done updating user: ");

        return this.save(user);
    }

    /**
     * Generates a new token for a user and updates their token hash.
     *
     * @param id the ID of the user for whom to generate a new token
     * @return the User object with the updated token hash
     * @throws RuntimeException if no user is found with the given email
     */
    public User updateToken(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = GenerateToken.generateToken();
        String newToken = EncodedToken.encode(token);
        user.setTokenHash(newToken);
        System.out.println("done updating user: ");
        return this.save(user);
    }

    /**
     * Finds a user by their email and password hash.
     *
     * @param email the email of the user to be found
     * @param passwordHash the password hash of the user to be found
     * @return an Optional containing the User object if found, or an empty Optional if no user matches the given criteria
     */
    public Optional<User> findByEmailAndPasswordHash(String email, String passwordHash) {
        return userRepository.findByEmailAndPasswordHash(email, passwordHash);
    }

    /**
     * Authenticates a user using their email and password.
     *
     * @param email    the email of the user to be authenticated
     * @param password the password of the user to be authenticated
     * @return the token hash of the user
     */
    public String authenticateUser(String email, String password) {
        System.out.println("email: " + email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email"));

        if (user != null) {
            System.out.println("user: ");
            System.out.println("password: ");
            String encodedPassword = user.getPasswordHash();
            boolean isRightPassword = EncodedPassword.isRightPassword(password, encodedPassword);

            if (isRightPassword) {
                Optional<User> userValid = this.findByEmailAndPasswordHash(email, encodedPassword);
                if (userValid.isPresent()) {
                    System.out.println("Login successful");
                    return user.getTokenHash();
                }
                else {
                    System.out.println("Login failed");
                    throw new RuntimeException("Login failed");
                }
            }
            else {
                System.out.println("Mot de pass incorrect");
                throw new RuntimeException("Mot de pass incorrect");
            }
        }

        System.out.println("not found user");
        throw new RuntimeException("not found user");
    }

    /**
     * Finds a user by their email and token hash.
     *
     * @param email    the email of the user to be found
     * @param tokenHash the token hash of the user to be found
     * @return an Optional containing the User object if found, or an empty Optional if no user matches the given criteria
     */
    public Optional<User> findByEmailAndTokenHash(String email, String tokenHash) {
        return userRepository.findByEmailAndTokenHash(email, tokenHash);
    }

    /**
    /**
     * Verifies if the provided token matches the stored token hash for the user with the given email.
     *
     * @param email the email of the user whose token is to be verified
     * @param token the token to verify
     * @return true if the token is valid, false otherwise
     * @throws RuntimeException if the user is not found
     */
    public boolean verifyToken(String email, String token) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user != null) {
            System.out.println("user: ");
            String encodedToken = user.getTokenHash();
            System.out.println("token: ");
            System.out.println("encodedToken: ");

            if (Objects.equals(token, encodedToken)) {
                Optional<User> userValid = this.findByEmailAndTokenHash(email, encodedToken);
                if (userValid.isPresent()) {
                    System.out.println("Token is valid");
                    return true;
                }
                else {
                    System.out.println("Token is not valid");
                    return false;
                }
            }
            else {
                System.out.println("Token is not valid");
                return false;
            }
        }

        System.out.println("User not found");
        return false;
    }


    /**
     * Logs out a user with the given email.
     *
     * @param email the email of the user to be logged out
     */
    public void logoutUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        System.out.println("logout user done: ");
        user.setEmail(null);
        user.setTokenHash(null);
    }

    public boolean updateUserStatusToPremium(Long userId) {
        String statut = "premium";
        return updateUserStatut(userId, statut);
    }

    public boolean updateUserStatusToFree(Long userId) {
        String statut = "normal";
        return updateUserStatut(userId, statut);
    }

    private boolean updateUserStatut(Long id, String statut) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            if (!user.get().getStatut().equals(statut)) {
                User userSetStatut = user.get();
                userSetStatut.setStatut(statut);
                userRepository.save(userSetStatut);
                return true;
            }
        }
        return false;
    }
}
