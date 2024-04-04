package ru.urfu.mm.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.urfu.mm.entity.Student;
import ru.urfu.mm.repository.StudentRepository;
import ru.urfu.mm.service.StudentService;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;

    /**
     * Получаем студента
     */
    @Test
    public void getStudent() {
        String login = UUID.randomUUID().toString();

        StudentService studentService = new StudentService(studentRepository);

        Student expected = new Student();

        when(studentRepository.findByLogin(UUID.fromString(login))).thenReturn(Optional.of(expected));

        Student student = studentService.getStudent(login);

        Assertions.assertEquals(expected, student);
    }

    /**
     * Получаем студента
     */
    @Test
    public void getStudent_notFound() {
        String login = UUID.randomUUID().toString();

        StudentService studentService = new StudentService(studentRepository);

        when(studentRepository.findByLogin(UUID.fromString(login))).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> studentService.getStudent(login));
    }
}
