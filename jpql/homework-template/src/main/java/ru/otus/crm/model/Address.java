package ru.otus.crm.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Table(name = "address")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "street")
    private String street;

}
