package com.github.fjbaldon.banksys.business.service;

import com.github.fjbaldon.banksys.business.model.User;
import com.github.fjbaldon.banksys.data.dao.UserDAO;

/**
 * The UserService class provides methods for user authentication and registration.
 * It communicates with the UserDAO to perform database queries related to users.
 *
 * Note: Error handling is minimal in this example for brevity. In a real-world application,
 * robust error handling and logging should be implemented.
 *
 * @author Francis John Baldon
 * @version 1.0
 * @since December 2023
 */
public class UserService {
    public static UserService create(UserDAO userDAO) {
        return new UserService(userDAO);
    }

    public enum AuthenticationStatus {
        OK,
        INVALID_CREDENTIALS,
        INVALID_USER
    }

    public AuthenticationStatus authenticate(User user) {
        if (user == null)
            return AuthenticationStatus.INVALID_USER;

        var storedUser = userDAO.getUserOf(user.getName());

        if (storedUser == null || !storedUser.getPassword().equals(user.getPassword()))
            return AuthenticationStatus.INVALID_CREDENTIALS;

        return AuthenticationStatus.OK;
    }

    public enum RegistrationStatus {
        OK,
        USERNAME_TAKEN,
        INVALID_ACCOUNT,
    }

    public RegistrationStatus register(User user) {
        if (user == null)
            return RegistrationStatus.INVALID_ACCOUNT;

        if (userDAO.getUserOf(user.getName()) != null)
            return RegistrationStatus.USERNAME_TAKEN;

        userDAO.save(user);

        return RegistrationStatus.OK;
    }

    private final UserDAO userDAO;

    private UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
