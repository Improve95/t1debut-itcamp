package ru.t1debut.itcamp.consent.core.service;

import io.awspring.cloud.s3.S3Resource;

import java.io.IOException;
import java.io.InputStream;

public interface S3StorageService {

    S3Resource upload(InputStream inputStream, String bucketName, String key) throws IOException;

    S3Resource download(String bucketName, String key);
}
