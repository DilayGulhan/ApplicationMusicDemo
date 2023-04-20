package com.Dilay.AppMusicDemo.user;


import com.Dilay.AppMusicDemo.Entity.Role;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ToString
public class UpdateUserRequest {

    @NotEmpty(message = "name must be given.")
    private String name;

    @NotEmpty(message = "surname must be given.")
    private String surname;

    @NotNull(message = "Admin or Executive roles must be given.")
    private Role userRole;

}