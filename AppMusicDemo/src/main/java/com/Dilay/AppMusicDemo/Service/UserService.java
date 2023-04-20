package com.Dilay.AppMusicDemo.Service;

import com.Dilay.AppMusicDemo.Entity.Role;
import com.Dilay.AppMusicDemo.Entity.User;
import com.Dilay.AppMusicDemo.Exception.ErrorCode;
import com.Dilay.AppMusicDemo.Exception.NotFindException;
import com.Dilay.AppMusicDemo.Model.Response.UserResponse;
import com.Dilay.AppMusicDemo.Repository.UserRepository;
import com.Dilay.AppMusicDemo.user.CreateUserRequest;
import com.Dilay.AppMusicDemo.user.UpdateUserRequest;
import com.Dilay.AppMusicDemo.util.DateUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
@AllArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
//    private final EmailClient emailClient;


    public UserResponse getUser(String id) {
        User existingUser = userRepository.findById(Integer.valueOf(id))
                .orElseThrow(() -> new NotFindException(ErrorCode.resource_missing, "There is no user by that ID."));

        return UserResponse.fromEntity(existingUser);
    }


    public User updateUser(UpdateUserRequest updateUserRequest, int userId
                           //, int authenticatedUserId
    ) {
//        User existingUser = userRepository.findById(userId)
//                .orElseThrow(() -> new NotFindException(ErrorCode.resource_missing, "There is no user like that!"));
//
//        User currentUser = userRepository.findById(authenticatedUserId)
//                .orElseThrow(() -> new NotFindException(ErrorCode.resource_missing, "There is no user like that!"));

//        if (!currentUser.getRole().equals(Role.ADMIN) && !currentUser.equals(existingUser)) {
//            throw new NotFindException(ErrorCode.forbidden, "You dont have the privilege.");
//        }

//        existingUser.setName(updateUserRequest.getName());
//        existingUser.setSurname(updateUserRequest.getSurname());
//        existingUser.setRole(Role.valueOf(updateUserRequest.getUserRole().toString()));
//
//
//        userRepository.save(existingUser);

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new NotFindException(ErrorCode.resource_missing, "There is no user like that!"));

           existingUser.setName(updateUserRequest.getName());
     existingUser.setSurname(updateUserRequest.getSurname());
      existingUser.setRole(Role.valueOf(updateUserRequest.getUserRole().toString()));
      userRepository.save(existingUser);
        return existingUser;
    }

    public User addUser(CreateUserRequest createUserRequest
//            , int userId
    ) {

//        if (!user.getRole().equals(Role.ADMIN)) {
//            throw new NotFindException(ErrorCode.forbidden, "Only Admins can add new users.");
//        }

        User newUser = new User();
        newUser.setName(createUserRequest.getName());

        User existingUser = userRepository.findUserByEmail(createUserRequest.getEmail());

        if (existingUser != null) {
            throw new NotFindException(ErrorCode.forbidden, "There is already a registered email like that.");
        }

        newUser.setEmail(createUserRequest.getEmail());
        newUser.setSurname(createUserRequest.getSurname());
        newUser.setVerified(false);
        newUser.setRole(Role.valueOf(createUserRequest.getUserRole().toString()));
        newUser.setRole(Role.USER); // change this
        newUser.setPassword(createUserRequest.getPassword());


        String recoveryCode = RandomStringUtils.randomAlphanumeric(24);
        ZonedDateTime recoveryCodeExpiredDate = DateUtil.now().plusDays(1);

        newUser.setRecoveryCode(recoveryCode);
        newUser.setRecoveryCodeExpiredDate(recoveryCodeExpiredDate);
        userRepository.save(newUser);

//        emailClient.sendSettingUpPasswordEmail(newUser);
        return newUser;
    }

    public void deleteUser(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFindException(ErrorCode.resource_missing, "There is no user like that!"));

        userRepository.delete(user);
    }
}
