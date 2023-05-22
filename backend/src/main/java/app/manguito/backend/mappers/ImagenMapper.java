package app.manguito.backend.mappers;

import app.manguito.backend.entities.Imagen;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ImagenMapper {

    @Value("${api.r2.bucket.url}")
    private String bucketUrl;

    public String toString(Imagen imagen) {
        if (imagen == null) return "";
        return String.format("%s/%s", bucketUrl, imagen.getFilename());
    }

}
