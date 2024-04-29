package ru.urfu.mm.application.usecase.download_tokens;

import ru.urfu.mm.application.gateway.TokenGateway;
import ru.urfu.mm.application.usecase.get_group.GetGroup;
import ru.urfu.mm.domain.AcademicGroup;

import java.io.*;
import java.util.List;
import java.util.UUID;

/**
 * Скачать регистрационные токены конкретной группы
 */
public class DownloadTokens {
    private final TokenGateway tokenGateway;
    private final GetGroup getGroup;

    public DownloadTokens(TokenGateway tokenGateway, GetGroup getGroup) {
        this.tokenGateway = tokenGateway;
        this.getGroup = getGroup;
    }

    public File downloadTokens(DownloadTokensRequest request) {
        AcademicGroup academicGroup = getGroup.getGroup(request.groupId());
        List<UUID> tokens = tokenGateway.getTokensByGroup(academicGroup);

        File file = null;
        try {
            file = writeTokens(academicGroup.getNumber(), tokens);
        } catch (IOException e) {
            throw new TempFileHandleException();
        }

        return file;
    }

    private File writeTokens(String groupNumber, List<UUID> tokens) throws IOException {
        File file = File.createTempFile("Tokens_" + groupNumber + "_", "");

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            tokens.forEach(token -> {
                try {
                    writer.write(String.valueOf(token));
                    writer.newLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        return file;
    }
}
