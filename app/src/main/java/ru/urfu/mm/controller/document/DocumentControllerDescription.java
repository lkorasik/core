package ru.urfu.mm.controller.document;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.urfu.mm.controller.Endpoints;

import java.io.IOException;

@Tag(name = "Document", description = "Управление документами")
@RequestMapping(Endpoints.Document.BASE)
public interface DocumentControllerDescription {

    @GetMapping(Endpoints.Document.GENERATE)
    byte[] generateDocument() throws IOException;
}
