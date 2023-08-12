package tn.iit.compte.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tn.iit.compte.app.dto.UserDto;
import tn.iit.compte.app.mapper.UserMapper;
import tn.iit.compte.app.request.UserRequest;
import tn.iit.compte.app.request.action.Create;
import tn.iit.compte.app.request.action.Update;
import tn.iit.compte.app.service.UserService;
import tn.iit.compte.app.service.imp.UserServiceImp;
import tn.iit.compte.app.util.request.ResponseMessage;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userServiceImp;

    @PostMapping("")
    public ResponseEntity<ResponseMessage> create(@RequestBody @Validated({Create.class}) UserRequest userRequest) {
        return new ResponseEntity<>(userServiceImp.createUser(UserMapper.requestToModel(userRequest)), OK);
    }

    @GetMapping("/{page}/{size}")
    public ResponseEntity<Page<UserDto>> getAll(@PathVariable int page, @PathVariable int size) {
        return new ResponseEntity<>(
                userServiceImp.findAll(page, size)
                        .map(UserMapper::modelToDto),
                OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String id) {
        return new ResponseEntity<>(
                UserMapper.modelToDto(userServiceImp.getUserById(id)),
                OK
        );
    }
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        return new ResponseEntity<>(
                UserMapper.modelToDto(userServiceImp.getUserByEmail(email)),
                OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> updateUser(@RequestBody @Validated(Update.class) UserRequest userRequest, @PathVariable String id) {
        return new ResponseEntity<>(
                userServiceImp.updateUser(
                        UserMapper.requestToModel(userRequest),
                        id
                ), OK
        );
    }

}
