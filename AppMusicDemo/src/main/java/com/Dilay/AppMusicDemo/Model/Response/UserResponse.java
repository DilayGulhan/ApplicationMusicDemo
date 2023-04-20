package com.Dilay.AppMusicDemo.Model.Response;

import com.Dilay.AppMusicDemo.Entity.User;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class UserResponse {
    private int  id;
    private String name;
    private String surname;
    private String email;
    private String role;


    public static UserResponse fromEntity(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .role(user.getRole().toString())
                .build();
    }

}
