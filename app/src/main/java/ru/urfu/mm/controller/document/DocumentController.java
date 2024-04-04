package ru.urfu.mm.controller.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.urfu.mm.controller.AbstractAuthorizedController;
import ru.urfu.mm.entity.Student;
import ru.urfu.mm.service.DocumentService;
import ru.urfu.mm.service.StudentService;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/document")
public class DocumentController extends AbstractAuthorizedController {
    @Autowired
    private DocumentService documentService;

    @GetMapping("/generate")
    public byte[] generateDocument() throws IOException {
        return documentService.generateDocument(UUID.fromString(getUserToken()));
    }
}