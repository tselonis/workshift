package com.example.workshift.rest.user;

import com.example.workshift.business.user.User;
import com.example.workshift.business.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {

    private UserDomainMapper userDomainMapper;
    private UserService userService;

    @PostMapping
    public ResponseEntity<Long> createUser(@RequestBody final CreateUserRequestDto createUserRequestDto) {
        User createdUser = userService.createUser(userDomainMapper.mapToCreateUserRequest(createUserRequestDto));
        return ResponseEntity.ok(createdUser.id());
    }
}
