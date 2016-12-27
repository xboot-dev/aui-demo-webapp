/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;

import org.apache.log4j.Logger;

/**
 * IO (Input/Output) Utils
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
public class IOUtils {
    private static final Logger log = Logger.getLogger(IOUtils.class);

    private static final int DEFAULT_BUFFER_SIZE = 4096; // 4kB

    public static String toString(final InputStream is) {
        if (is == null) {
            return null;
        }

        String result = null;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            StringWriter out = new StringWriter();

            char[] buffer = new char[DEFAULT_BUFFER_SIZE];
            int n = 0;

            while (-1 != (n = reader.read(buffer))) {
                out.write(buffer, 0, n);
            }

            result = out.toString();

        } catch (final IOException ex) {
            log.warn("Unable to convert InputStream to String!", ex);
        }

        return result;
    }

    /**
     * Copy the contents of the given InputStream into a new byte array.
     * Closes the stream when done.
     * @param in the stream to copy from
     * @return the new byte array that has been copied to
     * @throws IOException in case of I/O errors
     */
    public static byte[] copyToByteArray(final InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream(DEFAULT_BUFFER_SIZE);
        copy(in, out);
        return out.toByteArray();
    }

    /**
     * Copy the contents of the given InputStream to the given OutputStream.
     * Leaves both streams open when done.
     * @param in the InputStream to copy from
     * @param out the OutputStream to copy to
     * @return the number of bytes copied
     * @throws IOException in case of I/O errors
     */
    public static int copy(final InputStream in, final OutputStream out) throws IOException {
        int bytesRead = -1;
        int byteCount = 0;
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];

        while ((bytesRead = in.read(buffer)) != -1) {
            out.write(buffer, 0, bytesRead);
            byteCount += bytesRead;
        }

        out.flush();

        return byteCount;
    }

    /**
     * Copy the contents of the given byte array to the given OutputStream.
     * Closes the stream when done.
     * @param in the byte array to copy from
     * @param out the OutputStream to copy to
     * @throws IOException in case of I/O errors
     */
    public static void copy(final byte[] in, final OutputStream out) throws IOException {
        try {
            out.write(in);

        } finally {
            try {
                out.close();

            } catch (final IOException ex) {
                log.warn("Unable to close output stream!", ex);
            }
        }
    }

    public static String getSystemInput() {
        String result = null;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            StringWriter out = new StringWriter();

            String input = null;

            while ((input = reader.readLine()) != null) {
                out.write(String.format("%s\n", input));
            }

            result = out.toString();

        } catch (final IOException ex) {
            log.warn("Unable to read standard input!", ex);
        }

        return result;
    }
}