package tk.fjbaldon.banksys.service;

import tk.fjbaldon.banksys.model.User;
import tk.fjbaldon.banksys.repository.UserRepository;

/**
 * The UserService class provides methods for user authentication and registration.
 * It communicates with the UserRepository to perform database queries related to users.
 *
 * Note: Error handling is minimal in this example for brevity. In a real-world application,
 * robust error handling and logging should be implemented.
 *
 * @author Francis John Baldon
 * @version 1.0
 * @since December 2023
 */
public class UserService {
    public static UserService create(UserRepository userRepository) {
        return new UserService(userRepository);
    }

    public enum AuthenticationStatus {
        OK,
        INVALID_CREDENTIALS,
        INVALID_USER
    }

    public AuthenticationStatus authenticate(User user) {
        if (user == null)
            return AuthenticationStatus.INVALID_USER;

        var storedUser = userRepository.getUserOf(user.getName());

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

        if (userRepository.getUserOf(user.getName()) != null)
            return RegistrationStatus.USERNAME_TAKEN;

        userRepository.save(user);

        return RegistrationStatus.OK;
    }

    private final UserRepository userRepository;

    private UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
