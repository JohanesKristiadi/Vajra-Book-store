package com.haibcaminiproject.springboot.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Publisher")
@Table(
        name = "publisher",
        uniqueConstraints = {
                @UniqueConstraint(name = "publisher_name_unique", columnNames = "name")
        }
)
public class Publisher {
    @Id
    @SequenceGenerator(
            name = "publisher_sequence",
            sequenceName = "publisher_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "publisher_sequence"
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

    @OneToMany(mappedBy = "publisher", orphanRemoval = true)
    private List<Book> books;

    public Publisher(String name) {
        this.name = name;
    }
}
