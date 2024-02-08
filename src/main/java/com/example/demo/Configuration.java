package com.example.demo;

import org.springframework.beans.factory.annotation.Value;

@org.springframework.context.annotation.Configuration
public class Configuration {
    @Value("${file.nameTxt1}")
    public String fileName;

    @Value("${file.nameTxt2}")
    public String fileName2;

    @Value("${file.directory}")
    public String directory;
}
