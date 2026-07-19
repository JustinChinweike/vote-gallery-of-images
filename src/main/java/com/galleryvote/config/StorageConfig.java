package com.galleryvote.config;
import io.minio.MinioClient; import org.springframework.beans.factory.annotation.Value; import org.springframework.context.annotation.*;
@Configuration public class StorageConfig {@Bean MinioClient minio(@Value("${galleryvote.storage.endpoint}")String endpoint,@Value("${galleryvote.storage.access-key}")String access,@Value("${galleryvote.storage.secret-key}")String secret){return MinioClient.builder().endpoint(endpoint).credentials(access,secret).build();}}
