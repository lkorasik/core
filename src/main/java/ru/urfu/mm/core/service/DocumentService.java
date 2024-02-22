package ru.urfu.mm.core.service;

import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.urfu.mm.core.entity.EducationalProgramToCoursesWithSemesters;
import ru.urfu.mm.core.entity.SelectedCourses;
import ru.urfu.mm.core.repository.EducationalProgramToCoursesWithSemestersRepository;
import ru.urfu.mm.core.repository.SelectedCoursesRepository;
import ru.urfu.mm.core.repository.StudentRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DocumentService {
    private final String TEMPLATE_PATH = "template.docx";
    private final String HEAD_OF_EDUCATIONAL_PROGRAM_FULL_INITIALS_PLACEHOLDER = "HeadOfEducationalProgramFullInitials";
    private final String STUDENT_FULL_NAME_PLACEHOLDER = "StudentFullName";
    private final String FIRST_SEMESTER_YEAR_PLACEHOLDER = "FirstSemesterYear";
    private final String SECOND_SEMESTER_YEAR_PLACEHOLDER = "SecondSemesterYear";
    private final String THIRD_SEMESTER_YEAR_PLACEHOLDER = "ThirdSemesterYear";
    private final String STUDENT_INITIALS_PLACEHOLDER = "StudentInitials";
    private final String TRAINING_DIRECTION_PLACEHOLDER = "TrainingDirection";
    private final String EDUCATIONAL_PROGRAM_PLACEHOLDER = "EducationalProgram";
    private final String DEADLINE_FOR_SUBMITTING_BACHELORS_DIPLOMA_PLACEHOLDER = "DeadlineForSumbittingBachelorsDiploma";

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private SelectedCoursesRepository selectedCoursesRepository;
    @Autowired
    private EducationalProgramToCoursesWithSemestersRepository educationalProgramToCoursesWithSemestersRepository;

    public byte[] generateDocument(UUID studentId) throws IOException {
        XWPFDocument document = readTemplate();
        var tables = document.getTables().iterator();

        fillStaticContent(document, 2023, 2024, 2025);
        fillGeneralTableInformation(
                tables,
                "02.04.01 Математика и компьютерные науки",
                "Современные проблемы компьютерных наук",
                "Май 2025");

        var student = studentRepository.findByLogin(studentId).get();
        var courses = educationalProgramToCoursesWithSemestersRepository
                .findAll()
                .stream()
                .filter(x -> x.getEducationalProgram().getId() == student.getEducationalProgram().getId())
                .toList();
        var selectedCourses = selectedCoursesRepository
                .findAll()
                .stream()
                .filter(x -> x.getStudent().getLogin() == studentId)
                .toList();
        var semesterToCourse = courses
                .stream()
                .collect(
                        Collectors.groupingBy(
                                x -> x.getSemester().getSemesterNumber(),
                                Collectors.mapping(
                                        x -> x,
                                        Collectors.toList()
                                )
                        )
                );

        fillCourseTables(tables, semesterToCourse, selectedCourses);

        var path = writeText(document);

        return Files.readAllBytes(Path.of(path));
    }

    private String writeText(XWPFDocument document) throws IOException {
        var tempFile = File.createTempFile("StudyPlan-", "");
        FileOutputStream fos = new FileOutputStream(tempFile);
        document.write(fos);

        return tempFile.getAbsolutePath();
    }

    private void replaceText(String text, String placeholder, String value, XWPFRun run) {
        if (text.contains(placeholder)) {
            run.setText(value, 0);
        }
    }

    private void fillCourseTables(Iterator<XWPFTable> tables, Map<Integer, List<EducationalProgramToCoursesWithSemesters>> semesterToCourse, List<SelectedCourses> selectedCourses) {
        for(int i = 0; i < 4; i++) {
            var courses = semesterToCourse.get(i + 1);

            var requiredTable = tables.next();
            if(courses != null) {
                var requiredCourses = courses
                        .stream()
                        .filter(EducationalProgramToCoursesWithSemesters::isRequiredCourse)
                        .filter(x -> {
                            var item = x.getSpecialCourse().getId();
                            return selectedCourses
                                    .stream()
                                    .map(y -> y.getSpecialCourse().getId())
                                    .toList()
                                    .contains(item);
                        })
                        .toList();
                fillTable(requiredCourses, requiredTable);
            }

            var optionalTable = tables.next();
            if(courses != null) {
                var optionalCourses = courses
                        .stream()
                        .filter(x -> !x.isRequiredCourse())
                        .filter(x ->
                                selectedCourses
                                        .stream()
                                        .map(y -> y.getSpecialCourse().getId())
                                        .toList()
                                        .contains(x.getSpecialCourse().getId())
                        )
                        .toList();
                fillTable(optionalCourses, optionalTable);
            }

            var scienceTable = tables.next();
            if(courses != null) {
                fillTable(Collections.emptyList(), scienceTable);
            }
        }
    }

    private XWPFDocument readTemplate() throws IOException {
        XWPFDocument document;
        try (var stream = getClass().getResourceAsStream("/" + TEMPLATE_PATH)) {
            Objects.requireNonNull(stream);

            document = new XWPFDocument(stream);
        }

        return document;
    }

    private void fillTable(List<EducationalProgramToCoursesWithSemesters> courses, XWPFTable table) {
        for(var j = 0; j < courses.size(); j++) {
            var course = courses.get(j);
            var row = table.createRow();
            fillRow(row, course, j + 1);
        }
    }

    private void fillRow(XWPFTableRow row, EducationalProgramToCoursesWithSemesters course, int ordinal) {
        row.getTableCells().get(0).appendText(String.valueOf(ordinal));
        row.getTableCells().get(1).appendText(course.getSpecialCourse().getName());
        row.getTableCells().get(2).appendText("0");
        row.getTableCells().get(3).appendText(String.valueOf(course.getSpecialCourse().getCreditsCount()));
        row.getTableCells().get(4).appendText(course.getSpecialCourse().getControl().getDocumentaryValue());
        row.getTableCells().get(5).appendText("0");
    }

    private void fillGeneralTableInformation(Iterator<XWPFTable> tables, String trainingDirection, String educationalProgram, String deadlineForSubmittingBachelorsDiploma) {
        var commonTable = tables.next();
        for (var row : commonTable.getRows()) {
            for (var cell : row.getTableCells()) {
                for (var paragraph : cell.getParagraphs()) {
                    for (var run : paragraph.getRuns()) {
                        var text = run.getText(0);
                        if (text != null) {
                            replaceText(text, TRAINING_DIRECTION_PLACEHOLDER, trainingDirection, run);
                            replaceText(text, EDUCATIONAL_PROGRAM_PLACEHOLDER, educationalProgram, run);
                            replaceText(text, DEADLINE_FOR_SUBMITTING_BACHELORS_DIPLOMA_PLACEHOLDER, deadlineForSubmittingBachelorsDiploma, run);
                        }
                    }
                }
            }
        }
    }

    private void fillStaticContent(XWPFDocument document, int firstYear, int secondYear, int thirdYear) {
        for (Iterator<XWPFParagraph> it = document.getParagraphsIterator(); it.hasNext(); ) {
            var paragraph = it.next();

            for (var run : paragraph.getRuns()) {
                var text = run.getText(0);
                if (text != null) {
//                    replaceText(text, HEAD_OF_EDUCATIONAL_PROGRAM_FULL_INITIALS_PLACEHOLDER, HEAD_OF_EDUCATIONAL_PROGRAM_FULL_INITIALS_PLACEHOLDER, run);
//                    replaceText(text, STUDENT_FULL_NAME_PLACEHOLDER, STUDENT_FULL_NAME_PLACEHOLDER, run);
                    replaceText(text, FIRST_SEMESTER_YEAR_PLACEHOLDER, String.valueOf(firstYear), run);
                    replaceText(text, SECOND_SEMESTER_YEAR_PLACEHOLDER, String.valueOf(secondYear), run);
                    replaceText(text, THIRD_SEMESTER_YEAR_PLACEHOLDER, String.valueOf(thirdYear), run);
//                    replaceText(text, STUDENT_INITIALS_PLACEHOLDER, StudentInitials, run);
                }
            }
        }
    }
}
