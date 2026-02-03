package util;

import java.util.ArrayList;
import java.util.List;

public class TextUtil {

    // Convert text to lowercase and remove punctuation
    public static String cleanText(String text) {
        text = text.toLowerCase();
        text = text.replaceAll("[^a-zA-Z0-9\\. ]", "");
        return text;
    }

    // Split text into sentences
    public static List<String> splitIntoSentences(String text) {
        List<String> sentences = new ArrayList<>();
        String[] parts = text.split("\\.");

        for (String sentence : parts) {
            sentence = sentence.trim();
            if (!sentence.isEmpty()) {
                sentences.add(sentence);
            }
        }
        return sentences;
    }
}
