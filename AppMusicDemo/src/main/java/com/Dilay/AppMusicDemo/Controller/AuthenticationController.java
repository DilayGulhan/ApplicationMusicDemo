package com.Dilay.AppMusicDemo.Controller;

import com.Dilay.AppMusicDemo.Model.Request.LoginRequest;
import com.Dilay.AppMusicDemo.Model.Request.RegisterRequest;
import com.Dilay.AppMusicDemo.Model.Response.LoginResponse;
import com.Dilay.AppMusicDemo.Security.AuthResponse;
import com.Dilay.AppMusicDemo.Service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    @PostMapping("/register")
    public ResponseEntity<AuthResponse>register(@RequestBody RegisterRequest request ){
        return ResponseEntity.ok(service.register(request));
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse > login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(service.login(loginRequest)) ;
    }









}
