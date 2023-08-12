package tn.iit.compte.app.service;

import org.springframework.data.domain.Page;
import tn.iit.compte.app.model.User;
import tn.iit.compte.app.util.request.ResponseMessage;

public interface UserService {
    ResponseMessage createUser (User user);
    ResponseMessage updateUser (User user, String id);
    User getUserById (String id);
    void checkDuplicate(User user);
    Page<User> findAll (int page, int size);
    User getUserByEmail(String email);
}
