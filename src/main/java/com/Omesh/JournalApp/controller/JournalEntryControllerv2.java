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
@RequestMapping("/journal")
public class JournalEntryControllerv2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService  userService;



    @GetMapping("/{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName) {

        User user = userService.findUserByUserName(userName);


        List<JournalEntry> all=   user.getJournalEntries();
        if(all != null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }

            return new ResponseEntity<>(user + "user has no journals",  HttpStatus.NOT_FOUND);
    }

    @GetMapping("/entry/{myid}")
    public ResponseEntity<JournalEntry>  getEntry(@PathVariable ObjectId myid) {
       Optional<JournalEntry> journalEntry =  journalEntryService.getEntry(myid);
       if(journalEntry.isPresent()){
       return new ResponseEntity<JournalEntry>(journalEntry.get(), HttpStatus.OK);
       }
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping("{userName}")
    public ResponseEntity<Object> addEntry(@RequestBody JournalEntry myentry , @PathVariable String userName) {

        try {

            journalEntryService.saveEntry(myentry , userName);
            return new ResponseEntity<>(myentry , HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("id/{userName}/{myid}")
    public ResponseEntity<?> deleteEntry(@PathVariable ObjectId myid , @PathVariable String userName) {
        journalEntryService.deleteEntry(myid , userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{userName}/{myid}")
    public ResponseEntity<?> updateEntry(@PathVariable ObjectId myid, @RequestBody JournalEntry myentry , @PathVariable String userName) {
        JournalEntry old = journalEntryService.getEntry(myid).orElse(null);

        if(old != null){
            old.setTitle(myentry.getTitle() != null && !myentry.getTitle().equals("") ? myentry.getTitle() : old.getTitle());
            old.setContent(myentry.getContent()  != null && !myentry.getContent().equals("") ? myentry.getContent() : old.getContent()) ;
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(old, HttpStatus.OK);
        }



        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
