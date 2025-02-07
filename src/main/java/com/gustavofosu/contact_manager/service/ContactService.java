package com.gustavofosu.contact_manager.service;

/*
    Created by Gustav on 1/16/2025
    @author : Gustav
    @date : 1/16/2025
    @project : contact-manager
*/

import com.gustavofosu.contact_manager.model.Contact;
import com.gustavofosu.contact_manager.repository.ContactRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ContactService {
    private ContactRepository contactRepository;

    // Create a new contact
    public Contact createContact(Contact contact) {
        return contactRepository.save(contact);
    }

    // Retrieve all contacts
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    // Retrieve a single contact by ID
    public Optional<Contact> getContactByID(Long id) {
        return contactRepository.findById(id);
    }

    // Update an existing contact
    public Contact updateContact(Long id, Contact contactToUpdate) {
        return contactRepository.findById(id)
                .map(contact -> {
                    contact.setName(contactToUpdate.getName());
                    contact.setEmail(contactToUpdate.getEmail());
                    contact.setPhone(contactToUpdate.getPhone());
                    contact.setAddress(contactToUpdate.getAddress());
                    contact.setBirthday(contactToUpdate.getBirthday());
                    contact.setOccupation(contactToUpdate.getOccupation());
                    contact.setTags(contactToUpdate.getTags());
                    contact.setNotes(contactToUpdate.getNotes());
                    contact.setUpdatedAt(LocalDate.now());
                    return contactRepository.save(contact);
                })
                .orElseThrow(() -> new RuntimeException("Contact with ID: " + id + " not found"));
    }

    // Delete a contact by ID
    public void deleteContact(Long id) {
        if (!contactRepository.existsById(id)) {
            throw new RuntimeException("Contact with ID: " + id + " not found");
        }
        contactRepository.deleteById(id);
    }
}












