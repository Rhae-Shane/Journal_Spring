package com.Omesh.JournalApp.service;

import com.Omesh.JournalApp.entity.User;
import com.Omesh.JournalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveEntry(User user){
            userRepository.save(user);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public Optional<User> getUserById(ObjectId id){
        return userRepository.findById(id);
    }

    public void deleteEntry(ObjectId id){
        userRepository.deleteById(id);
    }

    public User findbyUsername(String userName){
        return userRepository.findByUserName(userName);
    }

}
