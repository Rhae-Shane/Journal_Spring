package com.Omesh.JournalApp.service;

import com.Omesh.JournalApp.entity.JournalEntry;
import com.Omesh.JournalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public void saveEntry(JournalEntry entry){

            entry.setDate(LocalDateTime.now());
            journalEntryRepository.save(entry);

    }

    public List<JournalEntry> getAllEntries(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getEntry(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    public void deleteEntry(ObjectId id){
        journalEntryRepository.deleteById(id);
    }

}
