package ru.urfu.mm.application.usecase.download_tokens;

/**
 * Ошибка при работе с файлом.
 */
public class TempFileHandleException extends RuntimeException{
    public TempFileHandleException() {
        super("An error occurred while creating a file or writing to a file.");
    }
}
