package com.Dilay.AppMusicDemo.Service;


import com.Dilay.AppMusicDemo.Entity.Role;
import com.Dilay.AppMusicDemo.Entity.User;
import com.Dilay.AppMusicDemo.Exception.ErrorCode;
import com.Dilay.AppMusicDemo.Exception.NotFindException;
import com.Dilay.AppMusicDemo.Model.Request.LoginRequest;
import com.Dilay.AppMusicDemo.Model.Request.RegisterRequest;
import com.Dilay.AppMusicDemo.Model.Response.LoginResponse;
import com.Dilay.AppMusicDemo.Repository.UserRepository;
import com.Dilay.AppMusicDemo.Security.AuthResponse;
import com.Dilay.AppMusicDemo.Security.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
//  private EmailClient emailClient ;
    private User user ;


    public AuthResponse register(@Valid RegisterRequest request) {
        var user= User.builder().name(request.getName())
                .surname(request.getSurname()).email(request.getEmail()).
                password(passwordEncoder.encode(request.getPassword())).
                role(Role.USER).build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
            //burada email confirm yapacağım
        return AuthResponse.builder().token(jwtToken).build();
    }

    public String getAuthenticatedUserId() {
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal.equals("anonymousUser")) {
            throw new NotFindException(ErrorCode.unauthorized, "Unauthorized user!");
        }
        return principal;
    }
    public LoginResponse login(LoginRequest loginRequest) {


        User user = repository.findUserByEmail(loginRequest.getEmail());
        if (user == null) {
            throw new NotFindException(ErrorCode.account_missing, "There are no user like that.");
        }

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new NotFindException(ErrorCode.validation, "Wrong Password!");
        }


        return LoginResponse.builder()
                .id(String.valueOf(user.getId()))
                .token(jwtService.generateToken(String.valueOf(user.getId())))
                .role(user.getRole())
                .name(user.getName())
                .surname(user.getSurname())
                .build();
    }

 }


//    public void sendVerificationEmail(EmailRequest emailRequest) {
//        User user = repository.findUserByEmail(emailRequest.getEmail());
//        if (user == null) {
//            throw new NotFindException(ErrorCode.account_missing, "There is no user with that email.");
//        }
//
//        if (user.isVerified()) {
//            throw new NotFindException(ErrorCode.forbidden, "User is already verified!");
//        }
//
//        String verificationCode = RandomStringUtils.randomAlphanumeric(24);
//        ZonedDateTime verificationCodeExpiredDate = DateUtil.now().plusDays(1);
//
//        user.setVerificationCode(verificationCode);
//        user.setVerificationCodeExpiredDate(verificationCodeExpiredDate);
//        repository.save(user);
//
//        emailClient.sendVerificationEmail(user);
//    }
//
//    public void verify( EmailVerifyRequest body) {
//        User user = repository.findUserByEmail(body.getEmail());
//        if (user == null) {
//            throw new NotFindException(ErrorCode.account_missing, "There is no user like that!");
//        }
//
//        if (user.getVerificationCodeExpiredDate().isBefore(DateUtil.now())) {
//            throw new NotFindException(ErrorCode.code_expired, "The code is expired!");
//        }
//
//        if (!user.getVerificationCode().equals(body.getVerificationCode())) {
//            throw new NotFindException(ErrorCode.code_mismatch, "The codes dont match!");
//        }
//
//        user.setRecoveryCodeExpiredDate(null);
//        user.setRecoveryCode(null);
//        user.setVerified(true);
//       repository.save(user);
//    }
//
//    public void recovery(EmailRecoveryRequest body) {
//        User user = repository.findUserByEmail(body.getEmail());
//        if (user == null) {
//            throw new NotFindException(ErrorCode.account_missing, "There is no user like that!");
//        }
//
//        if (user.getRecoveryCodeExpiredDate().isBefore(DateUtil.now())) {
//            throw new NotFindException(ErrorCode.code_expired, "The code is expired!");
//        }
//
//        if (!user.getRecoveryCode().equals(body.getRecoveryCode())) {
//            throw new NotFindException(ErrorCode.code_mismatch, "The codes dont match!");
//        }
//
//        user.setRecoveryCode(null);
//        user.setRecoveryCodeExpiredDate(null);
//        user.setPassword(passwordEncoder.encode(body.getNewPassword()));
//        repository.save(user);
//    }
//
//    public void sendRecoveryEmail(EmailRequest body) {
//        User user = repository.findUserByEmail(body.getEmail());
//        if (user == null) {
//            throw new NotFindException(ErrorCode.account_missing, "There is no user like that!");
//        }
//
//        String recoveryCode = RandomStringUtils.randomAlphanumeric(24);
//        ZonedDateTime recoveryCodeExpiredDate = DateUtil.now().plusDays(1);
//
//        user.setRecoveryCode(recoveryCode);
//        user.setRecoveryCodeExpiredDate(recoveryCodeExpiredDate);
//        repository.save(user);
//
//      emailClient.sendRecoveryEmail(user);
//    }
//

