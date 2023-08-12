package tn.iit.compte.app.service.imp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.iit.compte.app.exception.custom.ObjectFound;
import tn.iit.compte.app.exception.custom.ObjectNotFound;
import tn.iit.compte.app.model.User;
import tn.iit.compte.app.repository.UserRepository;
import tn.iit.compte.app.service.UserService;
import tn.iit.compte.app.util.request.ResponseMessage;
import tn.iit.compte.app.util.request.constant.Behavior;

import java.util.ArrayList;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserServiceImp implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Checking if Email of the user that has been authenticated exist in database or not.
     *
     * @param username Username(email) of the user.
     * @return instance of Spring security User Class.
     * @throws ObjectNotFound if email does not existe in database, status code : 404 NOT_FOUND.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.error("Fetching {} in database", username);
        User user = userRepository.findUserByEmail(username).orElseThrow(() -> {
            log.error("User not found in database: {}", username);
            throw new ObjectNotFound("Utilisateur n'existe pas.");
        });
        log.info("User found in database: {}", username);
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                new ArrayList<>()
        );
    }

    /**
     * Create new user
     *
     * @param user that will be added
     * @return ResponseMessage with Success behavior.
     */
    @Override
    public ResponseMessage createUser(User user) {
        log.info("Start creating new {}.", user);
        this.checkDuplicate(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        this.userRepository.save(user);
        log.info("{} created successfully.", user);
        return new ResponseMessage("User created.", Behavior.SUCCESS);
    }

    /**
     * Update existing user.
     *
     * @param user that contain new data.
     * @param id   id of user that will go to be changed.
     * @return ResponseMessage with Success behavior.
     */
    @Override
    public ResponseMessage updateUser(User user, String id) {
        log.info("start updateUser Method with param {}, and id: {}.", user, id);
        User foundedUser = getUserById(id);
        foundedUser.update(user);

        userRepository.save(foundedUser);
        log.info("User Changed successfully");
        return new ResponseMessage("User updated Successfully", Behavior.SUCCESS);
    }

    /**
     * Get user by id.
     *
     * @param id of user.
     * @return founded User;
     */
    @Override
    public User getUserById(String id) {
        log.info("start checking user if exist or not with id: {}", id);
        User foundedUser = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User does not exist in database.");
                    throw new ObjectNotFound("User does not existe.");
                });
        log.info("User found: {}", foundedUser);
        return foundedUser;
    }

    /**
     * Check if user email or user phone number existe in database.
     *
     * @param user that will go to be checked.
     */
    @Override
    public void checkDuplicate(User user) {
        log.info("Start fetching for user email: {} and user phone number: {}.", user.getEmail(), user.getPhoneNumber());
        userRepository.findUserByEmailOrPhoneNumber(user.getEmail(), user.getPhoneNumber())
                .ifPresent(personne1 -> {
                    log.error("User email: {} or user phone number: {} already exist in database", user.getEmail(), user.getPhoneNumber());
                    throw new ObjectFound("User Email or Phone number already exist.");

                });
        log.info("User not found.");
    }

    /**
     * Get all User per page.
     *
     * @param page number of page.
     * @param size number of User per page.
     * @return Page of user.
     */
    @Override
    public Page<User> findAll(int page, int size) {
        log.info("Getting all user of page: {} with size: {}", page, size);
        return userRepository.findAll(PageRequest.of(page, size, Sort.Direction.ASC, "id"));
    }

    @Override
    public User getUserByEmail(String email) {
        Optional<User> user = userRepository.findUserByEmailOrPhoneNumber(email, "");
        return user.orElse(null);
    }


}
