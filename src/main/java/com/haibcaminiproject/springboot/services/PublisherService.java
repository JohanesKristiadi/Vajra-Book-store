package com.haibcaminiproject.springboot.services;

import com.haibcaminiproject.springboot.models.Publisher;
import com.haibcaminiproject.springboot.repositories.BookRepository;
import com.haibcaminiproject.springboot.repositories.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PublisherService {

    private final PublisherRepository publisherRepository;
    private final BookRepository bookRepository;

    @Autowired
    public PublisherService(PublisherRepository publisherRepository, BookRepository bookRepository) {
        this.publisherRepository = publisherRepository;
        this.bookRepository = bookRepository;
    }

    public List<Publisher> getPublishers() {
        return publisherRepository.findAll();
    }

    public Publisher getPublisher(Long publisherId) {
        return publisherRepository.findById(publisherId).orElseThrow(() -> new IllegalStateException("Publisher does not exists"));
    }

    public void addNewPublisher(Publisher publisher) {
        Optional<Publisher> newPublisher = publisherRepository.findByName(publisher.getName());

        if (newPublisher.isPresent()) {
            throw new IllegalStateException("Name must be unique");
        }
        publisherRepository.save(publisher);
    }

    @Transactional
    public void updatePublisher(Long publisherId, String name) {
        Publisher publisher = publisherRepository.findById(publisherId).orElseThrow(() -> new IllegalStateException("Publisher does not exists"));

        if (name != null && name.length() > 0 && !Objects.equals(publisher.getName(), name)) {
            Optional<Publisher> otherPublisher = publisherRepository.findByName(name);
            if (otherPublisher.isPresent()) {
                throw new IllegalStateException("Name taken");
            }
            publisher.setName(name);
        }
    }

    public void deletePublisher(Long publisherId) {
        if (!publisherRepository.existsById(publisherId)) {
            throw new IllegalStateException("Publisher does not exists");
        }
        if (bookRepository.existsByPublisherId(publisherId)) {
            throw new IllegalStateException("Failed to update/delete, data might have relation with other data.");
        }
        publisherRepository.deleteById(publisherId);
    }

}
