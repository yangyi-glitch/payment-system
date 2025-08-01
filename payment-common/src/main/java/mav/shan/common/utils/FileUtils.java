package mav.shan.common.utils;

import cn.hutool.core.text.StrBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileUtils {
    public static synchronized String getFileUrl(String suffix, String fileName) {
        StrBuilder url = new StrBuilder();
        url.append(suffix);
        url.append("/");
        url.append(UUID.randomUUID().toString());
        url.append("-");
        url.append(fileName);
        return url.toString();
    }

    public static synchronized String getFileSuffix(String fileName) {
        String[] split = fileName.split("\\.");
        return split[split.length - 1];
    }

    public static synchronized String conventerUrl(String fileName, String suffix) {
        String[] split = fileName.split("\\.");
        return "convert/" + UUID.randomUUID() + split[0] + "." + suffix;
    }

    public static synchronized String conventerName(String fileName, String suffix) {
        String[] split = fileName.split("\\.");
        return split[0] + "." + suffix;
    }

    public static void createFolder(String filePath) {
        Path directoryPath = Paths.get(filePath);
        boolean exists = Files.exists(directoryPath);
        try {
            if (!exists) {
                Files.createDirectories(directoryPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将流转换为字符串
     *
     * @param inputStream
     * @return
     */
    public static String streamTostring(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String str;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            while ((str = bufferedReader.readLine()) != null) {
                stringBuilder.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
