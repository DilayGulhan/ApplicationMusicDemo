package com.Dilay.AppMusicDemo.Controller;

import com.Dilay.AppMusicDemo.Model.Response.UserResponse;
import com.Dilay.AppMusicDemo.Service.AuthenticationService;
import com.Dilay.AppMusicDemo.Service.UserService;
import com.Dilay.AppMusicDemo.user.CreateUserRequest;
import com.Dilay.AppMusicDemo.user.UpdateUserRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth/admin")
@AllArgsConstructor

public class UserController {
    UserService userService;
    AuthenticationService authenticationService ;


    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable int id) {
        return userService.getUser(String.valueOf(id));
    }
    @PostMapping
    public UserResponse addUser(@Valid @RequestBody CreateUserRequest userRequest  ){
     return UserResponse.fromEntity(userService.addUser( userRequest
          //   Integer.parseInt(authenticationService.getAuthenticatedUserId())
     )) ;
}
   @PutMapping("/{id}")
    public UserResponse updateUser(@PathVariable String id , @Valid @RequestBody UpdateUserRequest updateUserRequest){
       return UserResponse.fromEntity( userService.updateUser(updateUserRequest , Integer.parseInt(id) ));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id ){
        userService.deleteUser(Integer.parseInt(id));
    }

}
