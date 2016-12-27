/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

/**
 * File Utils
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
public class FileUtils {
    private static final Logger log = Logger.getLogger(FileUtils.class);
    private static final String ENCODING_UTF8 = "UTF-8";

    public static String getCurrentFolder() {
        try {
            String path = FileUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            File folder = new File(URLDecoder.decode(path, ENCODING_UTF8));
            return folder.getParent();

        } catch (final Exception ex) {
            log.warn("Unable to get current folder path!", ex);
        }

        return null;
    }

    public static boolean existsFile(final String fileName) {
        boolean exists = false;

        File file = new File(fileName);

        if (file.exists() && !file.isDirectory()) {
            exists = true;
        }

        return exists;
    }

    public static boolean existsFolder(final String folderName) {
        boolean exists = false;

        File folder = new File(folderName);

        if (folder.exists() && folder.isDirectory()) {
            exists = true;
        }

        return exists;
    }

    public static boolean createFolder(final String folderName) {
        boolean folderCreated = false;

        File folder = new File(folderName);

        if (!folder.exists() && folder.mkdirs()) {
            folderCreated = true;
        }

        return folderCreated;
    }

    public static String readFile(final String filePath) {
        if (StringUtils.isNullOrEmpty(filePath)) {
            return null;
        }

        return readFile(new File(filePath));
    }

    public static String readFile(final File file) {
        if (file == null) {
            return null;
        }

        FileInputStream fis = null;
        String content = null;

        try {
            fis = new FileInputStream(file);
            content = IOUtils.toString(fis, ENCODING_UTF8);

        } catch (final IOException ex) {
            log.warn(String.format("Unable to read file '%s'!", file.getAbsolutePath()), ex);

        } finally {
            if (fis != null) {
                try {
                    fis.close();

                } catch (final IOException ex) {
                    log.warn(String.format("An error occurred during closing file '%s'!", file.getAbsolutePath()), ex);
                }
            }
        }

        return content;
    }
}