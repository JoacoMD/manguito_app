package app.manguito.backend.mappers;

import app.manguito.backend.exception.AppException;
import com.amazonaws.services.opsworks.model.App;
import org.mapstruct.Mapper;

import java.util.Base64;


@Mapper(componentModel = "spring")
public interface FileMapper {

    default byte[] stringToByteArray(String base64) throws AppException {
        try {
            return Base64.getMimeDecoder().decode(base64);
        } catch (RuntimeException re) {
            throw new AppException("Ocurrio un error al procesar la imagen", re);
        }
    }
}
