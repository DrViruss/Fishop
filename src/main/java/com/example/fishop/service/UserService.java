package com.example.fishop.service;

import com.example.fishop.entity.User;
import com.example.fishop.repo.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    UserRepo repo;

    public UserService(UserRepo repo) {
        this.repo = repo;
    }

    public Long isPresent(User user){
        User user1 = getById(user.getId());
        if(user1 == null) return null;
        return user1.getId();
    }

    public User getById(Long id)
    {
        Optional<User> optional = repo.findById(id);
        return optional.orElse(null);
    }

    public User getByEmail(String email)
    {
        return repo.findByEmail(email);
    }


    public List<User> get()
    {
        return repo.findAll();
    }
    public User save(User user)
    {
        return repo.save(user);
    }


    public void remove(Long id)
    {
        repo.deleteById(id);
    }


}
