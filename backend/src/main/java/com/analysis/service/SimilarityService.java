package com.analysis.service;

import com.analysis.model.RedundancyResult;
import com.analysis.model.SimilarityResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class SimilarityService {

    private final DocumentService documentService;

    public SimilarityService(DocumentService documentService) {
        this.documentService = documentService;
    }

    // ─────────────────────────────────────────────
    //  MODULE 1: Compare main doc vs references
    // ─────────────────────────────────────────────
    public List<SimilarityResult> analyzeSimilarity(
            MultipartFile mainFile,
            List<MultipartFile> referenceFiles) throws IOException {

        String mainText = documentService.extractText(mainFile);
        List<SimilarityResult> results = new ArrayList<>();

        for (MultipartFile refFile : referenceFiles) {
            String refText = documentService.extractText(refFile);
            double similarity = computeCosineSimilarity(mainText, refText) * 100.0;
            results.add(new SimilarityResult(refFile.getOriginalFilename(), similarity));
        }

        // Sort descending by similarity
        results.sort((a, b) -> Double.compare(b.getSimilarityPercentage(), a.getSimilarityPercentage()));
        return results;
    }

    // ─────────────────────────────────────────────
    //  MODULE 2: Detect redundant sentences
    // ─────────────────────────────────────────────
    public List<RedundancyResult> detectRedundancy(MultipartFile file) throws IOException {
        List<String> sentences = documentService.extractSentences(file);
        List<RedundancyResult> redundancies = new ArrayList<>();
        double threshold = 0.85;

        for (int i = 0; i < sentences.size(); i++) {
            for (int j = i + 1; j < sentences.size(); j++) {
                double sim = computeCosineSimilarity(sentences.get(i), sentences.get(j));
                if (sim >= threshold) {
                    redundancies.add(new RedundancyResult(
                        sentences.get(i),
                        sentences.get(j),
                        sim * 100.0
                    ));
                }
            }
        }
        return redundancies;
    }

    // ─────────────────────────────────────────────
    //  CORE: Cosine Similarity Algorithm
    // ─────────────────────────────────────────────
    private double computeCosineSimilarity(String text1, String text2) {
        Map<String, Integer> freq1 = wordFrequency(text1);
        Map<String, Integer> freq2 = wordFrequency(text2);

        // Union of all words
        Set<String> allWords = new HashSet<>();
        allWords.addAll(freq1.keySet());
        allWords.addAll(freq2.keySet());

        double dotProduct = 0.0;
        double magnitude1 = 0.0;
        double magnitude2 = 0.0;

        for (String word : allWords) {
            int v1 = freq1.getOrDefault(word, 0);
            int v2 = freq2.getOrDefault(word, 0);
            dotProduct += v1 * v2;
            magnitude1 += v1 * v1;
            magnitude2 += v2 * v2;
        }

        if (magnitude1 == 0 || magnitude2 == 0) return 0.0;
        return dotProduct / (Math.sqrt(magnitude1) * Math.sqrt(magnitude2));
    }

    private Map<String, Integer> wordFrequency(String text) {
        Map<String, Integer> freq = new HashMap<>();
        // Lowercase, remove non-alphanumeric, split by whitespace
        String[] words = text.toLowerCase().replaceAll("[^a-z0-9\\s]", "").split("\\s+");
        for (String word : words) {
            if (!word.isEmpty()) {
                freq.put(word, freq.getOrDefault(word, 0) + 1);
            }
        }
        return freq;
    }
}
