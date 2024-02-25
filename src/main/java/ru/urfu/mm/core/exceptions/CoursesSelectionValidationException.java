package ru.urfu.mm.core.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.urfu.mm.core.service.CourseSelectionValidationStatus;

import java.util.Map;
import java.util.UUID;

public class CoursesSelectionValidationException extends RuntimeException {
    private Map<UUID, CourseSelectionValidationStatus> semesterIdToValidationStatus;
    private boolean IsModuleValidationError;

    public CoursesSelectionValidationException(Map<UUID, CourseSelectionValidationStatus> semesterIdToValidationStatus)
    {
        this.semesterIdToValidationStatus = semesterIdToValidationStatus;
    }

    public CoursesSelectionValidationException(boolean isModuleValidationError)
    {
        IsModuleValidationError = isModuleValidationError;
    }

    @Override
    public String toString()
    {
        if (semesterIdToValidationStatus != null)
        {
            try {
                return new ObjectMapper().writeValueAsString(semesterIdToValidationStatus);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        if (IsModuleValidationError)
        {
            return "Not all of the module courses were selected";
        }

        return new Object().toString();
    }
}
