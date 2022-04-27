package com.haibcaminiproject.springboot.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "BookCategory")
@Table(
        name = "book_category",
        uniqueConstraints = {
                @UniqueConstraint(name = "book_category_name_unique", columnNames = "name")
        }
)
public class BookCategory {
    @Id
    @SequenceGenerator(
            name = "book_category_sequence",
            sequenceName = "book_category_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "book_category_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "name",
            nullable = false
    )
    private String name;

    @Column(
            name = "created_at",
            nullable = false
    )
    private Instant createdAt;

    @Column(
            name = "updated_at",
            nullable = false
    )
    private Instant updatedAt;

    @OneToMany(mappedBy = "bookCategory", orphanRemoval = true)
    private List<Book> books;

    public BookCategory(String name, Instant createdAt, Instant updatedAt) {
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
