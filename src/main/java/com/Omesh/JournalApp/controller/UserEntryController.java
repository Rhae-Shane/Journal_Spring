package com.Omesh.JournalApp.controller;


import com.Omesh.JournalApp.entity.JournalEntry;
import com.Omesh.JournalApp.entity.User;
import com.Omesh.JournalApp.service.JournalEntryService;
import com.Omesh.JournalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserEntryController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUser(){
        List<User> allUser = userService.getAll();
        if(allUser.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(allUser, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<?> addNewUser(@RequestBody User user){
        try {
            userService.saveEntry(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
           return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody User user , @PathVariable String userName){
        User userInDb = userService.findbyUsername(userName);

        if(userInDb != null){
            userInDb.setPassword(user.getPassword());
            userInDb.setUserName(user.getUserName());
            userService.saveEntry(userInDb);
        }

        return new ResponseEntity<>(userInDb.toString() + " jhfsdkjgh" , HttpStatus.OK);
    }






}
