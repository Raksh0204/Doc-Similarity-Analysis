package com.analysis.service;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentService {

    /**
     * Extracts all text from a DOCX file.
     */
    public String extractText(MultipartFile file) throws IOException {
        StringBuilder text = new StringBuilder();
        try (XWPFDocument document = new XWPFDocument(file.getInputStream())) {
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                String paraText = paragraph.getText().trim();
                if (!paraText.isEmpty()) {
                    text.append(paraText).append(" ");
                }
            }
        }
        return text.toString().trim();
    }

    /**
     * Splits document text into individual sentences.
     */
    public List<String> extractSentences(MultipartFile file) throws IOException {
        String fullText = extractText(file);
        List<String> sentences = new ArrayList<>();

        // Split by sentence-ending punctuation
        String[] rawSentences = fullText.split("(?<=[.!?])\\s+");
        for (String s : rawSentences) {
            String trimmed = s.trim();
            if (trimmed.length() > 10) { // skip very short fragments
                sentences.add(trimmed);
            }
        }
        return sentences;
    }
}
