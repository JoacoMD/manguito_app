package app.manguito.backend.dto;

import app.manguito.backend.exception.AppException;
import lombok.Data;

@Data
public class ErrorResponse {

    private String message;

    public ErrorResponse(AppException e) {
        this.message = e.getMessage();
    }

    public ErrorResponse(String message) {
        this.message = message;
    }
}
