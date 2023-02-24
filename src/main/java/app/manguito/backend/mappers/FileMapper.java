package app.manguito.backend.mappers;

import org.mapstruct.Mapper;

import java.util.Base64;


@Mapper(componentModel = "spring")
public interface FileMapper {

    default String byteArrayToString(byte[] file) {
        return Base64.getMimeEncoder().encodeToString(file);
    }

    default byte[] stringToByteArray(String base64) {
        return Base64.getMimeDecoder().decode(base64);
    }
}
