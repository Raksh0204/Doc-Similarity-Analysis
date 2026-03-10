package com.analysis.controller;

import com.analysis.model.RedundancyResult;
import com.analysis.model.SimilarityResult;
import com.analysis.service.DocumentService;
import com.analysis.service.SimilarityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Allow standalone frontend to call this API
public class ApiController {

    private final SimilarityService similarityService;
    private final DocumentService documentService;

    public ApiController(SimilarityService similarityService, DocumentService documentService) {
        this.similarityService = similarityService;
        this.documentService = documentService;
    }

    // ── API: Similarity Analysis (returns JSON) ──
    @PostMapping("/analyze-similarity")
    public ResponseEntity<?> analyzeSimilarity(
            @RequestParam("mainFile") MultipartFile mainFile,
            @RequestParam("referenceFiles") List<MultipartFile> referenceFiles) {
        try {
            List<SimilarityResult> results = similarityService.analyzeSimilarity(mainFile, referenceFiles);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // ── API: Redundancy Detection (returns JSON) ──
    @PostMapping("/detect-redundancy")
    public ResponseEntity<?> detectRedundancy(@RequestParam("docFile") MultipartFile docFile) {
        try {
            List<String> sentences = documentService.extractSentences(docFile);
            List<RedundancyResult> redundancies = similarityService.detectRedundancy(docFile);

            Map<String, Object> response = new HashMap<>();
            response.put("totalSentences", sentences.size());
            response.put("redundancies", redundancies);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}
