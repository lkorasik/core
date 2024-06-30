package ru.urfu.mm.application.usecase.get_base_syllabus;

import java.util.List;
import java.util.UUID;

public record Response(UUID id, int year, List<ModuleResponse> modules) {
}
