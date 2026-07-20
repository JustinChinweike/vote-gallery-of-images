package com.galleryvote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class GalleryVoteApplication {
    public static void main(String[] args) { SpringApplication.run(GalleryVoteApplication.class, args); }
}
