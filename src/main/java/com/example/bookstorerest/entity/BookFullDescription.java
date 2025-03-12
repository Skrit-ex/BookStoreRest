package com.example.bookstorerest.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "fullDescription")
@Data
public class BookFullDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String bookName;
    @NonNull
    @Column(name = "full_description", length = 1500)
    private String fullDescription;

}
