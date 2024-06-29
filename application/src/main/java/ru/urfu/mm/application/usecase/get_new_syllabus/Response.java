package ru.urfu.mm.application.usecase.get_new_syllabus;

import java.util.List;
import java.util.UUID;

public record Response(UUID semesterId, List<UUID> requiredCourses, List<UUID> availableCourses, List<UUID> scienceWorks) {
}
