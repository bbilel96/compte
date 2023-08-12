package tn.iit.compte.app.mapper;

import tn.iit.compte.app.dto.UserDto;
import tn.iit.compte.app.model.User;
import tn.iit.compte.app.request.UserRequest;

public class UserMapper {
    public static UserDto modelToDto (User user){
        return new UserDto(user);
    }
    public static User requestToModel (UserRequest userRequest){
        return new User(userRequest);
    }

}
