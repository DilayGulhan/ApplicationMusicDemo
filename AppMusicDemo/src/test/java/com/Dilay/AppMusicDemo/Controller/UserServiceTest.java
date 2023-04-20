package com.Dilay.AppMusicDemo.Controller;

import com.Dilay.AppMusicDemo.Entity.Role;
import com.Dilay.AppMusicDemo.Entity.User;
import com.Dilay.AppMusicDemo.Model.Request.RegisterRequest;
import com.Dilay.AppMusicDemo.Repository.UserRepository;
import com.Dilay.AppMusicDemo.Security.AuthResponse;
import com.Dilay.AppMusicDemo.Security.JwtService;
import com.Dilay.AppMusicDemo.Service.AuthenticationService;
import com.Dilay.AppMusicDemo.user.CreateUserRequest;
import com.Dilay.AppMusicDemo.user.UpdateUserRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.checkerframework.checker.nullness.qual.AssertNonNullIfNonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

class UserServiceTest {
@Autowired
    private JwtService jwtService;
private static final int id = 1 ;
@Autowired
private TestRestTemplate template;

    private static final String NAME = "testuser";
    private static final String SURNAME =  "Test Surname"  ;
    private static final String EMAIL = "test@gmail.com";
    private static final String PASSWORD = "password123";
 @Autowired
    private UserRepository repository ;
    @Autowired
    private AuthenticationService authenticationService ;

    private final ObjectMapper objectMapper = new ObjectMapper();


//    @Test
//    void whenUnauthRequest_ThenStatus500() {
//        User user = User.builder().name(NAME).surname(SURNAME).email(EMAIL).password(PASSWORD).build();
//        ResponseEntity<String> response  = template.getForEntity("/api/v1/auth/admin/1" , String.class);
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR , response.getStatusCode());
//    }

    @Test
    void whenAuthRequest_ThenStatus200(){
        registerUsing();
        String token = jwtService.generateToken("1" );
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> request = new HttpEntity<>(null , headers);
       ResponseEntity<String> response  = template.getForEntity("/api/v1/auth/admin/1" , String.class );
      assertEquals(HttpStatus.OK , response.getStatusCode());
      Assertions.assertNotNull(response.getBody());
    }

    @Test
    void updateUser() { //TODO Authorized olarak düzelt
        User user = User.builder().name(NAME).email(EMAIL).password(PASSWORD).surname(SURNAME).build();
        repository.save(user);
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setName("testerName");
        updateUserRequest.setSurname("testerSurname");
        updateUserRequest.setUserRole(Role.ADMIN);
        JsonNode requestBodyJson = objectMapper.valueToTree(updateUserRequest);
        final HttpEntity<JsonNode> request = new HttpEntity<>(requestBodyJson, new HttpHeaders());
        ResponseEntity<User> response = template
                .exchange("/api/v1/auth/admin/1" , HttpMethod.PUT , request, User.class);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals( "testerName", response.getBody().getName());
    }

    @Test
    void testaddUser_ThenStatusOK() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setName(NAME);
        createUserRequest.setSurname(SURNAME);
        createUserRequest.setEmail(EMAIL);
        createUserRequest.setPassword(PASSWORD);
        createUserRequest.setUserRole(Role.ADMIN);
        JsonNode requestBodyJson = objectMapper.valueToTree(createUserRequest);
        final HttpEntity<JsonNode> request = new HttpEntity<>(requestBodyJson, new HttpHeaders());
        ResponseEntity<User> response = template
                .exchange("/api/v1/auth/admin" , HttpMethod.POST , request, User.class);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(NAME , response.getBody().getName());
    }

    @Test
    void deleteUser() {
        User user = User.builder().name(NAME).email(EMAIL).password(PASSWORD).surname(SURNAME).build();
        repository.save(user);
        int userId = user.getId();
        ResponseEntity<Void> response = template.exchange("/api/v1/auth/admin/{id}", HttpMethod.DELETE, null, Void.class, userId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Optional<User> deletedUser = repository.findById(userId);
        assertFalse(deletedUser.isPresent());
    }

    void registerUsing(){
        RegisterRequest registerRequest = new RegisterRequest(NAME,SURNAME ,EMAIL, PASSWORD) ;
        AuthResponse resp = authenticationService.register(registerRequest);
    }
}