package app.manguito.backend.services.impl;

import app.manguito.backend.dto.SaveImagenDTO;
import app.manguito.backend.entities.Imagen;
import app.manguito.backend.exception.AppException;
import app.manguito.backend.mappers.FileMapper;
import app.manguito.backend.services.R2Service;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.UUID;

@Service
public class R2ServiceImpl implements R2Service {

    @Value("${api.r2.accountId}")
    private String accountId;

    @Value("${api.r2.accessKey}")
    private String accessKey;

    @Value("${api.r2.secretKey}")
    private String secretKey;

    @Value("${api.r2.bucket.name}")
    String bucketName;

    @Autowired
    FileMapper fileMapper;

    AmazonS3 s3client;

    @Override
    public Imagen saveImage(SaveImagenDTO imagen) throws AppException {
        byte[] data = fileMapper.stringToByteArray(imagen.getArchivo());
        try {
            InputStream fis = new ByteArrayInputStream(data);
            String filename = UUID.randomUUID().toString().replace("-", "") + imagen.getExtension();
            getClient().putObject(bucketName, filename, fis, getMetadataFromImagenDTO(imagen, data.length));
            return new Imagen(filename, imagen.getType());
        } catch (RuntimeException re) {
            throw new AppException("Ocurrio un error al guardar la imagen", re);
        }
    }

    private ObjectMetadata getMetadataFromImagenDTO(SaveImagenDTO imagen, int length) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(length);
        metadata.setContentType(imagen.getType());
        return metadata;
    }

    private AmazonS3 getClient() {
        if (s3client == null) {
            AWSCredentials credentials = new BasicAWSCredentials(
                    accessKey,
                    secretKey
            );
            s3client = AmazonS3ClientBuilder
                    .standard()
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                            String.format("https://%s.r2.cloudflarestorage.com", accountId), "auto"))
                    .build();
        }
        return s3client;
    }
}
