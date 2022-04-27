package com.haibcaminiproject.springboot.models;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Book")
@Table(
        name = "book"
)
public class Book {
    @Id
    @SequenceGenerator(
            name = "book_sequence",
            sequenceName = "book_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "book_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "title",
            nullable = false
    )
    private String title;

    @Column(
            name = "stock",
            columnDefinition = "integer default 0"
    )
    private int stock;

    @Column(
            name = "price",
            nullable = false
    )
    private BigDecimal price;

    @Column(
        name = "isread",
        nullable = false,
        columnDefinition = "boolean default false"
    )
    private boolean isRead;

    @Lob
    @Column(
            name = "description"
    )
    private String description;

    @Lob
    @Column(
            name = "image",
            columnDefinition = "MEDIUMBLOB"
    )
    private String image;

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

    @ManyToOne
    @JoinColumn(
            name = "book_category_id",
            foreignKey = @ForeignKey(name = "book_category_id")
    )
    private BookCategory bookCategory;

    @ManyToOne
    @JoinColumn(
            name = "author_id",
            foreignKey = @ForeignKey(name = "author_id")
    )
    private Author author;

    @ManyToOne
    @JoinColumn(
            name = "publisher_id",
            foreignKey = @ForeignKey(name = "publisher_id")
    )
    private Publisher publisher;

    public Book(
            String title,
            int stock,
            BigDecimal price,
            String description,
            String image,
            Instant createdAt,
            Instant updatedAt) {
        this.title = title;
        this.stock = stock;
        this.price = price;
        this.description = description;
        this.image = image;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getImagePath() {
        return "data:image/jpeg;base64," + this.image;
    }
}
