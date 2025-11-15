package com.Omesh.JournalApp.controller;


import com.Omesh.JournalApp.entity.JournalEntry;
import com.Omesh.JournalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerv2 {

    @Autowired
    private JournalEntryService journalEntryService;




    @GetMapping
    public ResponseEntity<?> getAll() {
        List<JournalEntry> all=   journalEntryService.getAllEntries();
        if(all != null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("{myid}")
    public ResponseEntity<JournalEntry>  getEntry(@PathVariable ObjectId myid) {
       Optional<JournalEntry> journalEntry =  journalEntryService.getEntry(myid);
       if(journalEntry.isPresent()){
       return new ResponseEntity<JournalEntry>(journalEntry.get(), HttpStatus.OK);
       }
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping
    public ResponseEntity<Object> addEntry(@RequestBody JournalEntry myentry) {

        try {
            journalEntryService.saveEntry(myentry);
            return new ResponseEntity<>(myentry , HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("{myid}")
    public ResponseEntity<?> deleteEntry(@PathVariable ObjectId myid) {
        journalEntryService.deleteEntry(myid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{myid}")
    public ResponseEntity<?> updateEntry(@PathVariable ObjectId myid, @RequestBody JournalEntry myentry) {
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
