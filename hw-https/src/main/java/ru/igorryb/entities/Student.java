package ru.igorryb.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "student")
@SequenceGenerator(name = "student_seq", initialValue = 5)
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_seq")
    private Long id;

    @Column(name = "age")
    private int age;

    @Column(name = "full_name")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
