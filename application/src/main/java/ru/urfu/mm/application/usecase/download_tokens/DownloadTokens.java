package ru.urfu.mm.application.usecase.download_tokens;

import ru.urfu.mm.application.exception.NotImplementedException;
import ru.urfu.mm.application.usecase.get_group.GetAcademicGroup;
import ru.urfu.mm.domain.AcademicGroup;
import ru.urfu.mm.domain.Student;

import java.io.*;
import java.util.List;
import java.util.UUID;

/**
 * Скачать регистрационные токены конкретной группы
 */
public class DownloadTokens {
    public File downloadTokens(DownloadTokensRequest request) {
        throw new NotImplementedException();
//        AcademicGroup academicGroup = getAcademicGroup.getGroup(request.groupId());
//        List<UUID> tokens = academicGroup.getStudents().stream().map(Student::getId).toList();

//        return writeTokens(academicGroup.getNumber(), tokens);
    }

    private File writeTokens(String groupNumber, List<UUID> tokens) {
        File file = null;
        try {
            file = File.createTempFile("Tokens_" + groupNumber + "_", "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            tokens.forEach(token -> {
                try {
                    writer.write(String.valueOf(token));
                    writer.newLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return file;
    }
}
