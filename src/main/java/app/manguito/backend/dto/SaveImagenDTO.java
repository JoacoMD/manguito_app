package app.manguito.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveImagenDTO {

    private String archivo;

    private String type;

    public String getExtension() {
        return type.replaceAll("image/", ".");
    }
}
