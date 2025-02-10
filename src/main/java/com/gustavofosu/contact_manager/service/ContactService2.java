package com.gustavofosu.contact_manager.service;

/*
    Created by Gustav on 2/7/2025
    @author : Gustav
    @date : 2/7/2025
    @project : contact-manager
*/

import com.gustavofosu.contact_manager.model.Contact;
import com.gustavofosu.contact_manager.model.ContactDTO;
import com.gustavofosu.contact_manager.repository.ContactRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ContactService2 {

    @Value("${file.upload-dir}")
    private String uploadDir;
    private final ContactRepository contactRepository;

    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    public Contact getContactById(Long id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact not found"));
    }

    public void saveContact(ContactDTO contactDto) {
        MultipartFile image = contactDto.getImageFile();
        LocalDate createdAt = LocalDate.now();
        String storageFileName = createdAt.getDayOfWeek() + "_" + image.getOriginalFilename();

        try {
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, uploadPath.resolve(storageFileName),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload image: " + e.getMessage());
        }

        Contact contact = new Contact();
        contact.setName(contactDto.getName());
        contact.setEmail(contactDto.getEmail());
        contact.setPhone(contactDto.getPhone());
        contact.setAddress(contactDto.getAddress());
        contact.setBirthday(contactDto.getBirthday());
        contact.setOccupation(contactDto.getOccupation());
        contact.setTags(contactDto.getTags());
        contact.setNotes(contactDto.getNotes());
        contact.setImageFileName(storageFileName);
        contact.setCreatedAt(createdAt);

        contactRepository.save(contact);
    }

    public void updateContact(Contact contact, ContactDTO contactDto) {
        if(!contactDto.getImageFile().isEmpty()) {
            Path oldImagePath = Paths.get(uploadDir).resolve(contact.getImageFileName());

            try {
                Files.delete(oldImagePath);
            } catch (Exception e) {
                System.out.println("Exception: " + e.getMessage());
            }

            MultipartFile imageFile = contactDto.getImageFile();
            LocalDate createdAt = LocalDate.now();
            String storageFileName = createdAt.getDayOfWeek() + "_" + imageFile.getOriginalFilename();

            try (InputStream inputStream = imageFile.getInputStream()) {
                Files.copy(inputStream, Paths.get(uploadDir).resolve(storageFileName),
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                System.out.println("Exception: " + e.getMessage());
            }
            contact.setImageFileName(storageFileName);
        }

        contact.setName(contactDto.getName());
        contact.setEmail(contactDto.getEmail());
        contact.setPhone(contactDto.getPhone());
        contact.setAddress(contactDto.getAddress());
        contact.setBirthday(contactDto.getBirthday());
        contact.setOccupation(contactDto.getOccupation());
        contact.setTags(contactDto.getTags());
        contact.setNotes(contactDto.getNotes());
        contact.setUpdatedAt(LocalDate.now());

        contactRepository.save(contact);
    }

    public void deleteContact(Long id) {
        try {
            Contact contact = getContactById(id);

            Path imagePath = Paths.get(uploadDir + contact.getImageFileName());

            try {
                Files.deleteIfExists(imagePath);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }

            contactRepository.delete(contact);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Page<Contact> searchContacts(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));

        if (keyword == null || keyword.trim().isEmpty()) {
            return contactRepository.findAll(pageable);
        } else {
            return contactRepository.searchByFirstNameOrEmail(keyword, pageable);
        }
    }
}







