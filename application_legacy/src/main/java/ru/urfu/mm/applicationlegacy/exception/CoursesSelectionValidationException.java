package ru.urfu.mm.applicationlegacy.exception;

import ru.urfu.mm.applicationlegacy.usecase.CourseSelectionValidationStatus;

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
            /*
            try {
                return new ObjectMapper().writeValueAsString(semesterIdToValidationStatus);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
             */
            String json = "{";
            for(var key : semesterIdToValidationStatus.keySet()) {
                json += "\"" + key + "\": \"" + semesterIdToValidationStatus.get(key) + "\",";
            }
            json = json.substring(0, json.length() - 1);
            json += "}";
            return json;
        }
        if (IsModuleValidationError)
        {
            return "Not all of the module courses were selected";
        }

        return new Object().toString();
    }
}