package ru.urfu.mm.core.service;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

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

    public byte[] generateDocument() throws IOException {
        XWPFDocument document = readTemplate();
        var tables = document.getTables().iterator();

        fillStaticContent(document, 2023, 2024, 2025);
        fillGeneralTableInformation(
                tables,
                "02.04.01 Математика и компьютерные науки",
                "Современные проблемы компьютерных наук",
                "Май 2025");

        var list = generateData();
        fillCourseTables(tables, list);

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

    @Deprecated
    private List<List<String>> generateData() {
        return List.of(
            List.of("1", "Алгоритмы и структуры данных. Часть 1", "288", "8", "Экзамен", ""),
            List.of("2", "Компьютерные науки", "72", "2", "Экзамен", ""),
            List.of("3", "Современные научные исследования", "144", "4", "Экзамен", "")
        );
    }

    private void fillCourseTables(Iterator<XWPFTable> tables, List<List<String>> data) {
        for (int i = 0; i < 4; i++) {
            var firstRequiredTable = tables.next();
            for (var row : data) {
                var newRow = firstRequiredTable.createRow();
                for(int j = 0; i < 6; i++) {
                    newRow.getTableCells().get(j).appendText(row.get(j));
                }
            }

            var firstOptionalTable = tables.next();
            for (var row : data) {
                var newRow = firstOptionalTable.createRow();
                for(int j = 0; i < 6; i++) {
                    newRow.getTableCells().get(j).appendText(row.get(j));
                }
            }

            var firstScienceTable = tables.next();
            for (var row : data) {
                var newRow = firstScienceTable.createRow();
                for(int j = 0; i < 6; i++) {
                    newRow.getTableCells().get(j).appendText(row.get(j));
                }
            }
        }
    }

    private XWPFDocument readTemplate() throws IOException {
        XWPFDocument document;
        try (var stream = getClass().getResourceAsStream(TEMPLATE_PATH)) {
            Objects.requireNonNull(stream);

            document = new XWPFDocument(stream);
        }

        return document;
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

class Row {
    private final String number;
    private final String name;
    private final String hours;
    private final String volums;
    private final String check;
    private final String mark;

    public Row(String number, String name, String hours, String volums, String check, String mark) {
        this.number = number;
        this.name = name;
        this.hours = hours;
        this.volums = volums;
        this.check = check;
        this.mark = mark;
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public String getHours() {
        return hours;
    }

    public String getVolums() {
        return volums;
    }

    public String getCheck() {
        return check;
    }

    public String getMark() {
        return mark;
    }
}
