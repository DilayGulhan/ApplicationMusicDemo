package com.Dilay.AppMusicDemo.Controller;

import com.Dilay.AppMusicDemo.Entity.User;
import com.Dilay.AppMusicDemo.Model.Request.LoginRequest;
import com.Dilay.AppMusicDemo.Model.Request.RegisterRequest;
import com.Dilay.AppMusicDemo.Model.Response.LoginResponse;
import com.Dilay.AppMusicDemo.Repository.UserRepository;
import com.Dilay.AppMusicDemo.Security.AuthResponse;
import com.Dilay.AppMusicDemo.Security.JwtService;
import com.Dilay.AppMusicDemo.Service.AuthenticationService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

//@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional

class AuthenticationControllerTest {
  @Autowired
    private AuthenticationService authenticationService ;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    UserRepository userRepository ;
    @Autowired
    private JwtService jwtService;


    private static final String REGISTER_ENDPOINT = "/api/v1/auth/register";
    private static final String LOGIN_ENDPOINT = "/api/v1/auth/login";
    private static final String NAME = "testuser";
    private static final String SURNAME =  "Test Surname"  ;
    private static final String EMAIL = "test@gmail.com";
    private static final String PASSWORD = "password123";
  final ObjectMapper objectMapper = new ObjectMapper();

@Test
void GetsUserAfterAddingToRepo(){
    User user = User.builder().name("user1").email("mail@email.com").password("passwordd").surname("surname").build();
    userRepository.save(user);

    User response = userRepository.findUserByEmail("mail@email.com");
    assertNotNull(response);
}




    @Test
    void registerTest() throws Exception {

       RegisterRequest registerRequest = new RegisterRequest(NAME,SURNAME ,EMAIL, PASSWORD)  ;
        AuthResponse resp = authenticationService.register(registerRequest);
        assertNotNull(resp);
        User user = userRepository.findUserByEmail(EMAIL);
        assertNotNull(user);
        ResponseEntity<AuthResponse> response = restTemplate.postForEntity(REGISTER_ENDPOINT, registerRequest , AuthResponse.class );
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

@Test
   void loginTest(){
    String token = jwtService.generateToken("1" );
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(token);
    HttpEntity<String> request = new HttpEntity<>(null , headers);

    RegisterRequest registerRequest = new RegisterRequest(NAME,SURNAME ,EMAIL, PASSWORD)  ;
    AuthResponse resp = authenticationService.register(registerRequest);
    LoginRequest logReq = new LoginRequest(EMAIL, PASSWORD);
    LoginResponse response = authenticationService.login(logReq);
    assertNotNull(response);
//    JsonNode requestBodyJson = objectMapper.valueToTree(logReq);
//
//    final HttpEntity<JsonNode> request1 = new HttpEntity<>(requestBodyJson, new HttpHeaders());
//    final ResponseEntity<LoginResponse> response1 = restTemplate.exchange(LOGIN_ENDPOINT, HttpMethod.POST,
//            request1, new ParameterizedTypeReference<LoginResponse>() {  //burası hata veriyor ,
//            });
     User user =userRepository.findUserByEmail(EMAIL);
//    ResponseEntity<LoginResponse> response1 = restTemplate.postForEntity(LOGIN_ENDPOINT, logReq , LoginResponse.class );

    assertEquals(response.getName() , user.getName());



//        ResponseEntity<LoginResponse> loginResponse = restTemplate.postForEntity(LOGIN_ENDPOINT, logReq, LoginResponse.class);
//       assertEquals(HttpStatus.OK , response1.getStatusCode());


}

}