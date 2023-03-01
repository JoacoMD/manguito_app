package app.manguito.backend.services;

import app.manguito.backend.dto.SaveImagenDTO;
import app.manguito.backend.entities.Imagen;

public interface R2Service {

    Imagen saveImage(SaveImagenDTO imagen);
}
