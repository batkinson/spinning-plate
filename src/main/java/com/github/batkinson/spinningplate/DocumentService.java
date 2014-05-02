package com.github.batkinson.spinningplate;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface DocumentService {

	void store(InputStream input, String path) throws IOException;

	void delete(String path);

	InputStream fetch(String path) throws FileNotFoundException;

	byte [] generate(String path) throws Exception;

}
