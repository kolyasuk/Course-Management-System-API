package edu.sombra.cms.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

public class FileUtil {

    private static final Pattern FILE_EXTENSION_PATTERN = Pattern.compile("(?<=.)\\.[^.]+$");

    public static File multipartToFile(MultipartFile multipart, String fileName) throws IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + fileName);
        multipart.transferTo(convFile);
        return convFile;
    }
    

    public static String getFileNameWithoutExtension(String fileName) {
        return FILE_EXTENSION_PATTERN.matcher(fileName).replaceAll("");
    }

}
