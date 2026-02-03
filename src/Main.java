import util.FileUtil;
import util.TextUtil;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {

        // CHANGE THESE PATHS to match your system
        File mainDoc = new File("test_docs/my_doc.txt");
        File refFolder = new File("reference_docs");

        // Read main document
        String mainText = FileUtil.readFile(mainDoc);
        System.out.println("Main document content:");
        System.out.println(mainText);

        // Read reference documents
        List<File> refFiles = FileUtil.getTextFilesFromFolder(refFolder);
        System.out.println("\nReference documents found:");
        for (File f : refFiles) {
            System.out.println("- " + f.getName());
        }
    }
}
