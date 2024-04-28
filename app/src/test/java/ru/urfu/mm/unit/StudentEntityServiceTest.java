package ru.urfu.mm.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.urfu.mm.persistance.entity.StudentEntity;
import ru.urfu.mm.persistance.repository.StudentRepository;
import ru.urfu.mm.service.StudentService;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentEntityServiceTest {
    @Mock
    private StudentRepository studentRepository;

    /**
     * Получаем студента
     */
    @Test
    public void getStudent() {
        String login = UUID.randomUUID().toString();

        StudentService studentService = new StudentService(studentRepository);

        StudentEntity expected = new StudentEntity();

        when(studentRepository.findByLogin(UUID.fromString(login))).thenReturn(Optional.of(expected));

        StudentEntity studentEntity = studentService.getStudent(login);

        Assertions.assertEquals(expected, studentEntity);
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
