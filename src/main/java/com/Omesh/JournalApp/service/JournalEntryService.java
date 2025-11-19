package com.Omesh.JournalApp.service;

import com.Omesh.JournalApp.entity.JournalEntry;
import com.Omesh.JournalApp.entity.User;
import com.Omesh.JournalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry entry, String userName){

        User user = userService.findUserByUserName(userName);
        entry.setDate(LocalDateTime.now());
        JournalEntry saved = journalEntryRepository.save(entry);
        user.getJournalEntries().add(saved);
        userService.saveEntry(user);

    }

    public void saveEntry(JournalEntry entry){

        journalEntryRepository.save(entry);

    }

    public List<JournalEntry> getAllEntries(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getEntry(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    public void deleteEntry(ObjectId id, String userName){
        User user = userService.findUserByUserName(userName);
        user.getJournalEntries().removeIf(journalEntry -> journalEntry.getId().equals(id));
        userService.saveEntry(user);
        journalEntryRepository.deleteById(id);
    }

}
