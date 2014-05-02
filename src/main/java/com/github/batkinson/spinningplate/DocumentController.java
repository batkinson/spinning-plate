package com.github.batkinson.spinningplate;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.HandlerMapping;

/**
 * Spring MVC controller that handles
 */
@Controller
public class DocumentController {

	@Autowired
	DocumentService documentService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public byte[] getContent(HttpServletRequest request,
			HttpServletResponse response) throws FileNotFoundException,
			IOException {
		String path = getPathFromRequest(request);
		try (InputStream stream = documentService.fetch(path)) {
			if (path.endsWith(".xml"))
				response.setContentType("application/xml");
			else if (path.endsWith(".css"))
				response.setContentType("text/css");
			return IOUtils.toByteArray(stream);
		}
	}

	@RequestMapping(method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	public void putContent(HttpServletRequest request)
			throws FileNotFoundException, IOException {
		String path = getPathFromRequest(request);
		documentService.store(request.getInputStream(), path);
	}

	@RequestMapping(value = "*.pdf", method = RequestMethod.GET)
	@ResponseBody
	public byte[] getPdf(HttpServletRequest request) throws Exception {
		String path = getPathFromRequest(request).replaceAll("[.]pdf$", ".xml");
		return documentService.generate(path);
	}

	private String getPathFromRequest(HttpServletRequest request) {
		return (String) request
				.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
	}

}
