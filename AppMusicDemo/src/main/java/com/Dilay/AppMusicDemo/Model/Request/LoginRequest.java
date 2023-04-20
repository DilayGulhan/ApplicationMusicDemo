package com.Dilay.AppMusicDemo.Model.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@ToString
@AllArgsConstructor
public class LoginRequest {

    @Email
    @NotEmpty
    private String email;

    @Length(min = 6)
    private String password;


}
