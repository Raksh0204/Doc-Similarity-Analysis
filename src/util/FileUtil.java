package util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    // Read content of a single file
    public static String readFile(File file) throws IOException {
        return new String(Files.readAllBytes(file.toPath()));
    }

    // Get all .txt files from a folder
    public static List<File> getTextFilesFromFolder(File folder) {
        List<File> files = new ArrayList<>();

        if (folder.exists() && folder.isDirectory()) {
            File[] allFiles = folder.listFiles();
            if (allFiles != null) {
                for (File file : allFiles) {
                    if (file.isFile() && file.getName().endsWith(".txt")) {
                        files.add(file);
                    }
                }
            }
        }
        return files;
    }
}
