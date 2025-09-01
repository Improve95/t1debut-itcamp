package ru.t1debut.itcamp.consent.core.service.implement;

import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.t1debut.itcamp.consent.core.service.S3StorageService;

import java.io.InputStream;

@Slf4j
@RequiredArgsConstructor
@Service
public class S3StorageServiceImpl implements S3StorageService {

    private final S3Template s3Template;

    @Override
    public S3Resource upload(InputStream inputStream, String bucketName, String fileName) {
        return s3Template.upload(bucketName, fileName, inputStream);
    }

    @Override
    public S3Resource download(String bucketName, String key) {
        return s3Template.download(bucketName, key);
    }
}
