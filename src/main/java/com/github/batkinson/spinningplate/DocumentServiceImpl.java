package com.github.batkinson.spinningplate;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;

/**
 * Implementation of core document services.
 */
public class DocumentServiceImpl implements DocumentService {

	private File storageDir;

	public DocumentServiceImpl(File storageDir) {
		this.storageDir = storageDir;
	}

	public void store(InputStream input, String path) throws IOException {
		File storedFile = storageFile(path);
		try (FileOutputStream output = new FileOutputStream(storedFile)) {
			IOUtils.copy(input, output);
		}
	}

	@Override
	public void delete(String path) {
		File storedFile = storageFile(path);
		if (storedFile.exists() && storedFile.isFile() && storedFile.canWrite()) {
			storedFile.delete();
		}
	}

	private File storageFile(String path) {
		return new File(storageDir, path);
	}

	public InputStream fetch(String path) throws FileNotFoundException {
		File storedFile = storageFile(path);
		return new FileInputStream(storedFile);
	}

	public byte[] generate(String path) throws Exception {
		try (InputStream input = fetch(path);
				ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document doc = docBuilder.parse(input);
			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocument(doc, storageDir.toURI().toString());
			renderer.layout();
			renderer.createPDF(out);
			return out.toByteArray();
		}
	}
}
