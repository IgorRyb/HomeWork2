package ru.igorryb.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.igorryb.entities.Student;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {
}
