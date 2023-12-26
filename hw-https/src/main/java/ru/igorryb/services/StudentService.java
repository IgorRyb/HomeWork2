package ru.igorryb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.igorryb.entities.Student;
import ru.igorryb.repositories.StudentRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> findStudentName() {
        List<Student> students = new ArrayList<>();
        studentRepository.findAll().forEach(students::add);
        return students;
    }

    public void addStudent(int age, String name) {
        Student student = new Student();
        student.setAge(age);
        student.setName(name);
        studentRepository.save(student);
    }
}
