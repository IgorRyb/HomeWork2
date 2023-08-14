package ru.otus.crm.model;

import lombok.*;

import javax.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "phones")
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "number")
    private String number;

}
