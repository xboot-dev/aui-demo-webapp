/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerMapping;

import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.util.IOUtils;
import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.util.StringUtils;

/**
 * Resource Download Controller
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
@Controller
@RequestMapping("/download")
public class ResourceDownloadController {	
	private final Logger log = Logger.getLogger(ResourceDownloadController.class);

	public static final String RESOURCES = "resources";
	public static final String ATTACHMENT = "attachment";
	public static final String RESOURCE_INTERNAL = "_";

	@RequestMapping(value = "/resources/{pluginKey}/**", method = RequestMethod.GET)
	public void handleFileDownloadAsResource(
			@PathVariable("pluginKey") final String pluginKey,
			final HttpServletRequest request,
			final HttpServletResponse response) {
				
		String path = getPath(pluginKey, RESOURCES, request);
		
		//log.debug(String.format("Path: %s", path));

		try {			
			fileDownload(path, pluginKey, request, response);
			
		} catch (final IOException ex) {
			log.warn(String.format("Unable to download resource '%s'!", path), ex);
		}
	}
	
	@RequestMapping(value = "/attachment/{pluginKey}/**", method = RequestMethod.GET)
	public void handleFileDownloadAsAttachment(
			@PathVariable("pluginKey") final String pluginKey,
			final HttpServletRequest request,
			final HttpServletResponse response) {
				
		String path = getPath(pluginKey, ATTACHMENT, request);
		String filename = path.substring(path.lastIndexOf('/') + 1, path.length());

		//log.debug(String.format("Path: %s, Filename: %s", path, filename));

		try {			
			response.setHeader("Content-Disposition", String.format("attachment; filename=%s", filename));
			fileDownload(path, pluginKey, request, response);
			
		} catch (final IOException ex) {
			log.warn(String.format("Unable to download attachment '%s'!", filename), ex);
		}
	}
	
	private String getPath(String pluginKey, String type, HttpServletRequest request) {
		String urlPath = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		String mapping = String.format("/download/%s", type);
		String path = urlPath.replace(String.format("%s/%s", mapping, pluginKey), "");
		//log.debug(String.format("Url Path: %s", urlPath));
		return path;
	}

	public void fileDownload(
			final String path,
			final String pluginKey,
			final HttpServletRequest request,
			final HttpServletResponse response) throws IOException {

		if (path.toUpperCase().startsWith("/META-INF") ||
				path.toUpperCase().startsWith("/WEB-INF")) {
			return;
		}

		InputStream is = null;

		if (pluginKey.equals(RESOURCE_INTERNAL)) {
			// from .WAR file
			is = request.getServletContext().getResourceAsStream(path);
		}

		if (is != null) {
			//log.debug(String.format("Loading file %s...", path));

			byte[] data = IOUtils.copyToByteArray(is);

			is.close();

			String contentType = getContentType(path);

			if (contentType != null) {
				response.setContentType(contentType);
			}

			response.setContentLength(data.length);
			IOUtils.copy(data, response.getOutputStream());

		} else {
			log.warn(String.format("File %s does not exist!", path));
		}
	}

	public String getContentType(final String path) {
		if (StringUtils.isNullOrEmpty(path)) {
			return null;
		}

		String contentType = null;

		/*
		 * MIME Types – Complete List
		 * http://www.sitepoint.com/web-foundations/mime-types-complete-list/
		 *
		 * MIME Types for Web Fonts
		 * http://www.fantomfactory.org/articles/mime-types-for-web-fonts-in-bedsheet
		 */

		Map<String, String> mineTypes = new HashMap<String, String>();
		mineTypes.put(".js", "text/javascript");
		mineTypes.put(".css", "text/css");
		mineTypes.put(".svg", "image/svg+xml");
		mineTypes.put(".jpg", "image/jpeg");
		mineTypes.put(".png", "image/png");
		mineTypes.put(".gif", "image/gif");
		mineTypes.put(".ico", "image/x-icon");
		mineTypes.put(".txt", "text/plain");
		mineTypes.put(".xml", "text/xml");
		mineTypes.put(".json", "application/json");
		mineTypes.put(".ttf", "application/font-sfnt");
		mineTypes.put(".otf", "application/font-sfnt");
		mineTypes.put(".eot", "application/vnd.ms-fontobject");
		mineTypes.put(".woff", "application/font-woff");
		mineTypes.put(".woff2", "application/font-woff2");

		String fileExt = null;
		int endIndex = path.lastIndexOf(".");

		if (endIndex != -1) {
			fileExt = path.substring(endIndex, path.length());
		}

		//log.debug(String.format("File extension %s for file - %s", fileExt, path));

		if (fileExt != null && mineTypes.containsKey(fileExt)) {
			contentType = mineTypes.get(fileExt);
		}

		return contentType;
	}
}