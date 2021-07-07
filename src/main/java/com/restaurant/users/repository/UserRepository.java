package com.restaurant.users.repository;

import com.restaurant.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    public User findByEmail(String email);
    public User findByActive(boolean active);

}