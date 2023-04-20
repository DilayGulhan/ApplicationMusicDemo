package com.Dilay.AppMusicDemo.Repository;


import com.Dilay.AppMusicDemo.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
     User findUserByEmail(String email);
     User findUserById(int id );





}
