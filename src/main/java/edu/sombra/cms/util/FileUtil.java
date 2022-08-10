package edu.sombra.cms.util;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.regex.Pattern;

public class FileUtil {

    private static final Pattern FILE_EXTENSION_PATTERN = Pattern.compile("(?<=.)\\.[^.]+$");

    public static File multipartToFile(MultipartFile multipart, String fileName) throws IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + fileName);
        byte[] buffer = multipart.getInputStream().readAllBytes();
        OutputStream outStream = new FileOutputStream(convFile);
        outStream.write(buffer);
        IOUtils.closeQuietly(outStream);
        return convFile;
    }
    

    public static String getFileNameWithoutExtension(String fileName) {
        return FILE_EXTENSION_PATTERN.matcher(fileName).replaceAll("");
    }

}
