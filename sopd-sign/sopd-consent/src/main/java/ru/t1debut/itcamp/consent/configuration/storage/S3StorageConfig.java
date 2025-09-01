package ru.t1debut.itcamp.consent.configuration.storage;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Value
@ConfigurationProperties(prefix = "s3-storage", ignoreUnknownFields = false)
public class S3StorageConfig {

    MinioConfig minio;

    public record MinioConfig(String sopdDocumentBucketName, String emailFormBucketName) {}
}