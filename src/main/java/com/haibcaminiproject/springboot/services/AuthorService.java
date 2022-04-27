package com.haibcaminiproject.springboot.services;

import com.haibcaminiproject.springboot.models.Author;
import com.haibcaminiproject.springboot.repositories.AuthorRepository;
import com.haibcaminiproject.springboot.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public List<Author> getAuthors() {
        return authorRepository.findAll();
    }

    public Author getAuthor(Long authorId) {
        return authorRepository.findById(authorId).orElseThrow(() -> new IllegalStateException("Author does not exists"));
    }

    public void addNewAuthor(Author author) {
        Optional<Author> newAuthor = authorRepository.findByName(author.getName());

        if (newAuthor.isPresent()) {
            throw new IllegalStateException("Name must be unique");
        }
        authorRepository.save(author);
    }

    @Transactional
    public void updateAuthor(Long authorId, String name) {
        Author author = authorRepository.findById(authorId).orElseThrow(() -> new IllegalStateException("Author does not exists"));

        if (name != null && name.length() > 0 && !Objects.equals(author.getName(), name)) {
            Optional<Author> otherAuthor = authorRepository.findByName(name);
            if (otherAuthor.isPresent()) {
                throw new IllegalStateException("Name taken");
            }
            author.setName(name);
        }
    }

    public void deleteAuthor(Long authorId) {
        if (!authorRepository.existsById(authorId)) {
            throw new IllegalStateException("Author does not exists");
        }
        if (bookRepository.existsByAuthorId(authorId)) {
            throw new IllegalStateException("Failed to update/delete, data might have relation with other data.");
        }
        authorRepository.deleteById(authorId);
    }
}
