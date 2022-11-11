package app.manguito.backend.mappers;

import org.mapstruct.Mapper;
import org.springframework.util.Base64Utils;


@Mapper(componentModel = "spring")
public interface FileMapper {

    default String byteArrayToString(byte[] file) {
        return Base64Utils.encodeToString(file);
    }

    default byte[] stringToByteArray(String base64) {
        return Base64Utils.decodeFromString(base64);
    }
}
