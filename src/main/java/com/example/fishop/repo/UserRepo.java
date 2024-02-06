package com.example.fishop.repo;

import com.example.fishop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User,Long> {
    public User findByEmail(String email);
    public List<User> findAll();
    public void deleteById(Long id);
}
