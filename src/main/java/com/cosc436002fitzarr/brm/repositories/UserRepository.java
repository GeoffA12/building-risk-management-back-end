package com.cosc436002fitzarr.brm.repositories;

import com.cosc436002fitzarr.brm.models.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    public User findByUsername(String username);
}
