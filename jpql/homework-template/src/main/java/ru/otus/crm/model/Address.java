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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return Objects.equals(getId(), address.getId()) && Objects.equals(getStreet(), address.getStreet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getStreet());
    }
}
