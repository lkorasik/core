package ru.urfu.mm.application.usecase.get_base_syllabus;

import java.util.List;

public record Response(int year, List<ModuleResponse> modules) {
}
