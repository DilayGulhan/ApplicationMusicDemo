package com.Dilay.AppMusicDemo.Model.Response;

import com.Dilay.AppMusicDemo.Entity.Role;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class LoginResponse {
    private String id;
    private String token;
    private Role role;
    private String name;
    private String surname;

}
