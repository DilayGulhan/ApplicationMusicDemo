package com.Dilay.AppMusicDemo.user;


import com.Dilay.AppMusicDemo.Entity.Role;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ToString
public class CreateUserRequest {

    @Email(message = "Invalid email")
    @NotEmpty
    private String email;

    @NotEmpty(message = "name must be given.")
    private String name;

    @NotEmpty(message = "surname must be given.")
    private String surname;

    @NotNull(message = "If you are user you should choose normal or premium subsciption")
    private Role userRole;
    @NotNull(message = "password can't be null ")
    private String password;

}
