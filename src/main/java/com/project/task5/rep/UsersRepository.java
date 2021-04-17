package com.project.task5.rep;

import com.project.task5.models.Users;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends CrudRepository<Users, Long> {
    Users findByEmail(String email);
    Users findByPassword(String password);
    boolean existsByEmail(String email);
    boolean existsByPassword(String password);
    void deleteByEmail(String email);


    //Users findByCurrentId(Long id);
}
