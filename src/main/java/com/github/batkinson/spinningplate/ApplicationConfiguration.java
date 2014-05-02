package com.github.batkinson.spinningplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * Configures application services based on environmental configuration.
 */
@Configuration
@PropertySource("file:${user.home}/.spinning-plate.properties")
public class ApplicationConfiguration {

	@Autowired
	Environment env;

	@Bean(name = "storageDirectory")
	File storageLocation() throws IOException {
		Path storagePath = Paths.get(env.getProperty("storage.dir"));
		return Files.createDirectories(storagePath).toFile();
	}

	@Bean
	DocumentService documentService() throws IOException {
		return new DocumentServiceImpl(storageLocation());
	}
}
