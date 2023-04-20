package com.Dilay.AppMusicDemo.Model.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotNull
    private String name;
    @NotNull
    private String surname ;
    @NotNull
    private String email ;
    @NotNull
    private String password ;
}
