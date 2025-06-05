package com.todo.todo1_1.repo;

import com.todo.todo1_1.Entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
    Optional<User> getByUsername(String username);
    boolean existsByUsername(String username);
}
