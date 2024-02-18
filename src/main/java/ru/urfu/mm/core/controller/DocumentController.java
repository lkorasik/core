package ru.urfu.mm.core.controller;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

@RestController
@RequestMapping("/api/document")
public class DocumentController {
    @GetMapping("/generate")
    public void generateDocument() {
        try (var stream = this.getClass().getResourceAsStream("template.docx")) {
            Objects.requireNonNull(stream);

            XWPFDocument document = new XWPFDocument(stream);

            for (Iterator<XWPFParagraph> it = document.getParagraphsIterator(); it.hasNext(); ) {
                var paragraph = it.next();

                for (var run : paragraph.getRuns()) {
                    var text = run.getText(0);
                    if (text != null) {
                        replaceText(text, "HeadOfEducationalProgramInitials", "А. А. Кошелев", run);
                        replaceText(text, "StudentFullName", "Соломеин Лев Евгеньевич", run);
                        replaceText(text, "FirstSemesterYear", "2023", run);
                        replaceText(text, "SecondSemesterYear", "2024", run);
                        replaceText(text, "ThirdSemesterYear", "2025", run);
                        replaceText(text, "StudentInitials", "Соломеин Л. Е.", run);
                    }
                }
            }

            var tables = document.getTables().iterator();
            var commonTable = tables.next();
            for (var row : commonTable.getRows()) {
                for (var cell : row.getTableCells()) {
                    for (var paragraph : cell.getParagraphs()) {
                        for (var run : paragraph.getRuns()) {
                            var text = run.getText(0);
                            if (text != null) {
                                replaceText(text, "TrainingDirection", "02.04.01 Математика и компьютерные науки", run);
                                replaceText(text, "EducationalProgram", "Современные проблемы компьютерных наук", run);
                                replaceText(text, "DeadlineForSumbittingBachelorsDiploma", "Май 2025", run);
                            }
                        }
                    }
                }
            }

            var list = new ArrayList<Row>();
            list.add(new Row("1", "Алгоритмы и структуры данных. Часть 1", "288", "8", "Экзамен", ""));
            list.add(new Row("2", "Компьютерные науки", "72", "2", "Экзамен", ""));
            list.add(new Row("3", "Современные научные исследования", "144", "4", "Экзамен", ""));

            for(int i = 0; i < 4; i++) {
                var firstRequiredTable = tables.next();
                for (var row : list) {
                    var newRow = firstRequiredTable.createRow();
                    newRow.getTableCells().get(0).appendText(row.getNumber());
                    newRow.getTableCells().get(1).appendText(row.getName());
                    newRow.getTableCells().get(2).appendText(row.getHours());
                    newRow.getTableCells().get(3).appendText(row.getVolums());
                    newRow.getTableCells().get(4).appendText(row.getCheck());
                    newRow.getTableCells().get(5).appendText(row.getMark());
                }

                var firstOptionalTable = tables.next();
                for (var row : list) {
                    var newRow = firstOptionalTable.createRow();
                    newRow.getTableCells().get(0).appendText(row.getNumber());
                    newRow.getTableCells().get(1).appendText(row.getName());
                    newRow.getTableCells().get(2).appendText(row.getHours());
                    newRow.getTableCells().get(3).appendText(row.getVolums());
                    newRow.getTableCells().get(4).appendText(row.getCheck());
                    newRow.getTableCells().get(5).appendText(row.getMark());
                }

                var firstScienceTable = tables.next();
                for (var row : list) {
                    var newRow = firstScienceTable.createRow();
                    newRow.getTableCells().get(0).appendText(row.getNumber());
                    newRow.getTableCells().get(1).appendText(row.getName());
                    newRow.getTableCells().get(2).appendText(row.getHours());
                    newRow.getTableCells().get(3).appendText(row.getVolums());
                    newRow.getTableCells().get(4).appendText(row.getCheck());
                    newRow.getTableCells().get(5).appendText(row.getMark());
                }
            }

            FileOutputStream fos = new FileOutputStream(File.createTempFile("StudyPlan-", ""));
            document.write(fos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void replaceText(String text, String placeholder, String value, XWPFRun run) {
        if (text.contains(placeholder)) {
            run.setText(value, 0);
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